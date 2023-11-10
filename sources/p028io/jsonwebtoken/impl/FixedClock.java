package p028io.jsonwebtoken.impl;

import java.util.Date;
import p028io.jsonwebtoken.Clock;

/* renamed from: io.jsonwebtoken.impl.FixedClock */
public class FixedClock implements Clock {
    private final Date now;

    public FixedClock() {
        this(new Date());
    }

    public FixedClock(Date date) {
        this.now = date;
    }

    public Date now() {
        return this.now;
    }
}
