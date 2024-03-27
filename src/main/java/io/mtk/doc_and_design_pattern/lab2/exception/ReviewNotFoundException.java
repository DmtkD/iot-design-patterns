package io.mtk.doc_and_design_pattern.lab2.exception;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException(Integer id) {
        super(String.format("Could not find 'city' with id=%s", id));
    }
}