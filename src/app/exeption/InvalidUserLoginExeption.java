package app.exeption;

public class InvalidUserLoginExeption extends Exception{
    public InvalidUserLoginExeption() {
    }

    public InvalidUserLoginExeption(String message) {
        super(message);
    }

    public InvalidUserLoginExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUserLoginExeption(Throwable cause) {
        super(cause);
    }
}
