package com.itextpdf.text.log;

import p028io.jsonwebtoken.JwtParser;

public class SysoLogger implements Logger {
    private String name;
    private final int shorten;

    public boolean isLogging(Level level) {
        return true;
    }

    public SysoLogger() {
        this(1);
    }

    public SysoLogger(int i) {
        this.shorten = i;
    }

    protected SysoLogger(String str, int i) {
        this.shorten = i;
        this.name = str;
    }

    public Logger getLogger(Class<?> cls) {
        return new SysoLogger(cls.getName(), this.shorten);
    }

    public Logger getLogger(String str) {
        return new SysoLogger("[itext]", 0);
    }

    public void warn(String str) {
        System.out.println(String.format("%s WARN  %s", new Object[]{shorten(this.name), str}));
    }

    private String shorten(String str) {
        if (this.shorten == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        int indexOf = str.indexOf(46);
        String str2 = str;
        while (indexOf != -1) {
            int i = this.shorten;
            if (indexOf < i) {
                i = indexOf;
            }
            sb.append(str2.substring(0, i));
            sb.append(JwtParser.SEPARATOR_CHAR);
            str2 = str2.substring(indexOf + 1);
            indexOf = str2.indexOf(46);
        }
        sb.append(str.substring(str.lastIndexOf(46) + 1));
        return sb.toString();
    }

    public void trace(String str) {
        System.out.println(String.format("%s TRACE %s", new Object[]{shorten(this.name), str}));
    }

    public void debug(String str) {
        System.out.println(String.format("%s DEBUG %s", new Object[]{shorten(this.name), str}));
    }

    public void info(String str) {
        System.out.println(String.format("%s INFO  %s", new Object[]{shorten(this.name), str}));
    }

    public void error(String str) {
        System.out.println(String.format("%s ERROR %s", new Object[]{this.name, str}));
    }

    public void error(String str, Exception exc) {
        System.out.println(String.format("%s ERROR %s", new Object[]{this.name, str}));
        exc.printStackTrace(System.out);
    }
}
