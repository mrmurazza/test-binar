package product;

import exceptions.ListException;
import org.apache.commons.lang3.StringUtils;
import request.ProductRequest;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public class ProductManager {
    IProductDAO productDAO;

    @Inject
    public ProductManager(IProductDAO productDAO){
        this.productDAO = productDAO;
    }

    public List<Product> getByUserId(long userId){
        return productDAO.getByUserId(userId);
    }

    public Product createNewProduct(ProductRequest request, long userId) throws ListException {
        validateCreationRequest(request);

        Product product = new Product(userId,
                                      request.getName().get(),
                                      request.getPrice().get(),
                                      request.getImageurl().get());

        return productDAO.persist(product);
    }

    private void validateCreationRequest(ProductRequest request) throws ListException {
        ListException errors =  new ListException();

        if (!request.getName().isPresent() || StringUtils.isBlank(request.getName().get()))
            errors.add("Name must exist, please input the name of the product");

        if (!request.getPrice().isPresent())
            errors.add("Price must exist, please input the price of the product");

        if (request.getPrice().get() <= 0)
            errors.add("Price must be greater than 0");

        if (!request.getImageurl().isPresent() || StringUtils.isBlank(request.getImageurl().get()))
            errors.add("Image url must exist, please input the product's image url");

        if (!errors.isEmpty())
            throw errors;
    }

    public Product updateProduct(ProductRequest request, long id, long userId) throws ListException {
        Product product = getValidatedProductById(id, userId);
        validateUpdateRequest(request);

        product.setName(request.getName().get());
        product.setPrice(request.getPrice().get());
        product.setImageUrl(request.getImageurl().get());

        return productDAO.persist(product);
    }

    private void validateUpdateRequest(ProductRequest request) throws ListException {
        ListException errors = new ListException();

        if (!request.getName().isPresent() && request.getPrice().isPresent() && !request.getImageurl().isPresent())
            throw new ListException("Your request is empty, please fill at least one between name, " +
                    "price, or image url of this product you wish to update");

        if (request.getName().isPresent() && StringUtils.isBlank(request.getName().get()))
            errors.add("New name cannot be empty or whitespaces only");

        if (request.getPrice().isPresent() && request.getPrice().get() <= 0)
            errors.add("New price must be greater than 0");

        if (request.getImageurl().isPresent() && StringUtils.isBlank(request.getImageurl().get()))
            errors.add("New Image url cannot be empty or whitespaces only");

        if (!errors.isEmpty())
            throw errors;
    }

    public Product getValidatedProductById(long id, long userId) throws ListException {
        Optional<Product> productOpt = productDAO.getById(id);

        ListException errors = new ListException();
        if (!productOpt.isPresent())
            errors.add("Product with given ID is not found");
        else if (productOpt.get().getUserId() != userId)
            errors.add("Product with given ID is not associated with your User account");

        if (!errors.isEmpty())
            throw errors;

        return productOpt.get();
    }

    public void deleteById(long id, long userId) throws ListException {
        Product product = getValidatedProductById(id, userId);
        productDAO.deleteById(product.getId());
    }
}
