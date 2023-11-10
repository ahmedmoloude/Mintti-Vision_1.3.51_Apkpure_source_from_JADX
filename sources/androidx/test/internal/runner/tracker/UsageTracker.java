package androidx.test.internal.runner.tracker;

public interface UsageTracker {

    public static class NoOpUsageTracker implements UsageTracker {
        public void sendUsages() {
        }

        public void trackUsage(String str, String str2) {
        }
    }

    void sendUsages();

    void trackUsage(String str, String str2);
}
