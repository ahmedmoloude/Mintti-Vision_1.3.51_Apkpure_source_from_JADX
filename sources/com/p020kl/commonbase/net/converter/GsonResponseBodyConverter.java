package com.p020kl.commonbase.net.converter;

import com.google.gson.Gson;
import com.p020kl.commonbase.net.entity.ResponseResult;
import java.io.IOException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/* renamed from: com.kl.commonbase.net.converter.GsonResponseBodyConverter */
public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private Gson gson;
    private Type type;

    public GsonResponseBodyConverter(Gson gson2, Type type2) {
        this.gson = gson2;
        this.type = type2;
    }

    @Nullable
    public T convert(ResponseBody responseBody) throws IOException {
        String string = responseBody.string();
        ResponseResult responseResult = (ResponseResult) this.gson.fromJson(string, ResponseResult.class);
        return this.gson.fromJson(string, this.type);
    }
}
