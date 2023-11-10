package org.greenrobot.greendao;

import java.util.Collection;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.query.WhereCondition;

public class Property {
    public final String columnName;
    public final String name;
    public final int ordinal;
    public final boolean primaryKey;
    public final Class<?> type;

    public Property(int i, Class<?> cls, String str, boolean z, String str2) {
        this.ordinal = i;
        this.type = cls;
        this.name = str;
        this.primaryKey = z;
        this.columnName = str2;
    }

    /* renamed from: eq */
    public WhereCondition mo34587eq(Object obj) {
        return new WhereCondition.PropertyCondition(this, "=?", obj);
    }

    public WhereCondition notEq(Object obj) {
        return new WhereCondition.PropertyCondition(this, "<>?", obj);
    }

    public WhereCondition like(String str) {
        return new WhereCondition.PropertyCondition(this, " LIKE ?", (Object) str);
    }

    public WhereCondition between(Object obj, Object obj2) {
        return new WhereCondition.PropertyCondition(this, " BETWEEN ? AND ?", new Object[]{obj, obj2});
    }

    /* renamed from: in */
    public WhereCondition mo34591in(Object... objArr) {
        StringBuilder sb = new StringBuilder(" IN (");
        SqlUtils.appendPlaceholders(sb, objArr.length).append(')');
        return new WhereCondition.PropertyCondition(this, sb.toString(), objArr);
    }

    /* renamed from: in */
    public WhereCondition mo34590in(Collection<?> collection) {
        return mo34591in(collection.toArray());
    }

    public WhereCondition notIn(Object... objArr) {
        StringBuilder sb = new StringBuilder(" NOT IN (");
        SqlUtils.appendPlaceholders(sb, objArr.length).append(')');
        return new WhereCondition.PropertyCondition(this, sb.toString(), objArr);
    }

    public WhereCondition notIn(Collection<?> collection) {
        return notIn(collection.toArray());
    }

    /* renamed from: gt */
    public WhereCondition mo34589gt(Object obj) {
        return new WhereCondition.PropertyCondition(this, ">?", obj);
    }

    /* renamed from: lt */
    public WhereCondition mo34596lt(Object obj) {
        return new WhereCondition.PropertyCondition(this, "<?", obj);
    }

    /* renamed from: ge */
    public WhereCondition mo34588ge(Object obj) {
        return new WhereCondition.PropertyCondition(this, ">=?", obj);
    }

    /* renamed from: le */
    public WhereCondition mo34594le(Object obj) {
        return new WhereCondition.PropertyCondition(this, "<=?", obj);
    }

    public WhereCondition isNull() {
        return new WhereCondition.PropertyCondition(this, " IS NULL");
    }

    public WhereCondition isNotNull() {
        return new WhereCondition.PropertyCondition(this, " IS NOT NULL");
    }
}
