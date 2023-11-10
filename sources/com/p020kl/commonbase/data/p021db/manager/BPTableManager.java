package com.p020kl.commonbase.data.p021db.manager;

import com.p020kl.commonbase.base.BaseApplication;
import com.p020kl.commonbase.bean.BPEntity;
import com.p020kl.commonbase.data.p021db.BPEntityDao;
import com.p020kl.commonbase.utils.DateUtils;
import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

/* renamed from: com.kl.commonbase.data.db.manager.BPTableManager */
public class BPTableManager {
    public static void insertEntity(BPEntity bPEntity) {
        BaseApplication.getInstance().getBpEntityDao().insert(bPEntity);
    }

    public static void deleteEntity(BPEntity bPEntity) {
        BaseApplication.getInstance().getBpEntityDao().delete(bPEntity);
    }

    public static void updateEntity(BPEntity bPEntity) {
        BaseApplication.getInstance().getBpEntityDao().update(bPEntity);
    }

    public static void deleteAllByMemberId(String str) {
        BaseApplication.getInstance().getBpEntityDao().queryBuilder().where(BPEntityDao.Properties.UserId.mo34587eq(str), new WhereCondition[0]).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    public static List<BPEntity> queryAll(String str) {
        return BaseApplication.getInstance().getBpEntityDao().queryBuilder().where(BPEntityDao.Properties.UserId.mo34587eq(str), new WhereCondition[0]).list();
    }

    public static List<BPEntity> queryByDay(String str, int i, int i2, int i3) {
        QueryBuilder queryBuilder = BaseApplication.getInstance().getBpEntityDao().queryBuilder();
        queryBuilder.where(BPEntityDao.Properties.UserId.mo34587eq(str), queryBuilder.and(BPEntityDao.Properties.Year.mo34587eq(Integer.valueOf(i)), BPEntityDao.Properties.Month.mo34587eq(Integer.valueOf(i2)), BPEntityDao.Properties.Day.mo34587eq(Integer.valueOf(i3))));
        return queryBuilder.list();
    }

    public static List<BPEntity> queryByWeek(String str) {
        long mondayOfThisWeek = DateUtils.getMondayOfThisWeek();
        long sundayOfThisWeek = DateUtils.getSundayOfThisWeek();
        QueryBuilder queryBuilder = BaseApplication.getInstance().getBpEntityDao().queryBuilder();
        queryBuilder.where(BPEntityDao.Properties.UserId.mo34587eq(str), new WhereCondition[0]).where(BPEntityDao.Properties.CreateTime.between(Long.valueOf(mondayOfThisWeek), Long.valueOf(sundayOfThisWeek)), new WhereCondition[0]);
        return queryBuilder.list();
    }

    public static List<BPEntity> queryByHistoryWeek(String str, Date date) {
        long mondayOfHistoryWeek = DateUtils.getMondayOfHistoryWeek(date);
        long sundayOfHistoryWeek = DateUtils.getSundayOfHistoryWeek(date);
        QueryBuilder queryBuilder = BaseApplication.getInstance().getBpEntityDao().queryBuilder();
        queryBuilder.where(BPEntityDao.Properties.UserId.mo34587eq(str), new WhereCondition[0]).where(BPEntityDao.Properties.CreateTime.between(Long.valueOf(mondayOfHistoryWeek), Long.valueOf(sundayOfHistoryWeek)), new WhereCondition[0]);
        return queryBuilder.list();
    }

    public static List<BPEntity> queryByMonth(String str, int i, int i2) {
        QueryBuilder queryBuilder = BaseApplication.getInstance().getBpEntityDao().queryBuilder();
        queryBuilder.where(BPEntityDao.Properties.UserId.mo34587eq(str), queryBuilder.and(BPEntityDao.Properties.Year.mo34587eq(Integer.valueOf(i)), BPEntityDao.Properties.Month.mo34587eq(Integer.valueOf(i2)), new WhereCondition[0]));
        return queryBuilder.list();
    }
}
