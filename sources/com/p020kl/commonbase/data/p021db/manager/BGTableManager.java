package com.p020kl.commonbase.data.p021db.manager;

import com.p020kl.commonbase.base.BaseApplication;
import com.p020kl.commonbase.bean.BGEntity;
import com.p020kl.commonbase.data.p021db.BGEntityDao;
import com.p020kl.commonbase.utils.DateUtils;
import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

/* renamed from: com.kl.commonbase.data.db.manager.BGTableManager */
public class BGTableManager {
    public static void insertEntity(BGEntity bGEntity) {
        BaseApplication.getInstance().getBgEntityDao().insert(bGEntity);
    }

    public static void deleteEntity(BGEntity bGEntity) {
        BaseApplication.getInstance().getBgEntityDao().delete(bGEntity);
    }

    public static void updateEntity(BGEntity bGEntity) {
        BaseApplication.getInstance().getBgEntityDao().update(bGEntity);
    }

    public static void deleteAllByMemberId(String str) {
        BaseApplication.getInstance().getBgEntityDao().queryBuilder().where(BGEntityDao.Properties.UserId.mo34587eq(str), new WhereCondition[0]).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    public static List<BGEntity> queryAll(String str) {
        return BaseApplication.getInstance().getBgEntityDao().queryBuilder().where(BGEntityDao.Properties.UserId.mo34587eq(str), new WhereCondition[0]).list();
    }

    public static List<BGEntity> queryByDay(String str, int i, int i2, int i3) {
        QueryBuilder queryBuilder = BaseApplication.getInstance().getBgEntityDao().queryBuilder();
        queryBuilder.where(BGEntityDao.Properties.UserId.mo34587eq(str), queryBuilder.and(BGEntityDao.Properties.Year.mo34587eq(Integer.valueOf(i)), BGEntityDao.Properties.Month.mo34587eq(Integer.valueOf(i2)), BGEntityDao.Properties.Day.mo34587eq(Integer.valueOf(i3))));
        return queryBuilder.list();
    }

    public static List<BGEntity> queryByWeek(String str) {
        long mondayOfThisWeek = DateUtils.getMondayOfThisWeek();
        long sundayOfThisWeek = DateUtils.getSundayOfThisWeek();
        QueryBuilder queryBuilder = BaseApplication.getInstance().getBgEntityDao().queryBuilder();
        queryBuilder.where(BGEntityDao.Properties.UserId.mo34587eq(str), new WhereCondition[0]).where(BGEntityDao.Properties.CreateTime.between(Long.valueOf(mondayOfThisWeek), Long.valueOf(sundayOfThisWeek)), new WhereCondition[0]);
        return queryBuilder.list();
    }

    public static List<BGEntity> queryByHistoryWeek(String str, Date date) {
        long mondayOfHistoryWeek = DateUtils.getMondayOfHistoryWeek(date);
        long sundayOfHistoryWeek = DateUtils.getSundayOfHistoryWeek(date);
        QueryBuilder queryBuilder = BaseApplication.getInstance().getBgEntityDao().queryBuilder();
        queryBuilder.where(BGEntityDao.Properties.UserId.mo34587eq(str), new WhereCondition[0]).where(BGEntityDao.Properties.CreateTime.between(Long.valueOf(mondayOfHistoryWeek), Long.valueOf(sundayOfHistoryWeek)), new WhereCondition[0]);
        return queryBuilder.list();
    }

    public static List<BGEntity> queryByMonth(String str, int i, int i2) {
        QueryBuilder queryBuilder = BaseApplication.getInstance().getBgEntityDao().queryBuilder();
        queryBuilder.where(BGEntityDao.Properties.UserId.mo34587eq(str), queryBuilder.and(BGEntityDao.Properties.Year.mo34587eq(Integer.valueOf(i)), BGEntityDao.Properties.Month.mo34587eq(Integer.valueOf(i2)), new WhereCondition[0]));
        return queryBuilder.list();
    }
}
