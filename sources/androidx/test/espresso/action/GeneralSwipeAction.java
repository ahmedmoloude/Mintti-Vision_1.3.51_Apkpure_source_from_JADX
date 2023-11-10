package androidx.test.espresso.action;

import android.view.View;
import android.view.ViewConfiguration;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.Swiper;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.util.HumanReadables;
import java.util.Locale;
import org.hamcrest.Matcher;

public final class GeneralSwipeAction implements ViewAction {
    private static final int MAX_TRIES = 3;
    private static final int VIEW_DISPLAY_PERCENTAGE = 90;
    final CoordinatesProvider endCoordinatesProvider;
    final PrecisionDescriber precisionDescriber;
    final CoordinatesProvider startCoordinatesProvider;
    final Swiper swiper;

    public GeneralSwipeAction(Swiper swiper2, CoordinatesProvider coordinatesProvider, CoordinatesProvider coordinatesProvider2, PrecisionDescriber precisionDescriber2) {
        this.swiper = swiper2;
        this.startCoordinatesProvider = coordinatesProvider;
        this.endCoordinatesProvider = coordinatesProvider2;
        this.precisionDescriber = precisionDescriber2;
    }

    public Matcher<View> getConstraints() {
        return ViewMatchers.isDisplayingAtLeast(90);
    }

    public String getDescription() {
        return String.valueOf(this.swiper.toString().toLowerCase()).concat(" swipe");
    }

    public void perform(UiController uiController, View view) {
        float[] calculateCoordinates = this.startCoordinatesProvider.calculateCoordinates(view);
        float[] calculateCoordinates2 = this.endCoordinatesProvider.calculateCoordinates(view);
        float[] describePrecision = this.precisionDescriber.describePrecision();
        Swiper.Status status = Swiper.Status.FAILURE;
        int i = 0;
        while (i < 3 && status != Swiper.Status.SUCCESS) {
            try {
                status = this.swiper.sendSwipe(uiController, calculateCoordinates, calculateCoordinates2, describePrecision);
                int pressedStateDuration = ViewConfiguration.getPressedStateDuration();
                if (pressedStateDuration > 0) {
                    uiController.loopMainThreadForAtLeast((long) pressedStateDuration);
                }
                i++;
            } catch (RuntimeException e) {
                throw new PerformException.Builder().withActionDescription(getDescription()).withViewDescription(HumanReadables.describe(view)).withCause(e).build();
            }
        }
        if (status == Swiper.Status.FAILURE) {
            throw new PerformException.Builder().withActionDescription(getDescription()).withViewDescription(HumanReadables.describe(view)).withCause(new RuntimeException(String.format(Locale.ROOT, "Couldn't swipe from: %s,%s to: %s,%s precision: %s, %s . Swiper: %s start coordinate provider: %s precision describer: %s. Tried %s times", new Object[]{Float.valueOf(calculateCoordinates[0]), Float.valueOf(calculateCoordinates[1]), Float.valueOf(calculateCoordinates2[0]), Float.valueOf(calculateCoordinates2[1]), Float.valueOf(describePrecision[0]), Float.valueOf(describePrecision[1]), this.swiper, this.startCoordinatesProvider, this.precisionDescriber, 3}))).build();
        }
    }
}
