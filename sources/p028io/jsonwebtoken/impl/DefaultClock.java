package p028io.jsonwebtoken.impl;

import java.util.Date;
import p028io.jsonwebtoken.Clock;

/* renamed from: io.jsonwebtoken.impl.DefaultClock */
public class DefaultClock implements Clock {
    public static final Clock INSTANCE = new DefaultClock();

    public Date now() {
        return new Date();
    }
}
