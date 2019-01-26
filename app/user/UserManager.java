package user;

import exceptions.ListException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import request.UserSignupRequest;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static play.mvc.Controller.request;

@Singleton
public class UserManager {
    private IUserDAO userDAO;

    @Inject
    public UserManager(IUserDAO userDAO){
        this.userDAO = userDAO;
    }

    public Optional<User> getCurrentUser(){
        Optional<String> accessTokenOpt = request().getHeaders().get("Authorization");

        if (!accessTokenOpt.isPresent())
            return Optional.empty();

        return userDAO.getByAccessToken(accessTokenOpt.get());
    }


    public Optional<User> login(String email, String password){
        String digestedPassword = DigestUtils.sha256Hex(password);
        return userDAO.getByEmailAndPassword(email, digestedPassword);
    }

    public User signup(UserSignupRequest request) throws ListException {
        validateSignupRequest(request);

        String digestedPassword = DigestUtils.sha256Hex(request.getPassword().get());
        User user = new User(request.getName().get(), request.getEmail().get(), digestedPassword);

        return userDAO.persist(user);
    }

    private void validateSignupRequest(UserSignupRequest request) throws ListException {
        ListException errors =  new ListException();

        if (!request.getName().isPresent() || StringUtils.isBlank(request.getName().get()))
            errors.add("Please input your name");

        if (!request.getEmail().isPresent())
            errors.add("Please input your email");

        if (!isEmailFormatValid(request.getEmail().get()))
            errors.add("Email format is invalid, please recheck your email");

        try {
            validateEmailDomain(request.getEmail().get());
        } catch (NamingException e) {
            errors.add("Email format is invalid, please recheck your email");
        }

        if (userDAO.getByEmail(request.getEmail().get()).isPresent())
            errors.add("Email already in use, please recheck your email");

        if (!request.getPassword().isPresent() || StringUtils.isBlank(request.getPassword().get()))
            errors.add("Please input your password");

        if (!errors.isEmpty())
            throw errors;
    }

    private static boolean isEmailFormatValid(String email) {
        Pattern p = Pattern.compile("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
        Matcher m = p.matcher(email);
        return m.find();
    }

    private static void validateEmailDomain(String email) throws NamingException {
        String hostName = email.substring(email.lastIndexOf("@") + 1);
        InitialDirContext iDirC = new InitialDirContext();
        Attributes attributes = iDirC.getAttributes("dns:/" + hostName, new String[] {"MX"});
        // attributeMX is an attribute ('list') of the Mail Exchange(MX) Resource Records(RR)
        Attribute attributeMX = attributes.get("MX");

        if(attributeMX == null || attributeMX.size() == 0)
            throw new NamingException();
    }
}
