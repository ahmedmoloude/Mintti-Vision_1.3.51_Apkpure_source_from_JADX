package com.itextpdf.text.pdf.security;

import org.bouncycastle.tsp.TimeStampTokenInfo;

public interface TSAInfoBouncyCastle {
    void inspectTimeStampTokenInfo(TimeStampTokenInfo timeStampTokenInfo);
}
