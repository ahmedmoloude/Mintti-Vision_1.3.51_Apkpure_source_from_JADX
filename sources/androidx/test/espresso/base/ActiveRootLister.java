package androidx.test.espresso.base;

import androidx.test.espresso.Root;
import java.util.List;

public interface ActiveRootLister {
    List<Root> listActiveRoots();
}
