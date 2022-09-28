package co.com.sofkoin.beta.infrastructure.web.middlewares;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class IllegalStateExceptionHandler {

  @ExceptionHandler({ IllegalStateException.class })
  public ResponseEntity<Object> illegalStateExceptionHandler(IllegalStateException exception) {
    HashMap<String, Object> body = new HashMap<>();
    body.put("errorMessage", exception.getMessage());

    return new ResponseEntity<>(
            body,
            HttpStatus.NO_CONTENT
    );
  }
}
