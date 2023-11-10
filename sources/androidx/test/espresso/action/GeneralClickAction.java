package androidx.test.espresso.action;

import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.webkit.WebView;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.Tapper;
import androidx.test.espresso.core.internal.deps.guava.base.Optional;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.util.HumanReadables;
import java.util.Locale;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public final class GeneralClickAction implements ViewAction {
    private static final String TAG = "GeneralClickAction";
    private final int buttonState;
    final CoordinatesProvider coordinatesProvider;
    private final int inputDevice;
    final PrecisionDescriber precisionDescriber;
    private final Optional<ViewAction> rollbackAction;
    final Tapper tapper;

    @Deprecated
    public GeneralClickAction(Tapper tapper2, CoordinatesProvider coordinatesProvider2, PrecisionDescriber precisionDescriber2) {
        this(tapper2, coordinatesProvider2, precisionDescriber2, 0, 0, (ViewAction) null);
    }

    public Matcher<View> getConstraints() {
        Matcher<View> isDisplayingAtLeast = ViewMatchers.isDisplayingAtLeast(90);
        return this.rollbackAction.isPresent() ? Matchers.allOf(isDisplayingAtLeast, this.rollbackAction.get().getConstraints()) : isDisplayingAtLeast;
    }

    public String getDescription() {
        return String.valueOf(this.tapper.toString().toLowerCase()).concat(" click");
    }

    public void perform(UiController uiController, View view) {
        char c;
        UiController uiController2 = uiController;
        View view2 = view;
        float[] calculateCoordinates = this.coordinatesProvider.calculateCoordinates(view2);
        float[] describePrecision = this.precisionDescriber.describePrecision();
        Tapper.Status status = Tapper.Status.FAILURE;
        int i = 0;
        while (true) {
            if (status == Tapper.Status.SUCCESS || i >= 3) {
                c = 3;
            } else {
                try {
                    c = 3;
                    try {
                        status = this.tapper.sendTap(uiController, calculateCoordinates, describePrecision, this.inputDevice, this.buttonState);
                        if (Log.isLoggable(TAG, 3)) {
                            String valueOf = String.valueOf(String.format(Locale.ROOT, "%s - At Coordinates: %d, %d and precision: %d, %d", new Object[]{getDescription(), Integer.valueOf((int) calculateCoordinates[0]), Integer.valueOf((int) calculateCoordinates[1]), Integer.valueOf((int) describePrecision[0]), Integer.valueOf((int) describePrecision[1])}));
                            Log.d(TAG, valueOf.length() != 0 ? "perform: ".concat(valueOf) : new String("perform: "));
                        }
                        int pressedStateDuration = ViewConfiguration.getPressedStateDuration();
                        if (pressedStateDuration > 0) {
                            uiController2.loopMainThreadForAtLeast((long) pressedStateDuration);
                        }
                        if (status == Tapper.Status.WARNING) {
                            if (!this.rollbackAction.isPresent()) {
                                break;
                            }
                            this.rollbackAction.get().perform(uiController2, view2);
                        }
                        i++;
                    } catch (RuntimeException e) {
                        e = e;
                        PerformException.Builder builder = new PerformException.Builder();
                        Locale locale = Locale.ROOT;
                        Object[] objArr = new Object[5];
                        objArr[0] = getDescription();
                        objArr[1] = Integer.valueOf((int) calculateCoordinates[0]);
                        objArr[2] = Integer.valueOf((int) calculateCoordinates[1]);
                        objArr[c] = Integer.valueOf((int) describePrecision[0]);
                        objArr[4] = Integer.valueOf((int) describePrecision[1]);
                        throw builder.withActionDescription(String.format(locale, "%s - At Coordinates: %d, %d and precision: %d, %d", objArr)).withViewDescription(HumanReadables.describe(view)).withCause(e).build();
                    }
                } catch (RuntimeException e2) {
                    e = e2;
                    c = 3;
                    PerformException.Builder builder2 = new PerformException.Builder();
                    Locale locale2 = Locale.ROOT;
                    Object[] objArr2 = new Object[5];
                    objArr2[0] = getDescription();
                    objArr2[1] = Integer.valueOf((int) calculateCoordinates[0]);
                    objArr2[2] = Integer.valueOf((int) calculateCoordinates[1]);
                    objArr2[c] = Integer.valueOf((int) describePrecision[0]);
                    objArr2[4] = Integer.valueOf((int) describePrecision[1]);
                    throw builder2.withActionDescription(String.format(locale2, "%s - At Coordinates: %d, %d and precision: %d, %d", objArr2)).withViewDescription(HumanReadables.describe(view)).withCause(e).build();
                }
            }
        }
        c = 3;
        if (status == Tapper.Status.FAILURE) {
            PerformException.Builder withViewDescription = new PerformException.Builder().withActionDescription(getDescription()).withViewDescription(HumanReadables.describe(view));
            Locale locale3 = Locale.ROOT;
            Object[] objArr3 = new Object[9];
            objArr3[0] = Float.valueOf(calculateCoordinates[0]);
            objArr3[1] = Float.valueOf(calculateCoordinates[1]);
            objArr3[2] = Float.valueOf(describePrecision[0]);
            objArr3[c] = Float.valueOf(describePrecision[1]);
            objArr3[4] = this.tapper;
            objArr3[5] = this.coordinatesProvider;
            objArr3[6] = this.precisionDescriber;
            objArr3[7] = Integer.valueOf(i);
            objArr3[8] = Boolean.valueOf(this.rollbackAction.isPresent());
            throw withViewDescription.withCause(new RuntimeException(String.format(locale3, "Couldn't click at: %s,%s precision: %s, %s . Tapper: %s coordinate provider: %s precision describer: %s. Tried %s times. With Rollback? %s", objArr3))).build();
        } else if (this.tapper == Tap.SINGLE && (view2 instanceof WebView)) {
            uiController2.loopMainThreadForAtLeast((long) ViewConfiguration.getDoubleTapTimeout());
        }
    }

    public GeneralClickAction(Tapper tapper2, CoordinatesProvider coordinatesProvider2, PrecisionDescriber precisionDescriber2, int i, int i2) {
        this(tapper2, coordinatesProvider2, precisionDescriber2, i, i2, (ViewAction) null);
    }

    public GeneralClickAction(Tapper tapper2, CoordinatesProvider coordinatesProvider2, PrecisionDescriber precisionDescriber2, int i, int i2, ViewAction viewAction) {
        this.coordinatesProvider = coordinatesProvider2;
        this.tapper = tapper2;
        this.precisionDescriber = precisionDescriber2;
        this.inputDevice = i;
        this.buttonState = i2;
        this.rollbackAction = Optional.fromNullable(viewAction);
    }

    @Deprecated
    public GeneralClickAction(Tapper tapper2, CoordinatesProvider coordinatesProvider2, PrecisionDescriber precisionDescriber2, ViewAction viewAction) {
        this(tapper2, coordinatesProvider2, precisionDescriber2, 0, 0, viewAction);
    }
}
