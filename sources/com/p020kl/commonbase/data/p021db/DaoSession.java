package com.p020kl.commonbase.data.p021db;

import com.p020kl.commonbase.bean.BGEntity;
import com.p020kl.commonbase.bean.BPEntity;
import com.p020kl.commonbase.bean.BTEntity;
import com.p020kl.commonbase.bean.ECGEntity;
import com.p020kl.commonbase.bean.Spo2Entity;
import com.p020kl.commonbase.bean.UserInfoEntity;
import java.util.Map;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

/* renamed from: com.kl.commonbase.data.db.DaoSession */
public class DaoSession extends AbstractDaoSession {
    private final BGEntityDao bGEntityDao;
    private final DaoConfig bGEntityDaoConfig;
    private final BPEntityDao bPEntityDao;
    private final DaoConfig bPEntityDaoConfig;
    private final BTEntityDao bTEntityDao;
    private final DaoConfig bTEntityDaoConfig;
    private final ECGEntityDao eCGEntityDao;
    private final DaoConfig eCGEntityDaoConfig;
    private final Spo2EntityDao spo2EntityDao;
    private final DaoConfig spo2EntityDaoConfig;
    private final UserInfoEntityDao userInfoEntityDao;
    private final DaoConfig userInfoEntityDaoConfig;

    public DaoSession(Database database, IdentityScopeType identityScopeType, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> map) {
        super(database);
        DaoConfig clone = map.get(BGEntityDao.class).clone();
        this.bGEntityDaoConfig = clone;
        clone.initIdentityScope(identityScopeType);
        DaoConfig clone2 = map.get(BPEntityDao.class).clone();
        this.bPEntityDaoConfig = clone2;
        clone2.initIdentityScope(identityScopeType);
        DaoConfig clone3 = map.get(BTEntityDao.class).clone();
        this.bTEntityDaoConfig = clone3;
        clone3.initIdentityScope(identityScopeType);
        DaoConfig clone4 = map.get(ECGEntityDao.class).clone();
        this.eCGEntityDaoConfig = clone4;
        clone4.initIdentityScope(identityScopeType);
        DaoConfig clone5 = map.get(Spo2EntityDao.class).clone();
        this.spo2EntityDaoConfig = clone5;
        clone5.initIdentityScope(identityScopeType);
        DaoConfig clone6 = map.get(UserInfoEntityDao.class).clone();
        this.userInfoEntityDaoConfig = clone6;
        clone6.initIdentityScope(identityScopeType);
        BGEntityDao bGEntityDao2 = new BGEntityDao(clone, this);
        this.bGEntityDao = bGEntityDao2;
        BPEntityDao bPEntityDao2 = new BPEntityDao(clone2, this);
        this.bPEntityDao = bPEntityDao2;
        BTEntityDao bTEntityDao2 = new BTEntityDao(clone3, this);
        this.bTEntityDao = bTEntityDao2;
        ECGEntityDao eCGEntityDao2 = new ECGEntityDao(clone4, this);
        this.eCGEntityDao = eCGEntityDao2;
        Spo2EntityDao spo2EntityDao2 = new Spo2EntityDao(clone5, this);
        this.spo2EntityDao = spo2EntityDao2;
        UserInfoEntityDao userInfoEntityDao2 = new UserInfoEntityDao(clone6, this);
        this.userInfoEntityDao = userInfoEntityDao2;
        registerDao(BGEntity.class, bGEntityDao2);
        registerDao(BPEntity.class, bPEntityDao2);
        registerDao(BTEntity.class, bTEntityDao2);
        registerDao(ECGEntity.class, eCGEntityDao2);
        registerDao(Spo2Entity.class, spo2EntityDao2);
        registerDao(UserInfoEntity.class, userInfoEntityDao2);
    }

    public void clear() {
        this.bGEntityDaoConfig.clearIdentityScope();
        this.bPEntityDaoConfig.clearIdentityScope();
        this.bTEntityDaoConfig.clearIdentityScope();
        this.eCGEntityDaoConfig.clearIdentityScope();
        this.spo2EntityDaoConfig.clearIdentityScope();
        this.userInfoEntityDaoConfig.clearIdentityScope();
    }

    public BGEntityDao getBGEntityDao() {
        return this.bGEntityDao;
    }

    public BPEntityDao getBPEntityDao() {
        return this.bPEntityDao;
    }

    public BTEntityDao getBTEntityDao() {
        return this.bTEntityDao;
    }

    public ECGEntityDao getECGEntityDao() {
        return this.eCGEntityDao;
    }

    public Spo2EntityDao getSpo2EntityDao() {
        return this.spo2EntityDao;
    }

    public UserInfoEntityDao getUserInfoEntityDao() {
        return this.userInfoEntityDao;
    }
}
