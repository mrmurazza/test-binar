package user;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

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
}
