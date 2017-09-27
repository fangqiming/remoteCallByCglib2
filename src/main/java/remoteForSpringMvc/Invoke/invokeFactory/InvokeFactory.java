package remoteForSpringMvc.Invoke.invokeFactory;

import org.apache.commons.lang3.StringUtils;
import remoteForSpringMvc.Invoke.invokeObj.BaseInvokeProcessor;
import remoteForSpringMvc.Invoke.invokeObj.RestTemplateInvokeProcessor;

/**
 * Created by fangiming on 2017/9/17.
 */
public class InvokeFactory {
    public static BaseInvokeProcessor getInvokeClass(Class<?> targetClass, String url, String signName, boolean needSign,
                                                     String signKey, String systemCode, String invokeType) throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        if (StringUtils.isEmpty(invokeType)) {
            return null;
        }
        return new RestTemplateInvokeProcessor(targetClass, url, signName, needSign, signKey, systemCode, invokeType);
    }
}
