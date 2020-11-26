package be.howest.ti.mars.logic.exceptions;

public class PlantException extends RuntimeException{
    public PlantException() {
        super();
    }
    public PlantException(String message){
        super(message);
    }
    public PlantException(String message, Throwable ex) {
        super(message,ex);
    }
}
