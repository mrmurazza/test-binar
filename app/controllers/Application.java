package controllers;

import actions.LoggedIn;
import exceptions.ListException;
import javafx.util.Pair;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import product.Product;
import product.ProductManager;
import request.JsonRequest;
import request.ProductRequest;
import response.BaseJSONResponse;
import user.User;
import user.UserManager;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Singleton
public class Application extends Controller {
    private ProductManager productManager;
    private UserManager userManager;

    @Inject
    public Application(ProductManager productManager, UserManager userManager){
        this.productManager = productManager;
        this.userManager = userManager;
    }

    @Security.Authenticated(LoggedIn.class)
    public Result test(){
        return ok("Hello There");
    }

    public Result signup(){
        return ok();
    }

    public Result login(){
        return ok();
    }

    @Security.Authenticated(LoggedIn.class)
    public Result getProducts(Long id){
        User user = userManager.getCurrentUser().get();

        Object result;
        if (id == null) {
            result = productManager.getByUserId(user.getId());
        } else {
            try {
                result = productManager.getValidatedProductById(id, user.getId());
            } catch (ListException e){
                return badRequest(Json.toJson(BaseJSONResponse.initErrorResponse(e.getErrors())));
            }
        }

        return  ok(Json.toJson(BaseJSONResponse.initSuccessResponse(result)));
    }

    @Security.Authenticated(LoggedIn.class)
    public Result saveNewProduct(){
        ProductRequest request = ProductRequest.initFromJsonRequest(new JsonRequest(request()));
        User user = userManager.getCurrentUser().get();
        Product product;
        try {
            product = productManager.createNewProduct(request, user.getId());
        } catch (ListException e){
            return internalServerError(Json.toJson(BaseJSONResponse.initErrorResponse(e.getErrors())));
        }

        return ok(Json.toJson(BaseJSONResponse.initSuccessResponse(product)));
    }

    @Security.Authenticated(LoggedIn.class)
    public Result updateProduct(Long id){
        ProductRequest request = ProductRequest.initFromJsonRequest(new JsonRequest(request()));
        User user = userManager.getCurrentUser().get();
        Product product;
        try {
            product = productManager.updateProduct(request, id, user.getId());
        } catch (ListException e){
            return internalServerError(Json.toJson(BaseJSONResponse.initErrorResponse(e.getErrors())));
        }

        return ok(Json.toJson(BaseJSONResponse.initSuccessResponse(product)));
    }

    @Security.Authenticated(LoggedIn.class)
    public Result deleteProduct(Long id){
        User user = userManager.getCurrentUser().get();
        try {
            productManager.deleteById(id, user.getId());
        } catch (ListException e){
            return internalServerError(Json.toJson(BaseJSONResponse.initErrorResponse(e.getErrors())));
        }

        BaseJSONResponse response = BaseJSONResponse.initSuccessResponse(new Pair<>("message", id + " deleted"));
        return ok(Json.toJson(response));
    }
}
