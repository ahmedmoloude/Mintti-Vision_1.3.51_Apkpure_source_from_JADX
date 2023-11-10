package androidx.test.services.storage.file;

import android.net.Uri;
import androidx.test.services.storage.TestStorageConstants;
import com.itextpdf.text.Annotation;
import com.itextpdf.text.html.HtmlTags;
import com.itextpdf.text.xml.xmp.DublinCoreProperties;
import java.util.Objects;

public final class HostedFile {

    public enum HostedFileColumn {
        NAME("name", String.class, 3, 0),
        TYPE(DublinCoreProperties.TYPE, String.class, 3, 1),
        SIZE(HtmlTags.SIZE, Long.class, 1, 2),
        DATA("_data", Byte[].class, 4, 3),
        DISPLAY_NAME("_display_name", String.class, 3, 4),
        SIZE_2("_size", Long.class, 2, 5);
        
        private final int androidType;
        private final String columnName;
        private final Class<?> columnType;
        private final int position;

        private HostedFileColumn(String str, Class<?> cls, int i, int i2) {
            this.columnName = (String) HostedFile.checkNotNull(str);
            this.columnType = (Class) HostedFile.checkNotNull(cls);
            this.androidType = i;
            this.position = i2;
        }

        public String getColumnName() {
            return this.columnName;
        }

        public Class<?> getColumnType() {
            return this.columnType;
        }

        public int getAndroidType() {
            return this.androidType;
        }

        public int getPosition() {
            return this.position;
        }

        public static String[] getColumnNames() {
            HostedFileColumn[] values = values();
            int length = values.length;
            String[] strArr = new String[length];
            for (int i = 0; i < length; i++) {
                strArr[i] = values[i].getColumnName();
            }
            return strArr;
        }
    }

    public enum FileType {
        FILE("f"),
        DIRECTORY("d");
        
        private String type;

        private FileType(String str) {
            this.type = (String) HostedFile.checkNotNull(str);
        }

        public String getTypeCode() {
            return this.type;
        }

        public static FileType fromTypeCode(String str) {
            for (FileType fileType : values()) {
                if (fileType.getTypeCode().equals(str)) {
                    return fileType;
                }
            }
            String valueOf = String.valueOf(str);
            throw new IllegalArgumentException(valueOf.length() != 0 ? "unknown type: ".concat(valueOf) : new String("unknown type: "));
        }
    }

    public enum FileHost {
        TEST_FILE(TestStorageConstants.TEST_RUNFILES_PROVIDER_AUTHORITY, false),
        EXPORT_PROPERTIES(TestStorageConstants.OUTPUT_PROPERTIES_PROVIDER_AUTHORITY, true),
        OUTPUT(TestStorageConstants.TEST_OUTPUT_PROVIDER_AUTHORITY, true),
        INTERNAL_USE_ONLY(TestStorageConstants.INTERNAL_USE_PROVIDER_AUTHORITY, true);
        
        private final String authority;
        private final boolean writeable;

        private FileHost(String str, boolean z) {
            this.authority = (String) HostedFile.checkNotNull(str);
            this.writeable = z;
        }

        public String getAuthority() {
            return this.authority;
        }

        public boolean isWritable() {
            return this.writeable;
        }
    }

    public static Uri buildUri(FileHost fileHost, String str) {
        return new Uri.Builder().scheme(Annotation.CONTENT).authority(fileHost.getAuthority()).path(str).build();
    }

    /* access modifiers changed from: private */
    public static <T> T checkNotNull(T t) {
        Objects.requireNonNull(t);
        return t;
    }

    private HostedFile() {
    }
}
