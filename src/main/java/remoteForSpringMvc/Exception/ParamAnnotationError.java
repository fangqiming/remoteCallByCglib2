package remoteForSpringMvc.Exception;

/**
 * Created by fangiming on 2017/9/26.
 */
public class ParamAnnotationError extends  RuntimeException {

    public ParamAnnotationError() {
    }

    public ParamAnnotationError(String message) {
        super(message);
    }
}
