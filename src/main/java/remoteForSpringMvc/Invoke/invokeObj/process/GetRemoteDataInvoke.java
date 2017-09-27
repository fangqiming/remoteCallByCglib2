package remoteForSpringMvc.Invoke.invokeObj.process;

import org.springframework.http.MediaType;
import remoteForSpringMvc.Annotation.GetSub;
import remoteForSpringMvc.Invoke.invokeObj.BaseInvokeProcessor;
import remoteForSpringMvc.util.ReflectUtils;

import javax.ws.rs.HttpMethod;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fangiming on 2017/9/26.
 */
public class GetRemoteDataInvoke extends ProcressInvoke {

    public GetRemoteDataInvoke(BaseInvokeProcessor invokeProcessor, Method method, Object[] params){
        super( invokeProcessor, method, params);
    }

    @Override
    public Object invoke() {
        return getRemoteData();
    }

    public Object getRemoteData(){
        try{
            MediaType mediaType= getMediaType();
            Map<String , String> queryParams = new HashMap<String , String>();
            if(params!=null){
                analysisParam( method , params, queryParams);
                Map<String , String> headerMap=getSign();
                return invokeProcessor.invoke(invokeProcessor.getRemoteBaseUrl(), methodUrl, HttpMethod.GET, headerMap, mediaType, method, params, null, queryParams);
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error("rmi invoke error ",e);
        }
        return null;
    }

    private void analysisParam(Method method , Object[] params,Map<String , String> queryParams){
        Annotation[][]parameterAnnotations = method.getParameterAnnotations();
        for(int i =0 ; i<params.length ; i++){
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            Object paramValue = params[i];
            addGetQueryParam(parameterAnnotation, paramValue, queryParams);
        }
    }

    public void addGetQueryParam(Annotation[] parameterAnnotation,Object paramValue,Map<String , String> queryParams){
        GetSub queryParamAnnotation = ReflectUtils.findAnnotation(parameterAnnotation, GetSub.class);
        if(queryParamAnnotation!=null){
            formateParam(paramValue,queryParams,queryParamAnnotation.value());
        }
    }

}
