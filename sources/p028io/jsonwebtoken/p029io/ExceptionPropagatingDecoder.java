package p028io.jsonwebtoken.p029io;

import p028io.jsonwebtoken.lang.Assert;

/* renamed from: io.jsonwebtoken.io.ExceptionPropagatingDecoder */
class ExceptionPropagatingDecoder<T, R> implements Decoder<T, R> {
    private final Decoder<T, R> decoder;

    ExceptionPropagatingDecoder(Decoder<T, R> decoder2) {
        Assert.notNull(decoder2, "Decoder cannot be null.");
        this.decoder = decoder2;
    }

    public R decode(T t) throws DecodingException {
        Assert.notNull(t, "Decode argument cannot be null.");
        try {
            return this.decoder.decode(t);
        } catch (DecodingException e) {
            throw e;
        } catch (Exception e2) {
            throw new DecodingException("Unable to decode input: " + e2.getMessage(), e2);
        }
    }
}
