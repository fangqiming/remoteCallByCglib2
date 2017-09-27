package remoteForSpringMvc.Invoke.invokeObj.process;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import remoteForSpringMvc.Invoke.invokeObj.BaseInvokeProcessor;
import remoteForSpringMvc.util.HttpSignCalculator;
import remoteForSpringMvc.util.ReflectUtils;

import javax.ws.rs.Consumes;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fangiming on 2017/9/25.
 */
public abstract class ProcressInvoke {

    public Logger logger=Logger.getLogger(getClass());

    public BaseInvokeProcessor invokeProcessor;

    public Method method;

    public Object[] params;

    public String methodUrl;

    public ProcressInvoke(BaseInvokeProcessor invokeProcessor,Method method,Object[] params){
        this.invokeProcessor=invokeProcessor;
        this.method=method;
        this.params=params;
        this.methodUrl= getTypeAnnPath()+"?"+getMethodAnnPath();
    }

    public abstract Object invoke();

    protected String getTypeAnnPath(){
        String result="";
        if (StringUtils.isNotEmpty(ReflectUtils.getClassUrl(invokeProcessor.getTargetClass()))) {
            result = ReflectUtils.getClassUrl(invokeProcessor.getTargetClass());
        }
        return result;
    }

    protected String getMethodAnnPath(){
        String result="";
        if (StringUtils.isNotEmpty(ReflectUtils.getMethodUrl(method))) {
            result= ReflectUtils.getMethodUrl(method);
        }
        return result;
    }

    protected void formateParam(Object paramValue,Map params,String paramName){
        if(paramValue == null){
            params.put(paramName, "");
        }else if(paramValue.getClass().isArray() || paramValue instanceof Collection){
            Object[] array = null;
            if(paramValue.getClass().isArray()){
                array = (Object[]) paramValue;
            }else{
                Collection<Object> collection = (Collection<Object>)paramValue;
                array = collection.toArray(new Object[0]);
            }
            StringBuffer sb = new StringBuffer();
            for(Object item: array){
                if(sb.length()<=0){
                    sb.append(item);
                }else{
                    sb.append(",").append(item);
                }
            }
            params.put(paramName, sb.toString());
        }else{
            params.put(paramName, JSONObject.toJSON(paramValue).toString());
        }
    }

    protected MediaType getMediaType(){
        MediaType mediaType=createMediaType();
        if(invokeProcessor.getTargetClass().isAnnotationPresent(Consumes.class)){
            Consumes consume = (Consumes) invokeProcessor.getTargetClass().getAnnotation(Consumes.class);
            if(consume != null && consume.value().length>0){
                mediaType = MediaType.valueOf(consume.value()[0]);
            }
        }
        return mediaType;
    }

    private MediaType createMediaType(){
        Map<String , String> param = new HashMap<String , String>();
        param.put("charset", "UTF-8");
        return new MediaType(MediaType.APPLICATION_FORM_URLENCODED , param);
    }


    protected Map<String,String> getSign(){
        Map<String , String> headerMap = new HashMap<String , String>();
        if(invokeProcessor.isNeedSign()){
            String sign = HttpSignCalculator.calculateSign(invokeProcessor.getSignKey());
            headerMap.put(invokeProcessor.getSignName(), sign);
        }
        return headerMap;
    }
}
