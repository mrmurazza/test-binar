package actions;

import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import response.BaseJSONResponse;
import user.User;
import user.UserManager;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.Optional;

@Singleton
public class LoggedIn extends Security.Authenticator {
    private UserManager userManager;

    @Inject
    public LoggedIn(UserManager userManager){
        this.userManager = userManager;
    }

    @Override
    public String getUsername(Http.Context ctx) {
        Optional<User> userOpt = userManager.getCurrentUser();

        return userOpt.map(User::getName).orElse(null);

    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        return unauthorized(Json.toJson(BaseJSONResponse.initSingleErrorResponse("ERROR")));
    }

}
