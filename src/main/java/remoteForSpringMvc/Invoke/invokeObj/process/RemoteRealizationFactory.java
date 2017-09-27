package remoteForSpringMvc.Invoke.invokeObj.process;

import remoteForSpringMvc.Annotation.FileSub;
import remoteForSpringMvc.Annotation.GetSub;
import remoteForSpringMvc.Annotation.PostSub;
import remoteForSpringMvc.Exception.ParamAnnotationError;
import remoteForSpringMvc.Invoke.invokeObj.BaseInvokeProcessor;
import remoteForSpringMvc.util.ReflectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by fangiming on 2017/9/25.
 */
public class RemoteRealizationFactory {

    public static ProcressInvoke getRemoteCallRealization(Method method ,Object[] params,BaseInvokeProcessor invokeProcessor){
        if(isContainAnnotations(method,params,PostSub.class))
            return new PostRemoteDateInvoke( invokeProcessor, method,params);
        if(isContainAnnotations(method,params,GetSub.class))
            return new GetRemoteDataInvoke(invokeProcessor, method,params);
        if(isContainAnnotations(method,params,FileSub.class))
            return new FileUploadInvoke( invokeProcessor, method,params);
        throw new ParamAnnotationError(method.getName());
    }

    protected  static <T> boolean isContainAnnotations(Method method ,Object[] params,Class<T> klass){
        Annotation[][]parameterAnnotations = method.getParameterAnnotations();
        for(int i =0 ; i<params.length ; i++){
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            T ParamAnnotation = ReflectUtils.findAnnotation(parameterAnnotation, klass);
            if(ParamAnnotation!=null)
                return true;
        }
        return false;
    }

}
