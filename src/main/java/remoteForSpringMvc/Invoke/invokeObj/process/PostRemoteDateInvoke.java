package remoteForSpringMvc.Invoke.invokeObj.process;

import org.springframework.http.MediaType;
import remoteForSpringMvc.Annotation.PostSub;
import remoteForSpringMvc.Invoke.invokeObj.BaseInvokeProcessor;
import remoteForSpringMvc.util.ReflectUtils;

import javax.ws.rs.HttpMethod;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fangiming on 2017/9/25.
 */
public class PostRemoteDateInvoke extends ProcressInvoke {

    public PostRemoteDateInvoke(BaseInvokeProcessor invokeProcessor, Method method, Object[] params){
        super( invokeProcessor, method, params);
    }

    @Override
    public Object invoke(){
        return getRemoteData();
    }

    public Object getRemoteData(){
        try{
            MediaType mediaType= getMediaType();
            Map<String , Object> bodyParams = new HashMap<String , Object>();
            if(params!=null){
                analysisParam( method , params,  bodyParams);
                Map<String , String> headerMap=getSign();
                return invokeProcessor.invoke(invokeProcessor.getRemoteBaseUrl(), methodUrl, HttpMethod.POST, headerMap, mediaType, method, params, bodyParams, null);
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error("rmi invoke error ",e);
        }
        return null;
    }



    private void analysisParam(Method method , Object[] params, Map<String , Object> bodyParams){
        Annotation[][]parameterAnnotations = method.getParameterAnnotations();
        for(int i =0 ; i<params.length ; i++){
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            Object paramValue = params[i];
            addPostQueryParam(parameterAnnotation, paramValue,bodyParams);
        }
    }

    public void addPostQueryParam(Annotation[] parameterAnnotation,Object paramValue,Map<String , Object> bodyParams){
        PostSub formParamAnnoation = ReflectUtils.findAnnotation(parameterAnnotation, PostSub.class);
        formateParam(paramValue,bodyParams,formParamAnnoation.value());
    }

}
