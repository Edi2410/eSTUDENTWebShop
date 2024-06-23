package hr.estudent.webshop.estudentwebshop.exceptions;

public class CategoryInUseException extends RuntimeException {
    public CategoryInUseException(String message) {
        super(message);
    }
}

