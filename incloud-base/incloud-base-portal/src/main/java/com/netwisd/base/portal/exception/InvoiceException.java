package com.netwisd.base.portal.exception;

public class InvoiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvoiceException(String message) {
        super(message);
    }

    public InvoiceException(Throwable cause) {
        super(cause);
    }

    public InvoiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvoiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
