package p035me.yokeyword.fragmentation.queue;

import androidx.fragment.app.FragmentManager;

/* renamed from: me.yokeyword.fragmentation.queue.Action */
public abstract class Action {
    public static final int ACTION_BACK = 3;
    public static final int ACTION_LOAD = 4;
    public static final int ACTION_NORMAL = 0;
    public static final int ACTION_POP = 1;
    public static final int ACTION_POP_MOCK = 2;
    public static final long DEFAULT_POP_TIME = 300;
    public int action;
    public long duration;
    public FragmentManager fragmentManager;

    public abstract void run();

    public Action() {
        this.action = 0;
        this.duration = 0;
    }

    public Action(int i) {
        this.action = 0;
        this.duration = 0;
        this.action = i;
    }

    public Action(int i, FragmentManager fragmentManager2) {
        this(i);
        this.fragmentManager = fragmentManager2;
    }
}
