package user;

import com.google.inject.ImplementedBy;

import java.util.Optional;

@ImplementedBy(UserDAO.class)
public interface IUserDAO {
    User persist(User user);
    Optional<User> getByAccessToken(String accessToken);
}
