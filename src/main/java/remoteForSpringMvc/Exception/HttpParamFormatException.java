package remoteForSpringMvc.Exception;

/**
 * Created by fangiming on 2017/9/17.
 */
public class HttpParamFormatException extends RuntimeException {

    public HttpParamFormatException() {
    }

    public HttpParamFormatException(String message) {
        super(message);
    }
}
