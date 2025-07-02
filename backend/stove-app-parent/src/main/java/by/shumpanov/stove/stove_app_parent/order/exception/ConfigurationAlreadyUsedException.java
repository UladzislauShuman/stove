package by.shumpanov.stove.stove_app_parent.order.exception;

public class ConfigurationAlreadyUsedException extends RuntimeException {
    public ConfigurationAlreadyUsedException(String message) {
        super(message);
    }
}
