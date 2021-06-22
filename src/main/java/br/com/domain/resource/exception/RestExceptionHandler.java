package br.com.domain.resource.exception;

import br.com.domain.domain.ErrorDetail;
import br.com.domain.exception.IdInvalidServiceException;
import br.com.domain.exception.NotExistDaoException;
import br.com.domain.exception.ViolationConstraint;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({IdInvalidServiceException.class})
    public ResponseEntity<Object> idInvalido(IdInvalidServiceException ex,
                                                    WebRequest request) {

        return handleExceptionInternal(
                ex, ErrorDetail.builder()
                        .addErro(ex.getMessage())
                        .addStatus(HttpStatus.BAD_REQUEST)
                        .addHttpMethod(getHttpMethod(request))
                        .addPath(getPath(request))
                        .build(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

//    @ExceptionHandler({org.hibernate.exception.ConstraintViolationException.class})
//    public ResponseEntity<Object> constraintVioladaPadrao(org.hibernate.exception.ConstraintViolationException ex,
//                                                    WebRequest request) {
//
//        return handleExceptionInternal(
//                ex, DetalheErro.builder()
//                        .addDetalhe("Constraint violada: " + ex.getConstraintName())
//                        .addErro(ex.getMessage())
//                        .addStatus(HttpStatus.CONFLICT)
//                        .addHttpMethod(getHttpMethod(request))
//                        .addPath(getPath(request))
//                        .build(),
//                new HttpHeaders(), HttpStatus.CONFLICT, request);
//    }

    @ExceptionHandler({ViolationConstraint.class})
    public ResponseEntity<Object> constraintViolada(ViolationConstraint ex,
                                                    WebRequest request) {

        return handleExceptionInternal(
                ex, ErrorDetail.builder()
                        .addDetalhe("Constraint violada.")
                        .addErro(ex.getMessage())
                        .addStatus(HttpStatus.CONFLICT)
                        .addHttpMethod(getHttpMethod(request))
                        .addPath(getPath(request))
                        .build(),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler({org.hibernate.PropertyValueException.class})
    public ResponseEntity<Object> propriedadeNula(org.hibernate.PropertyValueException ex, WebRequest request) {

        return handleExceptionInternal(
                ex, ErrorDetail.builder()
                        .addDetalhe("O atributo '"+ ex.getPropertyName() +"' não pode ser nulo.")
                        .addErro(ex.getMessage())
                        .addStatus(HttpStatus.BAD_REQUEST)
                        .addHttpMethod(getHttpMethod(request))
                        .addPath(getPath(request))
                        .build(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({NotExistDaoException.class})
    public ResponseEntity<Object> entidadeNaoEncontrada(NotExistDaoException ex, WebRequest request) {

        return handleExceptionInternal(
                ex, ErrorDetail.builder()
                        .addDetalhe("Recurso não encontrado na base de dados.")
                        .addErro(ex.getMessage())
                        .addStatus(HttpStatus.NOT_FOUND)
                        .addHttpMethod(getHttpMethod(request))
                        .addPath(getPath(request))
                        .build(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class})
    public ResponseEntity<Object> serverException(RuntimeException ex, WebRequest request) {

        return handleExceptionInternal(
                ex, ErrorDetail.builder()
                        .addDetalhe("Um exceção foi lançada.")
                        .addErro(ex.getMessage())
                        .addStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .addHttpMethod(getHttpMethod(request))
                        .addPath(getPath(request))
                        .build(),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> serverExceptionTest(Exception ex, WebRequest request) {

        return handleExceptionInternal(
                ex, ErrorDetail.builder()
                        .addDetalhe("Um exceção foi lançada. (TESTE)")
                        .addErro(ex.getMessage())
                        .addStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .addHttpMethod(getHttpMethod(request))
                        .addPath(getPath(request))
                        .build(),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private String getPath(WebRequest request) {

        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }

    private String getHttpMethod(WebRequest request) {

        return ((ServletWebRequest) request).getRequest().getMethod();
    }
}