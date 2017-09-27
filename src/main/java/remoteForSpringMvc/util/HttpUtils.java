package remoteForSpringMvc.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.*;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.http.converter.xml.XmlAwareFormHttpMessageConverter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import remoteForSpringMvc.util.mediaTypeFactory.BodyInterface;
import remoteForSpringMvc.util.mediaTypeFactory.MediaTypeFactory;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fangiming on 2017/9/17.
 */
public abstract class HttpUtils {


    public  static String loadResult(String url, Map<String, String> queryParams, Map<String, Object> bodyParams, Map<String, String> header, String method, MediaType mediaType) {
        RestTemplate template = buildTemplate();
        StringBuffer queryParamsSB=formateQueryParams(queryParams);
        HttpMethod httpMethod=getHttpMethod(method);
        HttpHeaders requestHeaders=getHttpHeader(header);
        MediaType targetMedia= MediaTypeFactory.createMediaFactory(mediaType);
        requestHeaders.setContentType(targetMedia);
        Object httpBody=((BodyInterface)targetMedia).getBodyValue(bodyParams);
        requestHeaders.setAccept(setCanAcceptMediaList());
        HttpEntity<?> requestEntity = new HttpEntity<Object>(httpBody, requestHeaders);
        String newUrl=reloadCalUrl(url,queryParams, queryParamsSB);
        return getRemoveResult( httpMethod, template, newUrl, requestEntity);
    }

    private static RestTemplate buildTemplate(){
        RestTemplate template = new RestTemplate();
        List<HttpMessageConverter<?>> converterList = new ArrayList<HttpMessageConverter<?>>();
        converterList.add(new ByteArrayHttpMessageConverter());
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        stringConverter.setWriteAcceptCharset(false);
        converterList.add(stringConverter);
        converterList.add(new ResourceHttpMessageConverter());
        converterList.add(new SourceHttpMessageConverter());
        converterList.add(new XmlAwareFormHttpMessageConverter());
        converterList.add(new FormHttpMessageConverter());
        template.setMessageConverters(converterList);
        return template;
    }

    private static StringBuffer formateQueryParams(Map<String , String> queryParams){
        StringBuffer result=new StringBuffer();
        for(String paraName : queryParams.keySet()){
            result.append("&").append(paraName).append("=").append(queryParams.get(paraName));
        }
        return result;
    }

    private static HttpMethod getHttpMethod(String method){
        HttpMethod result=HttpMethod.POST;
        if(method.equals(javax.ws.rs.HttpMethod.GET)){
            result = HttpMethod.GET;
        }else if(method.equals(javax.ws.rs.HttpMethod.PUT)){
            result = HttpMethod.PUT;
        }else if(method.equals(javax.ws.rs.HttpMethod.DELETE)){
            result = HttpMethod.DELETE;
        }
        return result;
    }

    private static HttpHeaders getHttpHeader(Map<String , String> header){
        HttpHeaders result = new HttpHeaders();
        if(header != null && !header.isEmpty()){
            for(String headName : header.keySet()){
                result.add(headName,header.get(headName));
            }
        }
        return result;
    }

    private static List<MediaType> setCanAcceptMediaList(){
        List<MediaType> acceptList = new ArrayList<MediaType>();
        acceptList.add(MediaType.APPLICATION_JSON);
        return acceptList;
    }

    private static String reloadCalUrl(String url,Map<String , String> queryParams,StringBuffer sb){
        String newUrl=url;
        if(queryParams != null && !queryParams.isEmpty()){
            if(url.contains("?")){
                newUrl += "&"+sb.toString();
            }else{
                newUrl += "?" + sb.toString();
            }
        }
        return newUrl;
    }

    private static String getRemoveResult(HttpMethod httpMethod,RestTemplate template,String url,HttpEntity<?> requestEntity){
        String result = null;
        if(httpMethod.equals(HttpMethod.GET)){
            result = template.getForObject(url,String.class);
        }else{
            try{
                result = template.exchange(url, httpMethod, requestEntity, String.class).getBody();
            }catch(HttpStatusCodeException e){
                System.out.println("remote access failed");
                e.printStackTrace();
            }
        }
        return result;

    }

}
