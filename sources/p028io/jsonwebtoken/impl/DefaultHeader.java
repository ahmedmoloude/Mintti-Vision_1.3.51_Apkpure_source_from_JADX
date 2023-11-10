package p028io.jsonwebtoken.impl;

import java.util.Map;
import p028io.jsonwebtoken.Header;
import p028io.jsonwebtoken.lang.Strings;

/* renamed from: io.jsonwebtoken.impl.DefaultHeader */
public class DefaultHeader<T extends Header<T>> extends JwtMap implements Header<T> {
    public DefaultHeader() {
    }

    public DefaultHeader(Map<String, Object> map) {
        super(map);
    }

    public String getType() {
        return getString(Header.TYPE);
    }

    public T setType(String str) {
        setValue(Header.TYPE, str);
        return this;
    }

    public String getContentType() {
        return getString(Header.CONTENT_TYPE);
    }

    public T setContentType(String str) {
        setValue(Header.CONTENT_TYPE, str);
        return this;
    }

    public String getCompressionAlgorithm() {
        String string = getString(Header.COMPRESSION_ALGORITHM);
        return !Strings.hasText(string) ? getString(Header.DEPRECATED_COMPRESSION_ALGORITHM) : string;
    }

    public T setCompressionAlgorithm(String str) {
        setValue(Header.COMPRESSION_ALGORITHM, str);
        return this;
    }
}
