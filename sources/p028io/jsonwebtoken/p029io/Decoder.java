package p028io.jsonwebtoken.p029io;

/* renamed from: io.jsonwebtoken.io.Decoder */
public interface Decoder<T, R> {
    R decode(T t) throws DecodingException;
}
