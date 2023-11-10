package com.itextpdf.text.log;

public class SysoCounter implements Counter {
    protected String name;

    public SysoCounter() {
        this.name = "iText";
    }

    protected SysoCounter(Class<?> cls) {
        this.name = cls.getName();
    }

    public Counter getCounter(Class<?> cls) {
        return new SysoCounter(cls);
    }

    public void read(long j) {
        System.out.println(String.format("[%s] %s bytes read", new Object[]{this.name, Long.valueOf(j)}));
    }

    public void written(long j) {
        System.out.println(String.format("[%s] %s bytes written", new Object[]{this.name, Long.valueOf(j)}));
    }
}
