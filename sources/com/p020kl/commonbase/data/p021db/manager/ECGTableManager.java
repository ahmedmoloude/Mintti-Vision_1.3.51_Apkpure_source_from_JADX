package com.p020kl.commonbase.data.p021db.manager;

import com.p020kl.commonbase.base.BaseApplication;
import com.p020kl.commonbase.bean.ECGEntity;
import com.p020kl.commonbase.data.p021db.ECGEntityDao;
import com.p020kl.commonbase.utils.DateUtils;
import java.util.List;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;
import p028io.reactivex.Observable;
import p028io.reactivex.functions.Consumer;
import p028io.reactivex.schedulers.Schedulers;

/* renamed from: com.kl.commonbase.data.db.manager.ECGTableManager */
public class ECGTableManager {
    public static void insertEcgEntity(ECGEntity eCGEntity) {
        BaseApplication.getInstance().getEcgEntityDao().insert(eCGEntity);
    }

    public static void deleteEcgEntity(ECGEntity eCGEntity) {
        BaseApplication.getInstance().getEcgEntityDao().delete(eCGEntity);
    }

    public static void updateEcgEntity(ECGEntity eCGEntity) {
        BaseApplication.getInstance().getEcgEntityDao().update(eCGEntity);
    }

    public static void deleteAllByMemberId(String str) {
        BaseApplication.getInstance().getEcgEntityDao().queryBuilder().where(ECGEntityDao.Properties.UserId.mo34587eq(str), new WhereCondition[0]).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    public static void insertEcgEntity(List<ECGEntity> list) {
        Observable.fromIterable(list).subscribeOn(Schedulers.m1081io()).observeOn(Schedulers.m1081io()).subscribe(new Consumer<ECGEntity>() {
            public void accept(ECGEntity eCGEntity) throws Exception {
                BaseApplication.getInstance().getEcgEntityDao().insert(eCGEntity);
            }
        });
    }

    public static List<ECGEntity> queryAll(String str) {
        return BaseApplication.getInstance().getEcgEntityDao().queryBuilder().where(ECGEntityDao.Properties.UserId.mo34587eq(str), new WhereCondition[0]).list();
    }

    public static List<ECGEntity> queryByDay(String str, int i, int i2, int i3) {
        QueryBuilder queryBuilder = BaseApplication.getInstance().getEcgEntityDao().queryBuilder();
        queryBuilder.where(ECGEntityDao.Properties.UserId.mo34587eq(str), queryBuilder.and(ECGEntityDao.Properties.Year.mo34587eq(Integer.valueOf(i)), ECGEntityDao.Properties.Month.mo34587eq(Integer.valueOf(i2)), ECGEntityDao.Properties.Day.mo34587eq(Integer.valueOf(i3))));
        return queryBuilder.list();
    }

    public static List<ECGEntity> queryByWeek(String str) {
        long mondayOfThisWeek = DateUtils.getMondayOfThisWeek();
        long sundayOfThisWeek = DateUtils.getSundayOfThisWeek();
        QueryBuilder queryBuilder = BaseApplication.getInstance().getEcgEntityDao().queryBuilder();
        queryBuilder.where(ECGEntityDao.Properties.UserId.mo34587eq(str), new WhereCondition[0]).and(ECGEntityDao.Properties.CreateTime.mo34588ge(Long.valueOf(mondayOfThisWeek)), ECGEntityDao.Properties.CreateTime.mo34594le(Long.valueOf(sundayOfThisWeek)), new WhereCondition[0]);
        return queryBuilder.list();
    }

    public static List<ECGEntity> queryByMonth(String str, int i, int i2) {
        QueryBuilder queryBuilder = BaseApplication.getInstance().getEcgEntityDao().queryBuilder();
        queryBuilder.where(ECGEntityDao.Properties.UserId.mo34587eq(str), queryBuilder.and(ECGEntityDao.Properties.Year.mo34587eq(Integer.valueOf(i)), ECGEntityDao.Properties.Month.mo34587eq(Integer.valueOf(i2)), new WhereCondition[0]));
        return queryBuilder.list();
    }
}
