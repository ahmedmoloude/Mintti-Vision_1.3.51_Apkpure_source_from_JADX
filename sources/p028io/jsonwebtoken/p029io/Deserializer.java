package p028io.jsonwebtoken.p029io;

/* renamed from: io.jsonwebtoken.io.Deserializer */
public interface Deserializer<T> {
    T deserialize(byte[] bArr) throws DeserializationException;
}
