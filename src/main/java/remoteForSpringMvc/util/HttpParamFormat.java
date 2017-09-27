package remoteForSpringMvc.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fangiming on 2017/9/17.
 */
public class HttpParamFormat {

    public static Map<String , String> generateParam(String paramName , Class<?> paramClass, Object paramValue) throws Exception{
        Map<String , String> result = new HashMap<String , String>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(paramValue != null){
            if(paramClass.isPrimitive() || Number.class.isAssignableFrom(paramClass) || String.class.isAssignableFrom(paramClass)){
                result.put(paramName, paramValue + "");
            }else if(paramValue instanceof Collection || paramClass.isArray()){
                Object[] array = null;
                if(paramValue instanceof Collection){
                    Collection<Object> collection = (Collection)paramValue;
                    array = collection.toArray(new Object[0]);
                }
                if(paramClass.isArray()){
                    array = (Object[]) paramValue;
                }
                if(array != null){
                    for(int i =0;i<array.length;i++){
                        Object item = array[i];
                        if(isBaseType(item)){
                            String str = result.get(paramName);
                            if(str == null || str.trim().length()<=0){
                                str = item+"";
                            }else{
                                str += ","+item;
                            }
                            result.put(paramName, str);
                        }else{
                            Field[] fieldArray = item.getClass().getDeclaredFields();
                            for(Field field : fieldArray){
                                field.setAccessible(true);
                                String str = result.get(field.getName());
                                if(str == null || str.trim().length()<=0){
                                    str = field.get(item)+"";
                                }else{
                                    str += ","+field.get(item);
                                }
                                result.put(field.getName(), str);
                            }
                        }
                    }
                }
            }else if(Date.class.isAssignableFrom(paramClass)){
                result.put(paramName, format.format((Date)paramValue));
            }else if(paramClass.isEnum()){
                result.put(paramName , ((Enum)paramValue).name());
            }
            else {
                Field[] fieldArray = paramClass.getDeclaredFields();
                for(Field field : fieldArray){
                    field.setAccessible(true);
                    Object fieldValue = field.get(paramValue);
                    if(fieldValue != null){
                        String str = result.get(field.getName());

                        if(str == null || str.trim().length()<=0){
                            str = field.get(paramValue)+"";
                        }else{
                            str += ","+field.get(paramValue);
                        }

                        result.put(field.getName(), str);
                    }
                }
                Class<?> paramClazz = paramClass.getSuperclass();
                while(!paramClazz.equals(Object.class)){
                    result.putAll(generateParam(null , paramClazz,paramValue ));
                    paramClazz = paramClazz.getSuperclass();
                }
            }
        }
        return result;
    }

    private static boolean isBaseType(Object object){
        return object.getClass().isPrimitive() || object instanceof Number || object instanceof Character || object instanceof String;
    }
}
