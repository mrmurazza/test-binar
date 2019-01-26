package product;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

/*
 * Implementation of DAO layer of Product using SQL Database
 */

@Singleton
public class ProductSqlDAO implements IProductDAO {
    private JPAApi jpaApi;

    @Inject
    public ProductSqlDAO(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Override
    public Product persist(Product product){
        return jpaApi.em().merge(product);
    }

    @Override
    public Optional<Product> getById(long id) {
        Query q = jpaApi.em().createQuery("select product from Product product where product.id = :id");
        q.setParameter("id", id);

        try {
            return Optional.of((Product) q.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> getByUserId(long userId) {
        Query q = jpaApi.em().createQuery("select product from Product product where product.userId = :userId");
        q.setParameter("userId", userId);

        return q.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query q = jpaApi.em().createQuery("DELETE from Product product where product.id = :id");
        q.setParameter("id", id);
        q.executeUpdate();

        // Flush and clear to sync the local entity manager changes and clear its local em.
        // to make sure the entity we delete is deleted in the local EM too
        jpaApi.em().flush();
        jpaApi.em().clear();
    }
}
