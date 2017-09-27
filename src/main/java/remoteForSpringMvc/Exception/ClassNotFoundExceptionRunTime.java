package remoteForSpringMvc.Exception;

/**
 * Created by fangiming on 2017/9/17.
 */
public class ClassNotFoundExceptionRunTime extends RuntimeException {

    public ClassNotFoundExceptionRunTime() {
    }

    public ClassNotFoundExceptionRunTime(String message) {
        super(message);
    }
}
