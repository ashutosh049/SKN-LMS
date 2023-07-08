
package skn.lms.exception.type;

import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

public enum FailureTypePool implements Failing {
  BAD_REQUEST_FAILURE() {
    @Override
    public Supplier<String> getMessage() {
      return () -> "Bad request";
    }

    @Override
    public Supplier<HttpStatus> getHttpStatus() {
      return () -> HttpStatus.BAD_REQUEST;
    }
  },
  INTERNAL_SERVER_ERROR_FAILURE() {
    @Override
    public Supplier<String> getMessage() {
      return () -> "Something went wrong at our end";
    }

    @Override
    public Supplier<HttpStatus> getHttpStatus() {
      return () -> HttpStatus.INTERNAL_SERVER_ERROR;
    }
  },
  DOWNSTREAM_SERVICE_UNAVAILABLE_EXCEPTION() {
    @Override
    public Supplier<String> getMessage() {
      return () -> "Downstream app service unavailable";
    }

    @Override
    public Supplier<HttpStatus> getHttpStatus() {
      return () -> HttpStatus.SERVICE_UNAVAILABLE;
    }
  },
  TIMEOUT() {
    @Override
    public Supplier<String> getMessage() {
      return () -> "Timeout";
    }

    @Override
    public Supplier<HttpStatus> getHttpStatus() {
      return () -> HttpStatus.GATEWAY_TIMEOUT;
    }
  };
}
