package androidx.test.espresso.remote;

public interface EspressoRemoteMessage {

    public interface From<T, M> {
        T fromProto(M m);
    }

    /* renamed from: androidx.test.espresso.remote.EspressoRemoteMessage$To */
    public interface C0781To<M> {
        M toProto();
    }
}
