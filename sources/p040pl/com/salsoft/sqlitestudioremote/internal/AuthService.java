package p040pl.com.salsoft.sqlitestudioremote.internal;

/* renamed from: pl.com.salsoft.sqlitestudioremote.internal.AuthService */
public interface AuthService {
    boolean authorize(String str);

    boolean isAuthRequired();

    boolean isIpAllowed(String str);
}
