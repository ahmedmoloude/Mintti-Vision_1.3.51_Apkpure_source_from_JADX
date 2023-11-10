package p028io.jsonwebtoken.p029io;

import p028io.jsonwebtoken.lang.Assert;

/* renamed from: io.jsonwebtoken.io.ExceptionPropagatingEncoder */
class ExceptionPropagatingEncoder<T, R> implements Encoder<T, R> {
    private final Encoder<T, R> encoder;

    ExceptionPropagatingEncoder(Encoder<T, R> encoder2) {
        Assert.notNull(encoder2, "Encoder cannot be null.");
        this.encoder = encoder2;
    }

    public R encode(T t) throws EncodingException {
        Assert.notNull(t, "Encode argument cannot be null.");
        try {
            return this.encoder.encode(t);
        } catch (EncodingException e) {
            throw e;
        } catch (Exception e2) {
            throw new EncodingException("Unable to encode input: " + e2.getMessage(), e2);
        }
    }
}
