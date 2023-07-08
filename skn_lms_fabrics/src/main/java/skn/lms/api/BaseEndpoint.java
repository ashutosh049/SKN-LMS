package skn.lms.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import skn.lms.exception.ErrorResponse;

public interface BaseEndpoint {
  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  default ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException exception) {
    List<String> errors =
        exception.getBindingResult().getFieldErrors().stream()
            .map(x -> MessageFormat.format("`{0}` {1}", x.getField(), x.getDefaultMessage()))
            .collect(Collectors.toList());
    return ResponseEntity.badRequest().body(ErrorResponse.builder().errors(errors).build());
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  default ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException e) {

    return ResponseEntity.badRequest()
        .body(ErrorResponse.of(HttpStatus.BAD_REQUEST, String.format("Invalid '%s'", e.getName())));
  }
}
