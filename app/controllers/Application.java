package controllers;

import actions.LoggedIn;
import exceptions.ListException;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import product.Product;
import product.ProductManager;
import request.FormRequest;
import request.JsonRequest;
import request.ProductRequest;
import request.UserSignupRequest;
import response.BaseJSONResponse;
import user.User;
import user.UserManager;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
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

    public Result test(){
        return ok(Json.toJson(createSingleEntryMap("message", "Hello there")));
    }

    public Result signupPost(){
        UserSignupRequest request = UserSignupRequest.initFromFormRequest(new FormRequest(request()));
        User user;

        try {
            user = userManager.signup(request);
        } catch (ListException e){
            return badRequest(Json.toJson(BaseJSONResponse.initErrorResponse(e.getErrors())));
        }

        return ok(Json.toJson(BaseJSONResponse.initSuccessResponse(user)));
    }

    public Result login(){
        JsonRequest request = new JsonRequest(request());
        String email = request.getOptionalObject("email", String.class).orElse(null);
        String password = request.getOptionalObject("password", String.class).orElse(null);

        Optional<User> user = userManager.login(email, password);

        if (user.isPresent())
            return ok(Json.toJson(BaseJSONResponse.initSuccessResponse(createSingleEntryMap("access_token", user.get().getAccessToken()))));

        return badRequest(Json.toJson(BaseJSONResponse.initSingleErrorResponse("Login error, please retry!")));
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

        BaseJSONResponse response = BaseJSONResponse.initSuccessResponse(createSingleEntryMap("message", id + " deleted"));
        return ok(Json.toJson(response));
    }

    private static <K, V> Map<K,V> createSingleEntryMap(K key, V value){
        Map<K,V> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
}
