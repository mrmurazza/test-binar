package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import product.ProductManager;
import user.IUserAuth;
import user.UserManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Application extends Controller {
    private ProductManager productManager;
    private UserManager userManager;

    @Inject
    public Application(ProductManager productManager, UserManager userManager){
        this.productManager = productManager;
        this.userManager = userManager;
    }

    public Result test(){
        return ok("Hello There");
    }

    public Result signup(){
        return ok();
    }

    public Result login(){
        return ok();
    }

    public Result logout(){
        return ok();
    }

    public Result getProducts(Long id){
        return  ok();
    }

    public Result saveNewProduct(){
        return ok();
    }

    public Result updateProduct(Long id){
        return ok();
    }

    public Result deleteProduct(Long id){
        return ok();
    }
}
