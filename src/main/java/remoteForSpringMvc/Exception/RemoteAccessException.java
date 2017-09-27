package remoteForSpringMvc.Exception;

/**
 * Created by fangiming on 2017/9/17.
 */
public class RemoteAccessException extends RuntimeException{

    public RemoteAccessException() {
    }

    public RemoteAccessException(String message) {
        super(message);
    }
}
