package com.itextpdf.text.p018io;

import com.itextpdf.text.error_messages.MessageLocalization;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.channels.FileChannel;

/* renamed from: com.itextpdf.text.io.RandomAccessSourceFactory */
public final class RandomAccessSourceFactory {
    private boolean exclusivelyLockFile = false;
    private boolean forceRead = false;
    private boolean usePlainRandomAccess = false;

    public RandomAccessSourceFactory setForceRead(boolean z) {
        this.forceRead = z;
        return this;
    }

    public RandomAccessSourceFactory setUsePlainRandomAccess(boolean z) {
        this.usePlainRandomAccess = z;
        return this;
    }

    public RandomAccessSourceFactory setExclusivelyLockFile(boolean z) {
        this.exclusivelyLockFile = z;
        return this;
    }

    public RandomAccessSource createSource(byte[] bArr) {
        return new ArrayRandomAccessSource(bArr);
    }

    public RandomAccessSource createSource(RandomAccessFile randomAccessFile) throws IOException {
        return new RAFRandomAccessSource(randomAccessFile);
    }

    public RandomAccessSource createSource(URL url) throws IOException {
        InputStream openStream = url.openStream();
        try {
            return createSource(openStream);
        } finally {
            try {
                openStream.close();
            } catch (IOException unused) {
            }
        }
    }

    public RandomAccessSource createSource(InputStream inputStream) throws IOException {
        try {
            return createSource(StreamUtil.inputStreamToArray(inputStream));
        } finally {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    public RandomAccessSource createBestSource(String str) throws IOException {
        File file = new File(str);
        if (!file.canRead()) {
            if (str.startsWith("file:/") || str.startsWith("http://") || str.startsWith("https://") || str.startsWith("jar:") || str.startsWith("wsjar:") || str.startsWith("wsjar:") || str.startsWith("vfszip:")) {
                return createSource(new URL(str));
            }
            return createByReadingToMemory(str);
        } else if (this.forceRead) {
            return createByReadingToMemory((InputStream) new FileInputStream(str));
        } else {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, this.exclusivelyLockFile ? "rw" : "r");
            if (this.exclusivelyLockFile) {
                randomAccessFile.getChannel().lock();
            }
            try {
                return createBestSource(randomAccessFile);
            } catch (IOException e) {
                try {
                    randomAccessFile.close();
                } catch (IOException unused) {
                }
                throw e;
            } catch (RuntimeException e2) {
                try {
                    randomAccessFile.close();
                } catch (IOException unused2) {
                }
                throw e2;
            }
        }
    }

    public RandomAccessSource createBestSource(RandomAccessFile randomAccessFile) throws IOException {
        if (this.usePlainRandomAccess) {
            return new RAFRandomAccessSource(randomAccessFile);
        }
        if (randomAccessFile.length() <= 0) {
            return new RAFRandomAccessSource(randomAccessFile);
        }
        try {
            return createBestSource(randomAccessFile.getChannel());
        } catch (MapFailedException unused) {
            return new RAFRandomAccessSource(randomAccessFile);
        }
    }

    public RandomAccessSource createBestSource(FileChannel fileChannel) throws IOException {
        if (fileChannel.size() <= 67108864) {
            return new GetBufferedRandomAccessSource(new FileChannelRandomAccessSource(fileChannel));
        }
        return new GetBufferedRandomAccessSource(new PagedChannelRandomAccessSource(fileChannel));
    }

    public RandomAccessSource createRanged(RandomAccessSource randomAccessSource, long[] jArr) throws IOException {
        RandomAccessSource[] randomAccessSourceArr = new RandomAccessSource[(jArr.length / 2)];
        for (int i = 0; i < jArr.length; i += 2) {
            randomAccessSourceArr[i / 2] = new WindowRandomAccessSource(randomAccessSource, jArr[i], jArr[i + 1]);
        }
        return new GroupedRandomAccessSource(randomAccessSourceArr);
    }

    private RandomAccessSource createByReadingToMemory(String str) throws IOException {
        InputStream resourceStream = StreamUtil.getResourceStream(str);
        if (resourceStream != null) {
            return createByReadingToMemory(resourceStream);
        }
        throw new IOException(MessageLocalization.getComposedMessage("1.not.found.as.file.or.resource", str));
    }

    private RandomAccessSource createByReadingToMemory(InputStream inputStream) throws IOException {
        try {
            return new ArrayRandomAccessSource(StreamUtil.inputStreamToArray(inputStream));
        } finally {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }
}
