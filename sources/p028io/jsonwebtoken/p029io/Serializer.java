package p028io.jsonwebtoken.p029io;

/* renamed from: io.jsonwebtoken.io.Serializer */
public interface Serializer<T> {
    byte[] serialize(T t) throws SerializationException;
}
