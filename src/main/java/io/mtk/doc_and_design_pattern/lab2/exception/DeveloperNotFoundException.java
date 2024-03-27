package io.mtk.doc_and_design_pattern.lab2.exception;

public class DeveloperNotFoundException extends RuntimeException {
    public DeveloperNotFoundException(Integer id) {
        super(String.format("Could not find 'city' with id=%s", id));
    }
}