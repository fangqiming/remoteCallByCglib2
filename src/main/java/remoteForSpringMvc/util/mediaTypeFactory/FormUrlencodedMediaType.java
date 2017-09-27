package remoteForSpringMvc.util.mediaTypeFactory;

import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import remoteForSpringMvc.Exception.HttpParamFormatException;
import remoteForSpringMvc.util.HttpParamFormat;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by fangiming on 2017/9/17.
 */
public class FormUrlencodedMediaType extends MediaType implements BodyInterface {

    public FormUrlencodedMediaType() {
        super("application", "x-www-form-urlencoded", Charset.forName("UTF-8"));
    }

    @Override
    public Object getBodyValue(Map<String , Object> bodyParams) {
        MultiValueMap<String , String> bodyMap = new LinkedMultiValueMap<String, String>();
        for(Map.Entry<String , Object> entry : bodyParams.entrySet()){
            try {
                bodyMap.setAll(HttpParamFormat.generateParam(entry.getKey(), entry.getValue().getClass(), entry.getValue()));
            } catch (Exception e) {
                throw new HttpParamFormatException(bodyParams.toString());
            }
        }
        return bodyMap;
    }
}
