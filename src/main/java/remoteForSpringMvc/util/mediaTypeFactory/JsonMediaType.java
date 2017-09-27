package remoteForSpringMvc.util.mediaTypeFactory;

import com.alibaba.fastjson.JSON;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by fangiming on 2017/9/17.
 */
public class JsonMediaType extends MediaType implements BodyInterface {

    public JsonMediaType() {
        super("application", "json", Charset.forName("UTF-8"));
    }

    @Override
    public Object getBodyValue(Map<String, Object> bodyParams) {
        Object result=null;
        for(Map.Entry<String , Object> entry : bodyParams.entrySet()){							//遍历参数
            result = JSON.toJSONString(entry.getValue());										//给body赋值
        }
        return result;
    }

}
