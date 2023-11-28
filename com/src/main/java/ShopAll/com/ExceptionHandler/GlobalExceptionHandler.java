package ShopAll.com.ExceptionHandler;

import ShopAll.com.Exception.UserNotFoundException;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
//@RestControllerAdvice("com.apiRest.myFirstApi.controller") //Por paquetes
//@RestControllerAdvice(assignableTypes = TaskController.class) //Por clase
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.OK)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException ex){
        //TO DO
        return (ErrorResponse) new UserNotFoundException(ex.getMessage());
    }
}
