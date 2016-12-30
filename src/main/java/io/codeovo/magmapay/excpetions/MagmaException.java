package io.codeovo.magmapay.excpetions;

public class MagmaException extends Exception {
    public MagmaException() {}

    public MagmaException(String alertMessage) { super(alertMessage); }

    public MagmaException(Throwable cause) { super(cause); }

    public MagmaException(String message, Throwable cause) { super(message, cause); }
}