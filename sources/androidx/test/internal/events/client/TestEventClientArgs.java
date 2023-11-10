package androidx.test.internal.events.client;

import android.util.Log;

public final class TestEventClientArgs {
    public final ConnectionFactory connectionFactory;
    public final boolean isOrchestrated;
    public final boolean isPrimaryInstrProcess;
    public final boolean isTestDiscoveryRequested;
    public final boolean isTestRunEventsRequested;
    public final int orchestratorVersion;
    public final String testDiscoveryService;
    public final boolean testPlatformMigration;
    public final String testRunEventService;

    public interface ConnectionFactory {
        TestEventServiceConnection create(TestEventClientConnectListener testEventClientConnectListener);
    }

    private TestEventClientArgs(boolean z, int i, Builder builder) {
        this.isOrchestrated = z;
        this.isPrimaryInstrProcess = builder.isPrimaryInstProcess;
        this.isTestDiscoveryRequested = builder.testDiscoveryRequested;
        this.isTestRunEventsRequested = builder.testRunEventsRequested;
        this.testDiscoveryService = builder.testDiscoveryService;
        this.testRunEventService = builder.testRunEventService;
        this.connectionFactory = builder.connectionFactory;
        this.orchestratorVersion = i;
        this.testPlatformMigration = builder.testPlatformMigration;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private static final String TAG = "TestEventClient";
        /* access modifiers changed from: private */
        public ConnectionFactory connectionFactory;
        boolean isPrimaryInstProcess = true;
        private String orchestratorService;
        boolean testDiscoveryRequested = false;
        /* access modifiers changed from: private */
        public String testDiscoveryService;
        boolean testPlatformMigration = false;
        /* access modifiers changed from: private */
        public String testRunEventService;
        boolean testRunEventsRequested = false;

        public Builder setPrimaryInstProcess(boolean z) {
            this.isPrimaryInstProcess = z;
            return this;
        }

        public Builder setOrchestratorService(String str) {
            this.orchestratorService = str;
            return this;
        }

        public Builder setTestDiscoveryRequested(boolean z) {
            this.testDiscoveryRequested = z;
            return this;
        }

        public Builder setTestRunEventsRequested(boolean z) {
            this.testRunEventsRequested = z;
            return this;
        }

        public Builder setTestPlatformMigration(boolean z) {
            this.testPlatformMigration = z;
            return this;
        }

        public Builder setTestDiscoveryService(String str) {
            this.testDiscoveryService = str;
            return this;
        }

        public Builder setTestRunEventService(String str) {
            this.testRunEventService = str;
            return this;
        }

        public Builder setConnectionFactory(ConnectionFactory connectionFactory2) {
            this.connectionFactory = connectionFactory2;
            return this;
        }

        public TestEventClientArgs build() {
            String str = this.testDiscoveryService;
            int i = 2;
            boolean z = true;
            if (str == null || str.isEmpty()) {
                String str2 = this.testRunEventService;
                if (str2 == null || str2.isEmpty()) {
                    String str3 = this.orchestratorService;
                    if (str3 == null) {
                        Log.v(TAG, "No service name argument was given (testDiscoveryService, testRunEventService or orchestratorService)");
                        this.testDiscoveryRequested = false;
                        this.testRunEventsRequested = false;
                    } else if (this.connectionFactory == null) {
                        StringBuilder sb = new StringBuilder(String.valueOf(str3).length() + 96);
                        sb.append("Orchestrator service [");
                        sb.append(str3);
                        sb.append("] argument given, but no connectionFactory was provided for the v1 service");
                        Log.w(TAG, sb.toString());
                    } else if (this.testDiscoveryRequested || this.testRunEventsRequested) {
                        i = 1;
                    } else {
                        StringBuilder sb2 = new StringBuilder(String.valueOf(str3).length() + 103);
                        sb2.append("Orchestrator service [");
                        sb2.append(str3);
                        sb2.append("] argument given, but neither test discovery nor run event services was requested");
                        Log.w(TAG, sb2.toString());
                    }
                    i = 0;
                } else {
                    this.testRunEventsRequested = true;
                    this.testDiscoveryRequested = false;
                }
            } else {
                this.testDiscoveryRequested = true;
                this.testRunEventsRequested = false;
            }
            if (this.testDiscoveryRequested && this.testRunEventsRequested) {
                Log.w(TAG, "Can't use both the test discovery and run event services simultaneously");
                this.testRunEventsRequested = false;
            }
            if (i > 0) {
                StringBuilder sb3 = new StringBuilder(39);
                sb3.append("Connecting to Orchestrator v");
                sb3.append(i);
                Log.v(TAG, sb3.toString());
            }
            if (i <= 0) {
                z = false;
            }
            return new TestEventClientArgs(z, i, this);
        }
    }
}
