package p028io.jsonwebtoken.impl.compression;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import p028io.jsonwebtoken.CompressionCodec;
import p028io.jsonwebtoken.CompressionCodecResolver;
import p028io.jsonwebtoken.CompressionCodecs;
import p028io.jsonwebtoken.CompressionException;
import p028io.jsonwebtoken.Header;
import p028io.jsonwebtoken.impl.lang.Services;
import p028io.jsonwebtoken.lang.Assert;
import p028io.jsonwebtoken.lang.Strings;

/* renamed from: io.jsonwebtoken.impl.compression.DefaultCompressionCodecResolver */
public class DefaultCompressionCodecResolver implements CompressionCodecResolver {
    private static final String MISSING_COMPRESSION_MESSAGE = "Unable to find an implementation for compression algorithm [%s] using java.util.ServiceLoader. Ensure you include a backing implementation .jar in the classpath, for example jjwt-impl.jar, or your own .jar for custom implementations.";
    private final Map<String, CompressionCodec> codecs;

    public DefaultCompressionCodecResolver() {
        HashMap hashMap = new HashMap();
        for (CompressionCodec next : Services.loadAll(CompressionCodec.class)) {
            hashMap.put(next.getAlgorithmName().toUpperCase(), next);
        }
        hashMap.put(CompressionCodecs.DEFLATE.getAlgorithmName().toUpperCase(), CompressionCodecs.DEFLATE);
        hashMap.put(CompressionCodecs.GZIP.getAlgorithmName().toUpperCase(), CompressionCodecs.GZIP);
        this.codecs = Collections.unmodifiableMap(hashMap);
    }

    public CompressionCodec resolveCompressionCodec(Header header) {
        String algorithmFromHeader = getAlgorithmFromHeader(header);
        if (!Strings.hasText(algorithmFromHeader)) {
            return null;
        }
        return byName(algorithmFromHeader);
    }

    private String getAlgorithmFromHeader(Header header) {
        Assert.notNull(header, "header cannot be null.");
        return header.getCompressionAlgorithm();
    }

    private CompressionCodec byName(String str) {
        Assert.hasText(str, "'name' must not be empty");
        CompressionCodec compressionCodec = this.codecs.get(str.toUpperCase());
        if (compressionCodec != null) {
            return compressionCodec;
        }
        throw new CompressionException(String.format(MISSING_COMPRESSION_MESSAGE, new Object[]{str}));
    }
}
