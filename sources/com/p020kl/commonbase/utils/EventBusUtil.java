package com.p020kl.commonbase.utils;

import com.p020kl.commonbase.event.Event;
import org.greenrobot.eventbus.EventBus;

/* renamed from: com.kl.commonbase.utils.EventBusUtil */
public class EventBusUtil {
    public static void register(Object obj) {
        EventBus.getDefault().register(obj);
    }

    public static void unRegister(Object obj) {
        EventBus.getDefault().unregister(obj);
    }

    public static boolean isRegister(Object obj) {
        return EventBus.getDefault().isRegistered(obj);
    }

    public static void sendEvent(Event event) {
        EventBus.getDefault().post(event);
    }

    public static void sendStickyEvent(Event event) {
        EventBus.getDefault().postSticky(event);
    }
}
