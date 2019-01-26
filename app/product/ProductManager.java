package product;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ProductManager {
    IProductDAO productDAO;

    @Inject
    public ProductManager(IProductDAO productDAO){
        this.productDAO = productDAO;
    }


}
