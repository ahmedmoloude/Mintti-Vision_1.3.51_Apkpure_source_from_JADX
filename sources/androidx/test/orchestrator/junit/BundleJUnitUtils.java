package androidx.test.orchestrator.junit;

import android.os.Bundle;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public final class BundleJUnitUtils {
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_FAILURE = "failure";
    private static final String KEY_RESULT = "result";

    private BundleJUnitUtils() {
    }

    public static Bundle getBundleFromDescription(Description description) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("description", new ParcelableDescription(description));
        return bundle;
    }

    public static Bundle getBundleFromFailure(Failure failure) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_FAILURE, new ParcelableFailure(failure));
        return bundle;
    }

    public static Bundle getBundleFromResult(Result result) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_RESULT, new ParcelableResult(result));
        return bundle;
    }

    public static Bundle getBundleFromThrowable(Description description, Throwable th) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_FAILURE, new ParcelableFailure(new ParcelableDescription(description), th));
        return bundle;
    }

    public static ParcelableDescription getDescription(Bundle bundle) {
        return (ParcelableDescription) bundle.getParcelable("description");
    }

    public static ParcelableFailure getFailure(Bundle bundle) {
        return (ParcelableFailure) bundle.getParcelable(KEY_FAILURE);
    }

    public static ParcelableResult getResult(Bundle bundle) {
        return (ParcelableResult) bundle.getParcelable(KEY_RESULT);
    }
}
