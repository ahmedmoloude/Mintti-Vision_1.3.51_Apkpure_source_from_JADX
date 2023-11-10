package p028io.jsonwebtoken.p029io;

/* renamed from: io.jsonwebtoken.io.Encoder */
public interface Encoder<T, R> {
    R encode(T t) throws EncodingException;
}
