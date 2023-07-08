package skn.lms.infra.exception;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

@Slf4j
@UtilityClass
public class ExceptionUtil {

  public static ErrorResponseException buildErrorResponseException(HttpStatus httpStatus, String message) {
    HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(httpStatus.value());
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatusCode, message);
    return new ErrorResponseException(httpStatusCode, problemDetail, null);
  }

  public static void buildAndThrow(HttpStatus httpStatus, String message) {
    ErrorResponseException ex =buildErrorResponseException(httpStatus, message);
    log.warn("", ex);
    throw ex;
  }
}
