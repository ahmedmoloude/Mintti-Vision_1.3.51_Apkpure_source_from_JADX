package androidx.test.internal.runner;

import java.util.ArrayList;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.manipulation.Sortable;
import org.junit.runner.manipulation.Sorter;
import org.junit.runner.notification.RunNotifier;

class NonExecutingRunner extends Runner implements Sortable, Filterable {
    private final Runner runner;

    NonExecutingRunner(Runner runner2) {
        this.runner = runner2;
    }

    public Description getDescription() {
        return this.runner.getDescription();
    }

    public void run(RunNotifier runNotifier) {
        generateListOfTests(runNotifier, getDescription());
    }

    public void filter(Filter filter) throws NoTestsRemainException {
        filter.apply(this.runner);
    }

    public void sort(Sorter sorter) {
        sorter.apply(this.runner);
    }

    private void generateListOfTests(RunNotifier runNotifier, Description description) {
        ArrayList<Description> children = description.getChildren();
        if (children.isEmpty()) {
            runNotifier.fireTestStarted(description);
            runNotifier.fireTestFinished(description);
            return;
        }
        for (Description generateListOfTests : children) {
            generateListOfTests(runNotifier, generateListOfTests);
        }
    }
}
