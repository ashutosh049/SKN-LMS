package skn.lms.exception.type;

import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

public interface Failing {
  Supplier<String> getMessage();

  Supplier<HttpStatus> getHttpStatus();
}
