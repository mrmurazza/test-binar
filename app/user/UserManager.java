package user;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserManager {
    private IUserDAO userDAO;

    @Inject
    public UserManager(IUserDAO userDAO){
        this.userDAO = userDAO;
    }
}
