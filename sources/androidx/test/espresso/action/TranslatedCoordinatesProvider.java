package androidx.test.espresso.action;

import android.view.View;

final class TranslatedCoordinatesProvider implements CoordinatesProvider {
    final CoordinatesProvider coordinatesProvider;

    /* renamed from: dx */
    final float f193dx;

    /* renamed from: dy */
    final float f194dy;

    public TranslatedCoordinatesProvider(CoordinatesProvider coordinatesProvider2, float f, float f2) {
        this.coordinatesProvider = coordinatesProvider2;
        this.f193dx = f;
        this.f194dy = f2;
    }

    public float[] calculateCoordinates(View view) {
        float[] calculateCoordinates = this.coordinatesProvider.calculateCoordinates(view);
        calculateCoordinates[0] = calculateCoordinates[0] + (this.f193dx * ((float) view.getWidth()));
        calculateCoordinates[1] = calculateCoordinates[1] + (this.f194dy * ((float) view.getHeight()));
        return calculateCoordinates;
    }
}
