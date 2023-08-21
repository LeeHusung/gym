package workout.gym.common.exception;

public class CanNotDeleteItemWithHasOrder extends RuntimeException {
    public CanNotDeleteItemWithHasOrder(String message) {
        super(message);
    }
}
