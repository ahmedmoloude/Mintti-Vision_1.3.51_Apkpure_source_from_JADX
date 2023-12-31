package org.greenrobot.greendao.test;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;

public abstract class AbstractDaoSessionTest<T extends AbstractDaoMaster, S extends AbstractDaoSession> extends DbTest {
    protected T daoMaster;
    private final Class<T> daoMasterClass;
    protected S daoSession;

    public AbstractDaoSessionTest(Class<T> cls) {
        this(cls, true);
    }

    public AbstractDaoSessionTest(Class<T> cls, boolean z) {
        super(z);
        this.daoMasterClass = cls;
    }

    /* access modifiers changed from: protected */
    public void setUp() throws Exception {
        super.setUp();
        try {
            this.daoMaster = (AbstractDaoMaster) this.daoMasterClass.getConstructor(new Class[]{Database.class}).newInstance(new Object[]{this.f2147db});
            this.daoMasterClass.getMethod("createAllTables", new Class[]{Database.class, Boolean.TYPE}).invoke((Object) null, new Object[]{this.f2147db, false});
            this.daoSession = this.daoMaster.newSession();
        } catch (Exception e) {
            throw new RuntimeException("Could not prepare DAO session test", e);
        }
    }
}
