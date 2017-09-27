package remoteForSpringMvc.util;

import remoteForSpringMvc.Annotation.GetSub;

import javax.ws.rs.Path;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by fangiming on 2017/9/17.
 */
public class ReflectUtils {
    public static String getMethodUrl(Method method) {
        System.out.println(method.isAnnotationPresent(Path.class));
        if(method.isAnnotationPresent(Path.class)){
            Path resource = method.getAnnotation(Path.class);
            if(resource.value() != null && resource.value().length()>0){
                return resource.value();
            }
        }
        return null;
    }

    /**
     * 从 服务提供api上 获取请求地址
     * @return
     */
    public static String getClassUrl(Class<?> targetClass ) {
        if(targetClass.isAnnotationPresent(Path.class)){
            Path resource = (Path)targetClass.getAnnotation(Path.class);
            if(resource.value() != null && resource.value().length()>0){
                return resource.value();
            }
        }
        return null;
    }

    /**
     * 判断是基于request params 或 request body
     * @param annotationArray
     * @return
     */
    public static boolean bodyParam(Annotation[] annotationArray){
        return annotationArray != null ? findAnnotation(annotationArray,GetSub.class)==null : true;
    }

    @SuppressWarnings("unchecked")
    public static <T> T findAnnotation(Annotation[] annotationArray , Class<T> clazz){
        if(annotationArray != null){
            for(Annotation an : annotationArray){
                if(clazz.isAssignableFrom(an.annotationType())){
                    return (T)an;
                }
            }
        }
        return null;
    }
}
