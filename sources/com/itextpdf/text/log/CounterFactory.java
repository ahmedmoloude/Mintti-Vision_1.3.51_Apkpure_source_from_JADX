package com.itextpdf.text.log;

public class CounterFactory {
    private static CounterFactory myself = new CounterFactory();
    private Counter counter = new DefaultCounter();

    private CounterFactory() {
    }

    public static CounterFactory getInstance() {
        return myself;
    }

    public static Counter getCounter(Class<?> cls) {
        return myself.counter.getCounter(cls);
    }

    public Counter getCounter() {
        return this.counter;
    }

    public void setCounter(Counter counter2) {
        this.counter = counter2;
    }
}
