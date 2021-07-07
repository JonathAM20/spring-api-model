package br.com.domain.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetail implements Serializable {

    public static final String STATUS_CODE = "statusCode";
    public static final String STATUS_MESSAGE = "statusMessage";
    public static final String HTTP_METHOD = "httpMethod";
    public static final String DETAIL = "detail";
    public static final String PATH = "path";

    private Integer statusCode;
    private String statusMessage;
    private String httpMethod;
    private List<ErrorDTO> erros;
    private String detail;
    private String path;

    public ErrorDetail(){
        erros = new ArrayList<>();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public List<ErrorDTO> getErros() {
        return erros;
    }

    public String getDetail() {
        return detail;
    }

    public String getPath() {
        return path;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private ErrorDetail erro;

        Builder() {
            this.erro = new ErrorDetail();
        }

        public Builder addStatus(HttpStatus status) {
            this.erro.statusCode = status.value();
            this.erro.statusMessage = status.getReasonPhrase();
            return this;
        }

        public Builder addHttpMethod(String method) {
            this.erro.httpMethod = method;
            return this;
        }

        public Builder addErro(List<ErrorDTO> erro) {
            this.erro.erros.addAll(erro);
            return this;
        }

        public Builder addDetalhe(String detalhe) {
            this.erro.detail = detalhe;
            return this;
        }

        public Builder addPath(String path) {
            this.erro.path = path;
            return this;
        }

        public ErrorDetail build() {
            return this.erro;
        }
    }
}