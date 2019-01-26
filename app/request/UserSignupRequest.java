package request;

import java.util.Optional;

public class UserSignupRequest {
    private String name;
    private String email;
    private String password;

    private UserSignupRequest(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static UserSignupRequest initFromFormRequest(FormRequest request){
        Optional<String> nameOpt = request.getOptionalString("name");
        Optional<String> emailOpt = request.getOptionalString("email");
        Optional<String> passwordOpt = request.getOptionalString("password");

        return new UserSignupRequest(nameOpt.orElse(null),
                                     emailOpt.orElse(null),
                                     passwordOpt.orElse(null));
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }

    public Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }
}
