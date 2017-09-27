package remoteForSpringMvc.util;

/**
 * Created by fangiming on 2017/9/17.
 */
public class StringTools {
    public static String appendUrl(String firstUrl , String secondUrl){
        StringBuffer sb = new StringBuffer(firstUrl);
        if(firstUrl.endsWith("/")){
            if(secondUrl.startsWith("/")){
                sb.append(secondUrl.substring(1));
            }else{
                sb.append(secondUrl);
            }
        }else{
            if(secondUrl.startsWith("/")){
                sb.append(secondUrl);
            }else{
                sb.append("/"+secondUrl);
            }
        }

        return sb.toString();
    }
}
