package remoteForSpringMvc.ProxyFactory;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.SmartFactoryBean;
import org.springframework.util.StringUtils;
import remoteForSpringMvc.Exception.ClassNotFoundExceptionRunTime;
import remoteForSpringMvc.Invoke.invokeFactory.InvokeFactory;
import remoteForSpringMvc.Invoke.invokeObj.BaseInvokeProcessor;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by fangiming on 2017/9/17.
 */
public class ProxyFactoryCglib implements SmartFactoryBean<Object>, MethodInterceptor {

    private String targetClass;

    private String remoteBaseUrl;

    private String signName="sign";

    private boolean needSign=false;

    private String signKey;

    private String systemCode;

    private String invokeType;

    private boolean notCatchException=true;

    private BaseInvokeProcessor processor;



    @Override
    public boolean isPrototype() {
        return false;
    }

    @Override
    public boolean isEagerInit() {
        return false;
    }

    @Override
    public Object getObject() throws Exception {
        if(targetClass == null || targetClass.trim().length()<=0){
            throw new Exception("please check config the attribute targetClass is missing");
        }

        Class<?> clazz = Class.forName(targetClass);
        processor = InvokeFactory.getInvokeClass(clazz, remoteBaseUrl, signName, needSign, signKey, systemCode, invokeType);
        if(processor == null){
            throw new NoSuchBeanDefinitionException(clazz , " can't inilize ");
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setInterfaces(new Class[]{clazz});
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Class<?> getObjectType() {
        try{
            if(StringUtils.hasLength(targetClass)){
                return Class.forName(targetClass);
            }
        }catch(ClassNotFoundException e){
            System.out.println(targetClass+" Class not found");
            throw new ClassNotFoundExceptionRunTime(targetClass);
        }
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (!Modifier.isAbstract(method.getModifiers())) {
            return methodProxy.invokeSuper(method, objects);
        }
        try{
            return processor.process(method, objects);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
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

    public String getInvokeType() {
        return invokeType;
    }

    public void setInvokeType(String invokeType) {
        this.invokeType = invokeType;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public boolean isNotCatchException() {
        return notCatchException;
    }

    public void setNotCatchException(boolean notCatchException) {
        this.notCatchException = notCatchException;
    }
}
