package org.hamcrest;

import org.hamcrest.core.IsEqual;
import org.hamcrest.integration.EasyMock2Adapter;

public class EasyMock2Matchers {
    public static String equalTo(String str) {
        EasyMock2Adapter.adapt(IsEqual.equalTo(str));
        return null;
    }
}
