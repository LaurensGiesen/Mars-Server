package be.howest.ti.mars.logic.exceptions;

public class CropTypeException extends RuntimeException {
    public CropTypeException() {
        super();
    }

    public CropTypeException(String message) {
        super(message);
    }

    public CropTypeException(String message, Throwable ex) {
        super(message, ex);
    }
}
