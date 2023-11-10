package androidx.test.espresso.action;

import android.database.Cursor;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.util.EspressoOptional;
import androidx.test.espresso.util.HumanReadables;
import java.util.Locale;

public interface AdapterViewProtocol {

    public static class AdaptedData {
        @Deprecated
        public final Object data;
        private final DataFunction dataFunction;
        public final Object opaqueToken;

        public static class Builder {
            /* access modifiers changed from: private */
            public Object data;
            private DataFunction dataFunction;
            private Object opaqueToken;

            public AdaptedData build() {
                DataFunction dataFunction2 = this.dataFunction;
                if (dataFunction2 != null) {
                    this.data = dataFunction2.getData();
                } else {
                    this.dataFunction = new DataFunction() {
                        public Object getData() {
                            return Builder.this.data;
                        }
                    };
                }
                return new AdaptedData(this.data, this.opaqueToken, this.dataFunction);
            }

            public Builder withData(Object obj) {
                this.data = obj;
                return this;
            }

            public Builder withDataFunction(DataFunction dataFunction2) {
                this.dataFunction = dataFunction2;
                return this;
            }

            public Builder withOpaqueToken(Object obj) {
                this.opaqueToken = obj;
                return this;
            }
        }

        private AdaptedData(Object obj, Object obj2, DataFunction dataFunction2) {
            this.data = obj;
            this.opaqueToken = Preconditions.checkNotNull(obj2);
            this.dataFunction = (DataFunction) Preconditions.checkNotNull(dataFunction2);
        }

        public Object getData() {
            return this.dataFunction.getData();
        }

        public String toString() {
            String str;
            Object data2 = getData();
            if (data2 == null) {
                str = "null";
            } else {
                str = data2.getClass().getName();
            }
            if (data2 instanceof Cursor) {
                data2 = HumanReadables.describe((Cursor) data2);
            }
            return String.format(Locale.ROOT, "Data: %s (class: %s) token: %s", new Object[]{data2, str, this.opaqueToken});
        }
    }

    public interface DataFunction {
        Object getData();
    }

    Iterable<AdaptedData> getDataInAdapterView(AdapterView<? extends Adapter> adapterView);

    EspressoOptional<AdaptedData> getDataRenderedByView(AdapterView<? extends Adapter> adapterView, View view);

    boolean isDataRenderedWithinAdapterView(AdapterView<? extends Adapter> adapterView, AdaptedData adaptedData);

    void makeDataRenderedWithinAdapterView(AdapterView<? extends Adapter> adapterView, AdaptedData adaptedData);
}
