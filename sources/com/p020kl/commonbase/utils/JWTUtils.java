package com.p020kl.commonbase.utils;

import android.util.Log;
import com.p020kl.commonbase.bean.rothmanindex.PayloadBean;
import com.p020kl.commonbase.bean.rothmanindex.Role;
import com.p020kl.commonbase.constants.Constants;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import javax.crypto.SecretKey;
import p028io.jsonwebtoken.Header;
import p028io.jsonwebtoken.JwsHeader;
import p028io.jsonwebtoken.JwtBuilder;
import p028io.jsonwebtoken.Jwts;
import p028io.jsonwebtoken.security.Keys;

/* renamed from: com.kl.commonbase.utils.JWTUtils */
public class JWTUtils {
    public static String getJwt(String str) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        PayloadBean payloadBean = new PayloadBean();
        payloadBean.setClientId(Constants.CLIENT_ID);
        payloadBean.setIndividualId(str);
        arrayList.add(str);
        arrayList2.add(str);
        payloadBean.setCanAssess(arrayList);
        payloadBean.setCanMonitor(arrayList2);
        payloadBean.setRole(Role.consumer.name());
        payloadBean.setExp(String.valueOf((System.currentTimeMillis() / 1000) + 300));
        SecretKey hmacShaKeyFor = Keys.hmacShaKeyFor(Constants.SECRET_ID.getBytes(StandardCharsets.UTF_8));
        JwtBuilder builder = Jwts.builder();
        String compact = builder.setHeaderParam(Header.TYPE, Header.JWT_TYPE).setHeaderParam(JwsHeader.ALGORITHM, "HS256").setClaims((Map<String, ?>) ClassUtils.objectToMap(payloadBean)).signWith(hmacShaKeyFor).compact();
        Log.e("JWTUtils", "getJwt: " + compact);
        return compact;
    }
}
