package p028io.jsonwebtoken.impl;

import java.util.Date;
import java.util.Map;
import p028io.jsonwebtoken.Claims;
import p028io.jsonwebtoken.RequiredTypeException;

/* renamed from: io.jsonwebtoken.impl.DefaultClaims */
public class DefaultClaims extends JwtMap implements Claims {
    private static final String CONVERSION_ERROR_MSG = "Cannot convert existing claim value of type '%s' to desired type '%s'. JJWT only converts simple String, Date, Long, Integer, Short and Byte types automatically. Anything more complex is expected to be already converted to your desired type by the JSON Deserializer implementation. You may specify a custom Deserializer for a JwtParser with the desired conversion configuration via the JwtParserBuilder.deserializeJsonWith() method. See https://github.com/jwtk/jjwt#custom-json-processor for more information. If using Jackson, you can specify custom claim POJO types as described in https://github.com/jwtk/jjwt#json-jackson-custom-types";

    public DefaultClaims() {
    }

    public DefaultClaims(Map<String, ?> map) {
        super(map);
    }

    public String getIssuer() {
        return getString(Claims.ISSUER);
    }

    public Claims setIssuer(String str) {
        setValue(Claims.ISSUER, str);
        return this;
    }

    public String getSubject() {
        return getString("sub");
    }

    public Claims setSubject(String str) {
        setValue("sub", str);
        return this;
    }

    public String getAudience() {
        return getString(Claims.AUDIENCE);
    }

    public Claims setAudience(String str) {
        setValue(Claims.AUDIENCE, str);
        return this;
    }

    public Date getExpiration() {
        return (Date) get(Claims.EXPIRATION, Date.class);
    }

    public Claims setExpiration(Date date) {
        setDateAsSeconds(Claims.EXPIRATION, date);
        return this;
    }

    public Date getNotBefore() {
        return (Date) get(Claims.NOT_BEFORE, Date.class);
    }

    public Claims setNotBefore(Date date) {
        setDateAsSeconds(Claims.NOT_BEFORE, date);
        return this;
    }

    public Date getIssuedAt() {
        return (Date) get(Claims.ISSUED_AT, Date.class);
    }

    public Claims setIssuedAt(Date date) {
        setDateAsSeconds(Claims.ISSUED_AT, date);
        return this;
    }

    public String getId() {
        return getString(Claims.f2007ID);
    }

    public Claims setId(String str) {
        setValue(Claims.f2007ID, str);
        return this;
    }

    private static boolean isSpecDate(String str) {
        return Claims.EXPIRATION.equals(str) || Claims.ISSUED_AT.equals(str) || Claims.NOT_BEFORE.equals(str);
    }

    public Object put(String str, Object obj) {
        if (!(obj instanceof Date) || !isSpecDate(str)) {
            return super.put(str, obj);
        }
        return setDateAsSeconds(str, (Date) obj);
    }

    public <T> T get(String str, Class<T> cls) {
        Object obj = get(str);
        if (obj == null) {
            return null;
        }
        if (Date.class.equals(cls)) {
            if (isSpecDate(str)) {
                obj = toSpecDate(obj, str);
            } else {
                obj = toDate(obj, str);
            }
        }
        return castClaimValue(obj, cls);
    }

    private <T> T castClaimValue(Object obj, Class<T> cls) {
        if (obj instanceof Integer) {
            int intValue = ((Integer) obj).intValue();
            if (cls == Long.class) {
                obj = Long.valueOf((long) intValue);
            } else if (cls == Short.class && -32768 <= intValue && intValue <= 32767) {
                obj = Short.valueOf((short) intValue);
            } else if (cls == Byte.class && -128 <= intValue && intValue <= 127) {
                obj = Byte.valueOf((byte) intValue);
            }
        }
        if (cls.isInstance(obj)) {
            return cls.cast(obj);
        }
        throw new RequiredTypeException(String.format(CONVERSION_ERROR_MSG, new Object[]{obj.getClass(), cls}));
    }
}
