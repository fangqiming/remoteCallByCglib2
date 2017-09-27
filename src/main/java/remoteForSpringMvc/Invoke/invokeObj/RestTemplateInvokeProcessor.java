package remoteForSpringMvc.Invoke.invokeObj;

import com.alibaba.fastjson.JSON;
import org.springframework.http.MediaType;
import remoteForSpringMvc.util.HttpUtils;
import remoteForSpringMvc.util.StringTools;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * Created by fangiming on 2017/9/17.
 */
public class RestTemplateInvokeProcessor extends BaseInvokeProcessor {

    public RestTemplateInvokeProcessor(Class<?> targetClass, String remoteBaseUrl, String signName, boolean needSign, String signKey, String systemCode, String invokeType) {
        super(targetClass, remoteBaseUrl, signName, needSign, signKey, systemCode, invokeType);
    }

    @Override
    public Object invoke(String remoteBaseUrl, String methodUrl, String restfulMethod, Map<String, String> headerMap, MediaType mediaType, Method method, Object[] params, Map<String, Object> bodyParams, Map<String, String> queryParams) throws Exception {
        String requestUrl = StringTools.appendUrl(remoteBaseUrl, methodUrl);
        String json = HttpUtils.loadResult(requestUrl, queryParams, bodyParams, headerMap, restfulMethod, mediaType);
        Class<?> returnType = method.getReturnType();
        if(returnType != null){
            if(returnType.isArray() || returnType.isAssignableFrom(Collection.class)){
                return JSON.parseArray(json, returnType.getComponentType());
            }
            return JSON.parseObject(json , returnType);
        }
        return null;
    }
}
