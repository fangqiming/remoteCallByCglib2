package remoteForSpringMvc.Invoke.invokeObj;

import org.springframework.http.MediaType;
import remoteForSpringMvc.Invoke.invokeObj.process.RemoteRealizationFactory;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by fangiming on 2017/9/17.
 * 说明:本方法用于所有处理类的抽象类
 */
public abstract class BaseInvokeProcessor {
    private Class<?> targetClass;
    private String remoteBaseUrl;
    private String signName;
    private boolean needSign;
    private String signKey ;
    private String systemCode;
    private String invokeType;

    public BaseInvokeProcessor(Class<?> targetClass , String remoteBaseUrl, String signName, boolean needSign , String signKey , String systemCode , String invokeType) {
        this.targetClass = targetClass;
        this.remoteBaseUrl = remoteBaseUrl;
        this.signName = signName;
        this.needSign = needSign;
        this.signKey = signKey;
        this.systemCode = systemCode;
        this.invokeType = invokeType;
    }

    @SuppressWarnings("unchecked")
    public Object process(Method method ,  Object[] params) throws Exception  {
        return RemoteRealizationFactory.getRemoteCallRealization(method, params, this).invoke();
    }

    public abstract Object invoke(String endPoint , String methodUrl , String restfulMethod ,
                                  Map<String , String> headerMap,MediaType mediaType ,
                                  Method method , Object[] params,
                                  Map<String , Object> bodyParams,
                                  Map<String , String> queryParams) throws Exception;


    public Class<?> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    public String getRemoteBaseUrl() {
        return remoteBaseUrl;
    }

    public void setRemoteBaseUrl(String remoteBaseUrl) {
        this.remoteBaseUrl = remoteBaseUrl;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public boolean isNeedSign() {
        return needSign;
    }

    public void setNeedSign(boolean needSign) {
        this.needSign = needSign;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getInvokeType() {
        return invokeType;
    }

    public void setInvokeType(String invokeType) {
        this.invokeType = invokeType;
    }
}
