package com.p020kl.commonbase.views.countrypicker;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.p020kl.commonbase.utils.JsonUtils;
import com.p020kl.commonbase.utils.PinyinUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.kl.commonbase.views.countrypicker.Country */
public class Country implements PyEntity {
    private static final String TAG = "Country";
    private static ArrayList<Country> countries;
    public int code;
    public int flag;
    public String locale;
    public String name;
    public String pinyin;

    public Country(int i, String str, String str2, int i2) {
        this.code = i;
        this.name = str;
        this.flag = i2;
        this.locale = str2;
    }

    public String toString() {
        return "Country{status='" + this.code + '\'' + "flag='" + this.flag + '\'' + ", name='" + this.name + '\'' + '}';
    }

    public static ArrayList<Country> getAll(Context context, ExceptionCallback exceptionCallback) {
        int i;
        ArrayList<Country> arrayList = countries;
        if (arrayList != null) {
            return arrayList;
        }
        countries = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getResources().getAssets().open("code.json")));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                sb.append(readLine);
            }
            bufferedReader.close();
            JSONArray jSONArray = new JSONArray(sb.toString());
            String key = getKey(context);
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i2);
                String string = jSONObject.getString("locale");
                if (!TextUtils.isEmpty(string)) {
                    Resources resources = context.getResources();
                    i = resources.getIdentifier("flag_" + string.toLowerCase(), "drawable", context.getPackageName());
                } else {
                    i = 0;
                }
                countries.add(new Country(jSONObject.getInt("code"), jSONObject.getString(key), string, i));
            }
        } catch (IOException e) {
            if (exceptionCallback != null) {
                exceptionCallback.onIOException(e);
            }
            e.printStackTrace();
        } catch (JSONException e2) {
            if (exceptionCallback != null) {
                exceptionCallback.onJSONException(e2);
            }
            e2.printStackTrace();
        }
        return countries;
    }

    public static Country fromJson(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            return new Country(jSONObject.optInt("code"), jSONObject.optString("name"), jSONObject.optString("locale"), jSONObject.optInt("flag"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toJson() {
        return JsonUtils.toJsonString(this);
    }

    public static void destroy() {
        countries = null;
    }

    private static String getKey(Context context) {
        String language = Locale.getDefault().getLanguage();
        String country = context.getResources().getConfiguration().locale.getCountry();
        if (!"zh".equalsIgnoreCase(language) || !"CN".equalsIgnoreCase(country)) {
            return "en";
        }
        return "zh";
    }

    private static boolean inChina(Context context) {
        return "CN".equalsIgnoreCase(context.getResources().getConfiguration().locale.getCountry());
    }

    public int hashCode() {
        return this.code;
    }

    public String getPinyin() {
        if (this.pinyin == null) {
            this.pinyin = PinyinUtil.getPingYin(this.name);
        }
        return this.pinyin;
    }
}
