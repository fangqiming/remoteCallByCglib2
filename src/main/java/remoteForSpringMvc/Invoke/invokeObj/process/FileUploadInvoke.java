package remoteForSpringMvc.Invoke.invokeObj.process;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import remoteForSpringMvc.Annotation.FileSub;
import remoteForSpringMvc.Invoke.invokeObj.BaseInvokeProcessor;
import remoteForSpringMvc.util.ReflectUtils;
import remoteForSpringMvc.util.StringTools;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by fangiming on 2017/9/25.
 */
public class FileUploadInvoke extends ProcressInvoke{

    public FileUploadInvoke(BaseInvokeProcessor invokeProcessor,Method method,Object[] params){
        super( invokeProcessor, method, params);
    }

    private Object fileUpload(){
        Annotation[][]parameterAnnotations = method.getParameterAnnotations();
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
        RestTemplate rest = new RestTemplate();
        String requestUrl = StringTools.appendUrl(invokeProcessor.getRemoteBaseUrl(), methodUrl);
        for(int i =0 ; i<params.length ; i++){
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            FileSub fileParamAnnotation = ReflectUtils.findAnnotation(parameterAnnotation, FileSub.class);
            parts.add(fileParamAnnotation.value(), getByteArrayResource((CommonsMultipartFile) params[i]));
        }
        String string = rest.postForObject(requestUrl, parts, String.class);
        return string;
    }

    private ByteArrayResource getByteArrayResource(final CommonsMultipartFile file){
        return new ByteArrayResource(file.getBytes()){
            @Override
            public String getFilename() throws IllegalStateException {
                return file.getOriginalFilename();
            }
        };
    }

    @Override
    public Object invoke() {
        return fileUpload();
    }
}
