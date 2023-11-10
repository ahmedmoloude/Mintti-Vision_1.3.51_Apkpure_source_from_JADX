package p028io.jsonwebtoken.impl;

import com.p020kl.commonbase.constants.Constants;

@Deprecated
/* renamed from: io.jsonwebtoken.impl.DefaultTextCodecFactory */
public class DefaultTextCodecFactory implements TextCodecFactory {
    /* access modifiers changed from: protected */
    public String getSystemProperty(String str) {
        return System.getProperty(str);
    }

    /* access modifiers changed from: protected */
    public boolean isAndroid() {
        String systemProperty = getSystemProperty("java.vm.name");
        if (systemProperty != null) {
            return systemProperty.toLowerCase().contains("dalvik");
        }
        String systemProperty2 = getSystemProperty("java.vm.vendor");
        if (systemProperty2 != null) {
            return systemProperty2.toLowerCase().contains(Constants.f840OS);
        }
        return false;
    }

    public TextCodec getTextCodec() {
        if (isAndroid()) {
            return new AndroidBase64Codec();
        }
        return new Base64Codec();
    }
}
