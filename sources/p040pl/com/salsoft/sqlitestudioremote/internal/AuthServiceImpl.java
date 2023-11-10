package p040pl.com.salsoft.sqlitestudioremote.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/* renamed from: pl.com.salsoft.sqlitestudioremote.internal.AuthServiceImpl */
public class AuthServiceImpl implements AuthService {
    private List<Pattern> ipBlackList;
    private List<Pattern> ipWhiteList;
    private String password;

    public AuthServiceImpl(String str, List<String> list, List<String> list2) {
        this.password = str;
        if (list != null) {
            this.ipBlackList = new ArrayList();
            for (String createRegexFromGlob : list) {
                this.ipBlackList.add(Pattern.compile(Utils.createRegexFromGlob(createRegexFromGlob)));
            }
        }
        if (list2 != null) {
            this.ipWhiteList = new ArrayList();
            for (String createRegexFromGlob2 : list2) {
                this.ipWhiteList.add(Pattern.compile(Utils.createRegexFromGlob(createRegexFromGlob2)));
            }
        }
    }

    public boolean isAuthRequired() {
        String str = this.password;
        return str != null && !str.isEmpty();
    }

    public boolean authorize(String str) {
        return str != null && str.equals(this.password);
    }

    public boolean isIpAllowed(String str) {
        boolean z;
        List<Pattern> list = this.ipBlackList;
        if (list == null) {
            return true;
        }
        Iterator<Pattern> it = list.iterator();
        while (true) {
            if (it.hasNext()) {
                if (it.next().matcher(str).matches()) {
                    z = false;
                    break;
                }
            } else {
                z = true;
                break;
            }
        }
        List<Pattern> list2 = this.ipWhiteList;
        if (list2 == null) {
            return z;
        }
        for (Pattern matcher : list2) {
            if (matcher.matcher(str).matches()) {
                return true;
            }
        }
        return z;
    }
}
