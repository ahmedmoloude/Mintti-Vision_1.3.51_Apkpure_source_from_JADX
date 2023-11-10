package com.kongzue.dialogx.interfaces;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.kongzue.dialogx.interfaces.BaseDialog;

public abstract class DialogLifecycleCallback<T extends BaseDialog> implements LifecycleOwner {
    private final LifecycleRegistry registry = new LifecycleRegistry(this);

    public void onShow(T t) {
        try {
            if (this.registry.getCurrentState() != Lifecycle.State.CREATED) {
                this.registry.setCurrentState(Lifecycle.State.CREATED);
            }
        } catch (Exception unused) {
        }
    }

    public void onDismiss(T t) {
        try {
            if (this.registry.getCurrentState() != Lifecycle.State.DESTROYED) {
                this.registry.setCurrentState(Lifecycle.State.DESTROYED);
            }
        } catch (Exception unused) {
        }
    }

    public Lifecycle getLifecycle() {
        return this.registry;
    }
}
