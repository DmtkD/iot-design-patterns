package io.mtk.doc_and_design_pattern.lab2.exception;

public class AppNotFoundException extends RuntimeException {
    public AppNotFoundException(Integer id) {
        super(String.format("Could not find 'city' with id=%s", id));
    }
}