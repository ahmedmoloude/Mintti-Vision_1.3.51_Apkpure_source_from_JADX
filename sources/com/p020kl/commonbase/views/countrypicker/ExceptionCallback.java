package com.p020kl.commonbase.views.countrypicker;

import java.io.IOException;
import org.json.JSONException;

/* renamed from: com.kl.commonbase.views.countrypicker.ExceptionCallback */
public abstract class ExceptionCallback {
    public abstract void onIOException(IOException iOException);

    public abstract void onJSONException(JSONException jSONException);
}
