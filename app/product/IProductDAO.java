package product;

import com.google.inject.ImplementedBy;

import java.util.List;
import java.util.Optional;

@ImplementedBy(ProductDAO.class)
public interface IProductDAO {
    Product persist(Product product);
    Optional<Product> getById(long id);
    List<Product> getByUserId(long userId);
    void deleteById(long id);
}
