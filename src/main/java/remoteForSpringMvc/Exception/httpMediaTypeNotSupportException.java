package remoteForSpringMvc.Exception;

/**
 * Created by fangiming on 2017/9/17.
 */
public class httpMediaTypeNotSupportException extends RuntimeException{

    public httpMediaTypeNotSupportException() {
    }

    public httpMediaTypeNotSupportException(String message) {
        super(message);
    }
}
