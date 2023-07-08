package skn.lms.infra.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import skn.lms.exception.handler.GlobalExceptionHandler;

@Slf4j
@ControllerAdvice
public class FabricServiceExceptionHandler extends GlobalExceptionHandler {}
