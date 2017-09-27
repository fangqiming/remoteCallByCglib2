package remoteForSpringMvc.util.mediaTypeFactory;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by fangiming on 2017/9/25.
 */
public class FileMediaType extends MediaType implements BodyInterface {

    public FileMediaType() {
        super("multipart", "form-data", Charset.forName("UTF-8"));
    }

    @Override
    public Object getBodyValue(Map<String, Object> bodyParams) {
        MultiValueMap<String , Object> bodyMap = new LinkedMultiValueMap<String, Object>();
        for(String s :bodyParams.keySet()){
            CommonsMultipartFile file= (CommonsMultipartFile) bodyParams.get(s);
            bodyMap.add(s, getByteArrayResource(file));
        }
        return bodyMap;
    }

    private ByteArrayResource getByteArrayResource(final CommonsMultipartFile file){
        return new ByteArrayResource(file.getBytes()){
            @Override
            public String getFilename() throws IllegalStateException {
                return file.getOriginalFilename();
            }
        };
    }

}
