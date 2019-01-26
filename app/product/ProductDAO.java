package product;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Singleton
public class ProductDAO implements IProductDAO {
    private JPAApi jpaApi;

    @Inject
    public ProductDAO(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Override
    public Product persist(Product product){
        return jpaApi.em().merge(product);
    }

    @Override
    public Optional<Product> getById(long id) {
        return null;
    }

    @Override
    public List<Product> getByUserId(long userId) {

        return Collections.emptyList();
    }

    @Override
    public void deleteById(long userId) {

    }
}
