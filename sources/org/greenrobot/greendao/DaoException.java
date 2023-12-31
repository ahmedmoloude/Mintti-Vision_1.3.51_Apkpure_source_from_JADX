package org.greenrobot.greendao;

import android.database.SQLException;

public class DaoException extends SQLException {
    private static final long serialVersionUID = -5877937327907457779L;

    public DaoException() {
    }

    public DaoException(String str) {
        super(str);
    }

    public DaoException(String str, Throwable th) {
        super(str);
        safeInitCause(th);
    }

    public DaoException(Throwable th) {
        safeInitCause(th);
    }

    /* access modifiers changed from: protected */
    public void safeInitCause(Throwable th) {
        try {
            initCause(th);
        } catch (Throwable th2) {
            DaoLog.m1116e("Could not set initial cause", th2);
            DaoLog.m1116e("Initial cause is:", th);
        }
    }
}
