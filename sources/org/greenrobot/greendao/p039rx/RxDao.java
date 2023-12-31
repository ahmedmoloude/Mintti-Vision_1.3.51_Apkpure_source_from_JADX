package org.greenrobot.greendao.p039rx;

import java.util.List;
import java.util.concurrent.Callable;
import org.greenrobot.greendao.AbstractDao;
import rx.Observable;
import rx.Scheduler;

/* renamed from: org.greenrobot.greendao.rx.RxDao */
public class RxDao<T, K> extends RxBase {
    /* access modifiers changed from: private */
    public final AbstractDao<T, K> dao;

    public /* bridge */ /* synthetic */ Scheduler getScheduler() {
        return super.getScheduler();
    }

    public RxDao(AbstractDao<T, K> abstractDao) {
        this(abstractDao, (Scheduler) null);
    }

    public RxDao(AbstractDao<T, K> abstractDao, Scheduler scheduler) {
        super(scheduler);
        this.dao = abstractDao;
    }

    public Observable<List<T>> loadAll() {
        return wrap(new Callable<List<T>>() {
            public List<T> call() throws Exception {
                return RxDao.this.dao.loadAll();
            }
        });
    }

    public Observable<T> load(final K k) {
        return wrap(new Callable<T>() {
            public T call() throws Exception {
                return RxDao.this.dao.load(k);
            }
        });
    }

    public Observable<T> refresh(final T t) {
        return wrap(new Callable<T>() {
            public T call() throws Exception {
                RxDao.this.dao.refresh(t);
                return t;
            }
        });
    }

    public Observable<T> insert(final T t) {
        return wrap(new Callable<T>() {
            public T call() throws Exception {
                RxDao.this.dao.insert(t);
                return t;
            }
        });
    }

    public Observable<Iterable<T>> insertInTx(final Iterable<T> iterable) {
        return wrap(new Callable<Iterable<T>>() {
            public Iterable<T> call() throws Exception {
                RxDao.this.dao.insertInTx(iterable);
                return iterable;
            }
        });
    }

    public Observable<Object[]> insertInTx(final T... tArr) {
        return wrap(new Callable<Object[]>() {
            public Object[] call() throws Exception {
                RxDao.this.dao.insertInTx((T[]) tArr);
                return tArr;
            }
        });
    }

    public Observable<T> insertOrReplace(final T t) {
        return wrap(new Callable<T>() {
            public T call() throws Exception {
                RxDao.this.dao.insertOrReplace(t);
                return t;
            }
        });
    }

    public Observable<Iterable<T>> insertOrReplaceInTx(final Iterable<T> iterable) {
        return wrap(new Callable<Iterable<T>>() {
            public Iterable<T> call() throws Exception {
                RxDao.this.dao.insertOrReplaceInTx(iterable);
                return iterable;
            }
        });
    }

    public Observable<Object[]> insertOrReplaceInTx(final T... tArr) {
        return wrap(new Callable<Object[]>() {
            public Object[] call() throws Exception {
                RxDao.this.dao.insertOrReplaceInTx((T[]) tArr);
                return tArr;
            }
        });
    }

    public Observable<T> save(final T t) {
        return wrap(new Callable<T>() {
            public T call() throws Exception {
                RxDao.this.dao.save(t);
                return t;
            }
        });
    }

    public Observable<Iterable<T>> saveInTx(final Iterable<T> iterable) {
        return wrap(new Callable<Iterable<T>>() {
            public Iterable<T> call() throws Exception {
                RxDao.this.dao.saveInTx(iterable);
                return iterable;
            }
        });
    }

    public Observable<Object[]> saveInTx(final T... tArr) {
        return wrap(new Callable<Object[]>() {
            public Object[] call() throws Exception {
                RxDao.this.dao.saveInTx((T[]) tArr);
                return tArr;
            }
        });
    }

    public Observable<T> update(final T t) {
        return wrap(new Callable<T>() {
            public T call() throws Exception {
                RxDao.this.dao.update(t);
                return t;
            }
        });
    }

    public Observable<Iterable<T>> updateInTx(final Iterable<T> iterable) {
        return wrap(new Callable<Iterable<T>>() {
            public Iterable<T> call() throws Exception {
                RxDao.this.dao.updateInTx(iterable);
                return iterable;
            }
        });
    }

    public Observable<Object[]> updateInTx(final T... tArr) {
        return wrap(new Callable<Object[]>() {
            public Object[] call() throws Exception {
                RxDao.this.dao.updateInTx((T[]) tArr);
                return tArr;
            }
        });
    }

    public Observable<Void> delete(final T t) {
        return wrap(new Callable<Void>() {
            public Void call() throws Exception {
                RxDao.this.dao.delete(t);
                return null;
            }
        });
    }

    public Observable<Void> deleteByKey(final K k) {
        return wrap(new Callable<Void>() {
            public Void call() throws Exception {
                RxDao.this.dao.deleteByKey(k);
                return null;
            }
        });
    }

    public Observable<Void> deleteAll() {
        return wrap(new Callable<Void>() {
            public Void call() throws Exception {
                RxDao.this.dao.deleteAll();
                return null;
            }
        });
    }

    public Observable<Void> deleteInTx(final Iterable<T> iterable) {
        return wrap(new Callable<Void>() {
            public Void call() throws Exception {
                RxDao.this.dao.deleteInTx(iterable);
                return null;
            }
        });
    }

    public Observable<Void> deleteInTx(final T... tArr) {
        return wrap(new Callable<Void>() {
            public Void call() throws Exception {
                RxDao.this.dao.deleteInTx((T[]) tArr);
                return null;
            }
        });
    }

    public Observable<Void> deleteByKeyInTx(final Iterable<K> iterable) {
        return wrap(new Callable<Void>() {
            public Void call() throws Exception {
                RxDao.this.dao.deleteByKeyInTx(iterable);
                return null;
            }
        });
    }

    public Observable<Void> deleteByKeyInTx(final K... kArr) {
        return wrap(new Callable<Void>() {
            public Void call() throws Exception {
                RxDao.this.dao.deleteByKeyInTx((K[]) kArr);
                return null;
            }
        });
    }

    public Observable<Long> count() {
        return wrap(new Callable<Long>() {
            public Long call() throws Exception {
                return Long.valueOf(RxDao.this.dao.count());
            }
        });
    }

    public AbstractDao<T, K> getDao() {
        return this.dao;
    }
}
