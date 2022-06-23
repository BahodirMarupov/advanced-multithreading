package jmp.workshop.task5.exception;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 23/06/2022
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
