package remoteForSpringMvc.util;

import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fangiming on 2017/9/17.
 * 作用：用于两端的身份校验
 */
public abstract class HttpSignCalculator {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");

    public static String calculateSign(String signKey){
        long curren = System.currentTimeMillis();
        curren += 10 * 60 * 1000;
        String key=dateFormat.format(new Date(curren));
        return DigestUtils.md5DigestAsHex((key+signKey).getBytes());
    }

    public static boolean isPassVerification(HttpServletRequest request,String signKey,String signName){
        String key=dateFormat.format(new Date());
        String serverKey=DigestUtils.md5DigestAsHex((key+signKey).getBytes());
        return request.getHeader(signName).equals(serverKey);
    }




}
