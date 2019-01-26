package controllers;

import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Singleton;

@Singleton
public class Application extends Controller {

    public Application(){

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

    public Result getData(Long id){
        return  ok();
    }

    public Result createData(){
        return ok();
    }

    public Result updateData(Long id){
        return ok();
    }

    public Result deleteData(Long id){
        return ok();
    }
}
