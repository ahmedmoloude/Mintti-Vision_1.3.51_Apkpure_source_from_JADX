package org.greenrobot.greendao;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.greenrobot.greendao.async.AsyncSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.p039rx.RxTransaction;
import org.greenrobot.greendao.query.QueryBuilder;
import rx.schedulers.Schedulers;

public class AbstractDaoSession {

    /* renamed from: db */
    private final Database f2142db;
    private final Map<Class<?>, AbstractDao<?, ?>> entityToDao = new HashMap();
    private volatile RxTransaction rxTxIo;
    private volatile RxTransaction rxTxPlain;

    public AbstractDaoSession(Database database) {
        this.f2142db = database;
    }

    /* access modifiers changed from: protected */
    public <T> void registerDao(Class<T> cls, AbstractDao<T, ?> abstractDao) {
        this.entityToDao.put(cls, abstractDao);
    }

    public <T> long insert(T t) {
        return getDao(t.getClass()).insert(t);
    }

    public <T> long insertOrReplace(T t) {
        return getDao(t.getClass()).insertOrReplace(t);
    }

    public <T> void refresh(T t) {
        getDao(t.getClass()).refresh(t);
    }

    public <T> void update(T t) {
        getDao(t.getClass()).update(t);
    }

    public <T> void delete(T t) {
        getDao(t.getClass()).delete(t);
    }

    public <T> void deleteAll(Class<T> cls) {
        getDao(cls).deleteAll();
    }

    public <T, K> T load(Class<T> cls, K k) {
        return getDao(cls).load(k);
    }

    public <T, K> List<T> loadAll(Class<T> cls) {
        return getDao(cls).loadAll();
    }

    public <T, K> List<T> queryRaw(Class<T> cls, String str, String... strArr) {
        return getDao(cls).queryRaw(str, strArr);
    }

    public <T> QueryBuilder<T> queryBuilder(Class<T> cls) {
        return getDao(cls).queryBuilder();
    }

    public AbstractDao<?, ?> getDao(Class<? extends Object> cls) {
        AbstractDao<?, ?> abstractDao = this.entityToDao.get(cls);
        if (abstractDao != null) {
            return abstractDao;
        }
        throw new DaoException("No DAO registered for " + cls);
    }

    public void runInTx(Runnable runnable) {
        this.f2142db.beginTransaction();
        try {
            runnable.run();
            this.f2142db.setTransactionSuccessful();
        } finally {
            this.f2142db.endTransaction();
        }
    }

    public <V> V callInTx(Callable<V> callable) throws Exception {
        this.f2142db.beginTransaction();
        try {
            V call = callable.call();
            this.f2142db.setTransactionSuccessful();
            return call;
        } finally {
            this.f2142db.endTransaction();
        }
    }

    public <V> V callInTxNoException(Callable<V> callable) {
        this.f2142db.beginTransaction();
        try {
            V call = callable.call();
            this.f2142db.setTransactionSuccessful();
            this.f2142db.endTransaction();
            return call;
        } catch (Exception e) {
            throw new DaoException("Callable failed", e);
        } catch (Throwable th) {
            this.f2142db.endTransaction();
            throw th;
        }
    }

    public Database getDatabase() {
        return this.f2142db;
    }

    public Collection<AbstractDao<?, ?>> getAllDaos() {
        return Collections.unmodifiableCollection(this.entityToDao.values());
    }

    public AsyncSession startAsyncSession() {
        return new AsyncSession(this);
    }

    public RxTransaction rxTxPlain() {
        if (this.rxTxPlain == null) {
            this.rxTxPlain = new RxTransaction(this);
        }
        return this.rxTxPlain;
    }

    public RxTransaction rxTx() {
        if (this.rxTxIo == null) {
            this.rxTxIo = new RxTransaction(this, Schedulers.io());
        }
        return this.rxTxIo;
    }
}
