package p000a.p001a.p002a.p003a.p004d;

/* renamed from: a.a.a.a.d.f */
public class C0017f<T> {

    /* renamed from: a */
    public int f86a = 256;

    /* renamed from: b */
    public int f87b = 0;

    /* renamed from: c */
    public int[] f88c;

    /* renamed from: d */
    public double[] f89d;

    /* renamed from: e */
    public int[] f90e = new int[15];

    /* renamed from: f */
    public int f91f = 0;

    /* renamed from: g */
    public boolean f92g = false;

    public C0017f(int i) {
        this.f86a = i;
        this.f88c = new int[i];
        this.f89d = new double[i];
    }

    /* renamed from: a */
    public void mo50a() {
        this.f87b = 0;
        this.f91f = 0;
        int i = this.f86a;
        this.f88c = new int[i];
        this.f89d = new double[i];
        this.f90e = new int[15];
    }

    /* renamed from: a */
    public int[] mo51a(int[] iArr) {
        if (this.f87b == 0 && this.f91f != 0) {
            int i = 0;
            while (i < this.f91f) {
                int[] iArr2 = this.f88c;
                int i2 = this.f87b;
                iArr2[i2] = this.f90e[i];
                i++;
                this.f87b = i2 + 1;
            }
            this.f91f = 0;
        }
        int i3 = 0;
        while (true) {
            if (i3 >= iArr.length) {
                break;
            }
            int i4 = this.f87b;
            if (i4 == this.f86a) {
                this.f91f = iArr.length - i3;
                this.f87b = 0;
                for (int i5 = 0; i5 < this.f91f; i5++) {
                    this.f90e[i5] = iArr[i5 + i3];
                }
                this.f92g = true;
            } else {
                this.f88c[i4] = iArr[i3];
                i3++;
                this.f87b = i4 + 1;
            }
        }
        if (!this.f92g) {
            return null;
        }
        this.f92g = false;
        return this.f88c;
    }

    /* renamed from: b */
    public double[] mo52b(int[] iArr) {
        if (this.f87b == 0 && this.f91f != 0) {
            int i = 0;
            while (i < this.f91f) {
                double[] dArr = this.f89d;
                int i2 = this.f87b;
                dArr[i2] = (double) this.f90e[i];
                i++;
                this.f87b = i2 + 1;
            }
            this.f91f = 0;
        }
        int i3 = 0;
        while (true) {
            if (i3 >= iArr.length) {
                break;
            }
            int i4 = this.f87b;
            if (i4 == this.f86a) {
                this.f91f = iArr.length - i3;
                this.f87b = 0;
                for (int i5 = 0; i5 < this.f91f; i5++) {
                    this.f90e[i5] = iArr[i5 + i3];
                }
                this.f92g = true;
            } else {
                this.f89d[i4] = (double) iArr[i3];
                i3++;
                this.f87b = i4 + 1;
            }
        }
        if (!this.f92g) {
            return null;
        }
        this.f92g = false;
        return this.f89d;
    }
}
