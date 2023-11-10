package androidx.test.runner.permission;

public abstract class ShellCommand {
    private static final String SAFE_PUNCTUATION = "@%-_+:,./";

    /* access modifiers changed from: package-private */
    public abstract void execute() throws Exception;

    static String shellEscape(String str) {
        int length = str.length();
        if (length == 0) {
            return "''";
        }
        int i = 0;
        while (i < length) {
            char charAt = str.charAt(i);
            if (Character.isLetterOrDigit(charAt) || SAFE_PUNCTUATION.indexOf(charAt) != -1) {
                i++;
            } else {
                String replace = str.replace("'", "'\\''");
                StringBuilder sb = new StringBuilder(String.valueOf(replace).length() + 2);
                sb.append("'");
                sb.append(replace);
                sb.append("'");
                return sb.toString();
            }
        }
        return str;
    }
}
