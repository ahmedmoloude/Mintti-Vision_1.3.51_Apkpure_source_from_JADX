package com.itextpdf.text.log;

public final class NoOpLogger implements Logger {
    public void debug(String str) {
    }

    public void error(String str) {
    }

    public void error(String str, Exception exc) {
    }

    public Logger getLogger(Class<?> cls) {
        return this;
    }

    public Logger getLogger(String str) {
        return this;
    }

    public void info(String str) {
    }

    public boolean isLogging(Level level) {
        return false;
    }

    public void trace(String str) {
    }

    public void warn(String str) {
    }
}
