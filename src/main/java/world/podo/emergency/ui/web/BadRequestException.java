package world.podo.emergency.ui.web;

class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = -7417594578893900704L;

    BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
