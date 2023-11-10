package com.haibin.calendarview;

import android.content.Context;

public final class TrunkBranchAnnals {
    private static String[] BRANCH_STR;
    private static String[] TRUNK_STR;

    public static void init(Context context) {
        if (TRUNK_STR == null) {
            TRUNK_STR = context.getResources().getStringArray(C1484R.array.trunk_string_array);
            BRANCH_STR = context.getResources().getStringArray(C1484R.array.branch_string_array);
        }
    }

    public static String getTrunkString(int i) {
        return TRUNK_STR[getTrunkInt(i)];
    }

    public static int getTrunkInt(int i) {
        int i2 = i % 10;
        if (i2 == 0) {
            return 9;
        }
        return i2 - 1;
    }

    public static String getBranchString(int i) {
        return BRANCH_STR[getBranchInt(i)];
    }

    public static int getBranchInt(int i) {
        int i2 = i % 12;
        if (i2 == 0) {
            return 11;
        }
        return i2 - 1;
    }

    public static String getTrunkBranchYear(int i) {
        return String.format("%s%s", new Object[]{getTrunkString(i), getBranchString(i)});
    }
}
