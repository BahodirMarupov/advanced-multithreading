package jmp.workshop.task5.exception;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 23/06/2022
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
