package com.ifsp.edu.hto.sge.notificador.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {

    private static final long serialVersionUID = 1L;

    private Map<String, String> headers;

    private String payload;

    public Message() {
    }

    public Message(Map<String, String> headers, String payload) {
        this.headers = headers;
        this.payload = payload;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getPayload() {
        return this.payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }


    public String toString() {
        return "Message [headers=" + this.headers + ", payload=" + this.payload + "]";
    }


}
