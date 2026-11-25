package cl.duoc.auth.exception;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<?> credencialesInvalidas(BadCredentialsException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
      .body(Map.of("mensaje", ex.getMessage()));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> correoDuplicado(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
      .body(Map.of("mensaje", ex.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> errorValidacion(MethodArgumentNotValidException ex) {
    String mensaje = ex.getBindingResult().getFieldErrors().stream()
      .findFirst()
      .map(err -> err.getDefaultMessage())
      .orElse("Datos inválidos en la solicitud");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
      .body(Map.of("mensaje", mensaje));
  }
}