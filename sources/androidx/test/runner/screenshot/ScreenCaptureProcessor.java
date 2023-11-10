package androidx.test.runner.screenshot;

import java.io.IOException;

public interface ScreenCaptureProcessor {
    String process(ScreenCapture screenCapture) throws IOException;
}
