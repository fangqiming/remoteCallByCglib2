package remoteForSpringMvc.util.mediaTypeFactory;

import org.springframework.http.MediaType;
import remoteForSpringMvc.Exception.httpMediaTypeNotSupportException;

/**
 * Created by fangiming on 2017/9/17.
 */
public class MediaTypeFactory {
    public static MediaType createMediaFactory(MediaType mediaType){
        if(mediaType.isCompatibleWith(MediaType.APPLICATION_FORM_URLENCODED)){
            return new FormUrlencodedMediaType();
        }else if(mediaType.isCompatibleWith(MediaType.APPLICATION_JSON)){
            return new JsonMediaType();
        }else{
            System.out.println("only support Json/FormUrlencoded if you need other media type please to enhance it");
            throw new httpMediaTypeNotSupportException(mediaType.toString());
        }
    }
}
