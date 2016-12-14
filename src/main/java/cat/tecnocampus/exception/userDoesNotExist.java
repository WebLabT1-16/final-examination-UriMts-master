package cat.tecnocampus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="L'element ya existeix")
public class userDoesNotExist extends Exception{
    //LANZAMOS LA EXCEPCION AL TRATAR DE INSERTAR UN ELEMENTO YA EXISTENTE
    public userDoesNotExist(String message){super(message);}
}
