package p028io.jsonwebtoken;

import java.util.Map;
import p028io.jsonwebtoken.Header;

/* renamed from: io.jsonwebtoken.Header */
public interface Header<T extends Header<T>> extends Map<String, Object> {
    public static final String COMPRESSION_ALGORITHM = "zip";
    public static final String CONTENT_TYPE = "cty";
    @Deprecated
    public static final String DEPRECATED_COMPRESSION_ALGORITHM = "calg";
    public static final String JWT_TYPE = "JWT";
    public static final String TYPE = "typ";

    String getCompressionAlgorithm();

    String getContentType();

    String getType();

    T setCompressionAlgorithm(String str);

    T setContentType(String str);

    T setType(String str);
}
