package com.ifsp.edu.hto.sge.relatorios.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Response<T> {

    private T data;
    private Optional<List<String>> errors;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<String> getErrors() {
        if(!Optional.ofNullable(this.errors).isPresent()) {
            this.errors = Optional.of(new ArrayList<String>());
        }
        return errors.get();

    }

    public void setErrors(List<String> errors) {
        this.errors = Optional.ofNullable(errors);
    }
}
