package remoteForSpringMvc.util.mediaTypeFactory;

import java.util.Map;

/**
 * Created by fangiming on 2017/9/17.
 */
public interface BodyInterface {

    public Object getBodyValue(Map<String, Object> bodyParams);
}
