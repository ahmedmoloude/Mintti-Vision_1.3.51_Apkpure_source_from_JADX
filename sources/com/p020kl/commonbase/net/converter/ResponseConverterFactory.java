package com.p020kl.commonbase.net.converter;

import com.google.gson.Gson;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Objects;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/* renamed from: com.kl.commonbase.net.converter.ResponseConverterFactory */
public class ResponseConverterFactory extends Converter.Factory {
    private Gson gson = null;

    public static ResponseConverterFactory create() {
        return create(new Gson());
    }

    public static ResponseConverterFactory create(Gson gson2) {
        return new ResponseConverterFactory(gson2);
    }

    private ResponseConverterFactory(Gson gson2) {
        Objects.requireNonNull(gson2, "gson == null");
        this.gson = gson2;
    }

    public ResponseConverterFactory() {
    }

    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotationArr, Retrofit retrofit) {
        return new GsonResponseBodyConverter(this.gson, type);
    }
}
