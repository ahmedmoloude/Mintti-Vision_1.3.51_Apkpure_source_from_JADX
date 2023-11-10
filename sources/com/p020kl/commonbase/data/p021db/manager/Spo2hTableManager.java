package com.p020kl.commonbase.data.p021db.manager;

import com.p020kl.commonbase.base.BaseApplication;
import com.p020kl.commonbase.bean.Spo2Entity;
import com.p020kl.commonbase.data.p021db.Spo2EntityDao;
import com.p020kl.commonbase.utils.DateUtils;
import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

/* renamed from: com.kl.commonbase.data.db.manager.Spo2hTableManager */
public class Spo2hTableManager {
    public static void insertEntity(Spo2Entity spo2Entity) {
        BaseApplication.getInstance().getSpo2hEntityDao().insert(spo2Entity);
    }

    public static void deleteEntity(Spo2Entity spo2Entity) {
        BaseApplication.getInstance().getSpo2hEntityDao().delete(spo2Entity);
    }

    public static void updateEntity(Spo2Entity spo2Entity) {
        BaseApplication.getInstance().getSpo2hEntityDao().update(spo2Entity);
    }

    public static void deleteAllByMemberId(String str) {
        BaseApplication.getInstance().getSpo2hEntityDao().queryBuilder().where(Spo2EntityDao.Properties.UserId.mo34587eq(str), new WhereCondition[0]).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    public static List<Spo2Entity> queryAll(String str) {
        return BaseApplication.getInstance().getSpo2hEntityDao().queryBuilder().where(Spo2EntityDao.Properties.UserId.mo34587eq(str), new WhereCondition[0]).list();
    }

    public static List<Spo2Entity> queryByDay(String str, int i, int i2, int i3) {
        QueryBuilder queryBuilder = BaseApplication.getInstance().getSpo2hEntityDao().queryBuilder();
        queryBuilder.where(Spo2EntityDao.Properties.UserId.mo34587eq(str), queryBuilder.and(Spo2EntityDao.Properties.Year.mo34587eq(Integer.valueOf(i)), Spo2EntityDao.Properties.Month.mo34587eq(Integer.valueOf(i2)), Spo2EntityDao.Properties.Day.mo34587eq(Integer.valueOf(i3))));
        return queryBuilder.list();
    }

    public static List<Spo2Entity> queryByWeek(String str) {
        long mondayOfThisWeek = DateUtils.getMondayOfThisWeek();
        long sundayOfThisWeek = DateUtils.getSundayOfThisWeek();
        QueryBuilder queryBuilder = BaseApplication.getInstance().getSpo2hEntityDao().queryBuilder();
        queryBuilder.where(Spo2EntityDao.Properties.UserId.mo34587eq(str), new WhereCondition[0]).where(Spo2EntityDao.Properties.CreateTime.between(Long.valueOf(mondayOfThisWeek), Long.valueOf(sundayOfThisWeek)), new WhereCondition[0]);
        return queryBuilder.list();
    }

    public static List<Spo2Entity> queryByHistoryWeek(String str, Date date) {
        long mondayOfHistoryWeek = DateUtils.getMondayOfHistoryWeek(date);
        long sundayOfHistoryWeek = DateUtils.getSundayOfHistoryWeek(date);
        QueryBuilder queryBuilder = BaseApplication.getInstance().getSpo2hEntityDao().queryBuilder();
        queryBuilder.where(Spo2EntityDao.Properties.UserId.mo34587eq(str), new WhereCondition[0]).where(Spo2EntityDao.Properties.CreateTime.between(Long.valueOf(mondayOfHistoryWeek), Long.valueOf(sundayOfHistoryWeek)), new WhereCondition[0]);
        return queryBuilder.list();
    }

    public static List<Spo2Entity> queryByMonth(String str, int i, int i2) {
        QueryBuilder queryBuilder = BaseApplication.getInstance().getSpo2hEntityDao().queryBuilder();
        queryBuilder.where(Spo2EntityDao.Properties.UserId.mo34587eq(str), queryBuilder.and(Spo2EntityDao.Properties.Year.mo34587eq(Integer.valueOf(i)), Spo2EntityDao.Properties.Month.mo34587eq(Integer.valueOf(i2)), new WhereCondition[0]));
        return queryBuilder.list();
    }
}
