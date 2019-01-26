package user;

import com.google.inject.ImplementedBy;

import java.util.Optional;

@ImplementedBy(UserSqlDAO.class)
public interface IUserDAO {
    User persist(User user);
    Optional<User> getByAccessToken(String accessToken);
    Optional<User> getByEmail(String email);
    Optional<User> getByEmailAndPassword(String email, String password);
}
