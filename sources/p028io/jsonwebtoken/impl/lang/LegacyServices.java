package p028io.jsonwebtoken.impl.lang;

import p028io.jsonwebtoken.lang.UnknownClassException;

@Deprecated
/* renamed from: io.jsonwebtoken.impl.lang.LegacyServices */
public final class LegacyServices {
    public static <T> T loadFirst(Class<T> cls) {
        try {
            return Services.loadFirst(cls);
        } catch (UnavailableImplementationException e) {
            throw new UnknownClassException(e.getMessage(), e);
        }
    }
}
