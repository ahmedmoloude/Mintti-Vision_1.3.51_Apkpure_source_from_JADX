package com.p020kl.commonbase.data.p021db.manager;

import com.p020kl.commonbase.base.BaseApplication;
import com.p020kl.commonbase.bean.BTEntity;
import com.p020kl.commonbase.data.p021db.BTEntityDao;
import com.p020kl.commonbase.utils.DateUtils;
import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

/* renamed from: com.kl.commonbase.data.db.manager.BTTableManager */
public class BTTableManager {
    public static void insertEntity(BTEntity bTEntity) {
        BaseApplication.getInstance().getBtEntityDao().insert(bTEntity);
    }

    public static void deleteEntity(BTEntity bTEntity) {
        BaseApplication.getInstance().getBtEntityDao().delete(bTEntity);
    }

    public static void updateEntity(BTEntity bTEntity) {
        BaseApplication.getInstance().getBtEntityDao().update(bTEntity);
    }

    public static void deleteAllByMemberId(String str) {
        BaseApplication.getInstance().getBtEntityDao().queryBuilder().where(BTEntityDao.Properties.UserId.mo34587eq(str), new WhereCondition[0]).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    public static List<BTEntity> queryAll(String str) {
        return BaseApplication.getInstance().getBtEntityDao().queryBuilder().where(BTEntityDao.Properties.UserId.mo34587eq(str), new WhereCondition[0]).list();
    }

    public static List<BTEntity> queryByDay(String str, int i, int i2, int i3) {
        QueryBuilder queryBuilder = BaseApplication.getInstance().getBtEntityDao().queryBuilder();
        queryBuilder.where(BTEntityDao.Properties.UserId.mo34587eq(str), queryBuilder.and(BTEntityDao.Properties.Year.mo34587eq(Integer.valueOf(i)), BTEntityDao.Properties.Month.mo34587eq(Integer.valueOf(i2)), BTEntityDao.Properties.Day.mo34587eq(Integer.valueOf(i3))));
        return queryBuilder.list();
    }

    public static List<BTEntity> queryByWeek(String str) {
        long mondayOfThisWeek = DateUtils.getMondayOfThisWeek();
        long sundayOfThisWeek = DateUtils.getSundayOfThisWeek();
        QueryBuilder queryBuilder = BaseApplication.getInstance().getBtEntityDao().queryBuilder();
        queryBuilder.where(BTEntityDao.Properties.UserId.mo34587eq(str), new WhereCondition[0]).where(BTEntityDao.Properties.CreateTime.between(Long.valueOf(mondayOfThisWeek), Long.valueOf(sundayOfThisWeek)), new WhereCondition[0]);
        return queryBuilder.list();
    }

    public static List<BTEntity> queryByHistoryWeek(String str, Date date) {
        if (date == null) {
            return queryByWeek(str);
        }
        long mondayOfHistoryWeek = DateUtils.getMondayOfHistoryWeek(date);
        long sundayOfHistoryWeek = DateUtils.getSundayOfHistoryWeek(date);
        QueryBuilder queryBuilder = BaseApplication.getInstance().getBtEntityDao().queryBuilder();
        queryBuilder.where(BTEntityDao.Properties.UserId.mo34587eq(str), new WhereCondition[0]).where(BTEntityDao.Properties.CreateTime.between(Long.valueOf(mondayOfHistoryWeek), Long.valueOf(sundayOfHistoryWeek)), new WhereCondition[0]);
        return queryBuilder.list();
    }

    public static List<BTEntity> queryByMonth(String str, int i, int i2) {
        QueryBuilder queryBuilder = BaseApplication.getInstance().getBtEntityDao().queryBuilder();
        queryBuilder.where(BTEntityDao.Properties.UserId.mo34587eq(str), queryBuilder.and(BTEntityDao.Properties.Year.mo34587eq(Integer.valueOf(i)), BTEntityDao.Properties.Month.mo34587eq(Integer.valueOf(i2)), new WhereCondition[0]));
        return queryBuilder.list();
    }
}
