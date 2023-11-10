package androidx.test.espresso.action;

import android.os.Build;
import android.view.KeyEvent;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.remote.annotation.RemoteMsgConstructor;
import androidx.test.espresso.remote.annotation.RemoteMsgField;
import java.util.Locale;

public final class EspressoKey {
    @RemoteMsgField(order = 0)
    private final int keyCode;
    @RemoteMsgField(order = 1)
    private final int metaState;

    public static class Builder {
        /* access modifiers changed from: private */
        public int builderKeyCode = -1;
        private boolean isAltPressed;
        private boolean isCtrlPressed;
        private boolean isShiftPressed;

        public EspressoKey build() {
            int i = this.builderKeyCode;
            Preconditions.checkState(i > 0 && i < KeyEvent.getMaxKeyCode(), "Invalid key code: %s", this.builderKeyCode);
            return new EspressoKey(this);
        }

        public Builder withAltPressed(boolean z) {
            this.isAltPressed = z;
            return this;
        }

        public Builder withCtrlPressed(boolean z) {
            this.isCtrlPressed = z;
            return this;
        }

        public Builder withKeyCode(int i) {
            this.builderKeyCode = i;
            return this;
        }

        public Builder withShiftPressed(boolean z) {
            this.isShiftPressed = z;
            return this;
        }

        /* access modifiers changed from: private */
        public int getMetaState() {
            boolean z = this.isShiftPressed;
            if (this.isAltPressed) {
                z |= true;
            }
            if (!this.isCtrlPressed) {
                return z ? 1 : 0;
            }
            if (Build.VERSION.SDK_INT >= 11) {
                return z | true ? 1 : 0;
            }
            return z;
        }
    }

    @RemoteMsgConstructor
    EspressoKey(int i, int i2) {
        this.keyCode = i;
        this.metaState = i2;
    }

    public int getKeyCode() {
        return this.keyCode;
    }

    public int getMetaState() {
        return this.metaState;
    }

    public String toString() {
        return String.format(Locale.ROOT, "keyCode: %s, metaState: %s", new Object[]{Integer.valueOf(this.keyCode), Integer.valueOf(this.metaState)});
    }

    private EspressoKey(Builder builder) {
        this(builder.builderKeyCode, builder.getMetaState());
    }
}
