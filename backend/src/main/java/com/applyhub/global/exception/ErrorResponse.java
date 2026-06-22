package com.applyhub.global.exception;

public record ErrorResponse(
        String code,
        String message) {
}