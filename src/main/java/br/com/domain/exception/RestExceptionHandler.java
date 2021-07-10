package br.com.domain.exception;

import br.com.domain.domain.ErrorDTO;
import br.com.domain.domain.ErrorDetail;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<ErrorDTO> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> buildError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        return handleExceptionInternal(
                ex, ErrorDetail.builder()
                        .addDetalhe("Error validation")
                        .addErro(errors)
                        .addStatus(HttpStatus.BAD_REQUEST)
                        .addHttpMethod(getHttpMethod(request))
                        .addPath(getPath(request))
                        .build(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ViolationConstraintException.class})
    public ResponseEntity<Object> violationConstraintException(ViolationConstraintException ex,
                                                    WebRequest request) {

        List<ErrorDTO> list = new ArrayList<>();
        String[] errorFeatures = ex.getMessage().split("-");
        list.add(buildError(errorFeatures[0], errorFeatures[1]));

        return handleExceptionInternal(
                ex, ErrorDetail.builder()
                        .addDetalhe("Violated constraint")
                        .addErro(list)
                        .addStatus(HttpStatus.CONFLICT)
                        .addHttpMethod(getHttpMethod(request))
                        .addPath(getPath(request))
                        .build(),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler({InvalidUserException.class})
    public ResponseEntity<Object> invalidUserException(InvalidUserException ex, WebRequest request) {

        List<ErrorDTO> list = new ArrayList<>();
        String[] errorFeatures = ex.getMessage().split("-");
        list.add(buildError(errorFeatures[0], errorFeatures[1]));

        return handleExceptionInternal(
                ex, ErrorDetail.builder()
                        .addDetalhe("Invalid User")
                        .addErro(list)
                        .addStatus(HttpStatus.FORBIDDEN)
                        .addHttpMethod(getHttpMethod(request))
                        .addPath(getPath(request))
                        .build(),
                new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<Object> noSuchElementException(NoSuchElementException ex, WebRequest request) {

        List<ErrorDTO> list = new ArrayList<>();
        String[] errorFeatures = ex.getMessage().split("-");
        list.add(buildError(errorFeatures[0], errorFeatures[1]));

        return handleExceptionInternal(
                ex, ErrorDetail.builder()
                        .addDetalhe("Invalid Data")
                        .addErro(list)
                        .addStatus(HttpStatus.BAD_REQUEST)
                        .addHttpMethod(getHttpMethod(request))
                        .addPath(getPath(request))
                        .build(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> entityNotFoundException(EntityNotFoundException ex, WebRequest request) {

        List<ErrorDTO> list = new ArrayList<>();
        if(ex.getMessage().contains("CardSituation")) list.add(buildError("CardSituation.id", "invalid"));
        if(ex.getMessage().contains("Customer")) list.add(buildError("Customer.id", "invalid"));

        return handleExceptionInternal(
                ex, ErrorDetail.builder()
                        .addDetalhe("Violated constraint")
                        .addErro(list)
                        .addStatus(HttpStatus.CONFLICT)
                        .addHttpMethod(getHttpMethod(request))
                        .addPath(getPath(request))
                        .build(),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    private String getPath(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }

    private String getHttpMethod(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getMethod();
    }

    private ErrorDTO buildError(String code, String message) {
        return new ErrorDTO(code, message);
    }
}