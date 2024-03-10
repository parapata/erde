package io.github.parapata.erde.core.exception;

import io.github.parapata.erde.Resource;

/**
 * ValidateException.
 *
 * @author parapata
 */
public class ValidateException extends AppException {

    private static final long serialVersionUID = 1L;

    public ValidateException(Resource message) {
        super(message.getValue());
    }

    public ValidateException(Throwable cause, Resource message, String... args) {
        super(message.createMessage(args));
    }
}
