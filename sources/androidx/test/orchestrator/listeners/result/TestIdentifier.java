package androidx.test.orchestrator.listeners.result;

public class TestIdentifier {
    private final String className;
    private final String testName;

    public TestIdentifier(String str, String str2) {
        if (str == null || str2 == null) {
            throw new IllegalArgumentException("className and testName must be non-null");
        }
        this.className = str;
        this.testName = str2;
    }

    public String getClassName() {
        return this.className;
    }

    public String getTestName() {
        return this.testName;
    }

    public int hashCode() {
        String str = this.className;
        int i = 0;
        int hashCode = ((str == null ? 0 : str.hashCode()) + 31) * 31;
        String str2 = this.testName;
        if (str2 != null) {
            i = str2.hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TestIdentifier testIdentifier = (TestIdentifier) obj;
        String str = this.className;
        if (str == null) {
            if (testIdentifier.className != null) {
                return false;
            }
        } else if (!str.equals(testIdentifier.className)) {
            return false;
        }
        String str2 = this.testName;
        if (str2 == null) {
            if (testIdentifier.testName != null) {
                return false;
            }
        } else if (!str2.equals(testIdentifier.testName)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return String.format("%s#%s", new Object[]{getClassName(), getTestName()});
    }
}
