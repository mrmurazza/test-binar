package user;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class UserDAO implements IUserDAO {
    private JPAApi jpaApi;

    @Inject
    public UserDAO(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Override
    public User persist(User user){
        return jpaApi.em().merge(user);
    }

    @Override
    public Optional<User> getByAccessToken(String accessToken){
        return Optional.empty();
    }
}
