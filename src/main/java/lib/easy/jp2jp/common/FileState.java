package lib.easy.jp2jp.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

public class FileState {
    private File file;
    private Function<InputStream, String> checksumFunc;
    private String currentChecksum;

    public FileState(File file) throws IOException {
        this(file, (is) -> {
            CheckedInputStream checkedInputStream = new CheckedInputStream(is, new CRC32());
            byte[] buffer = new byte[1024];
            try {
                while (checkedInputStream.read(buffer, 0, buffer.length) >= 0) {}
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Long.toString(checkedInputStream.getChecksum().getValue());
        });
    }

    public FileState(File file, Function<InputStream, String> checksumFunc) throws IOException {
        this.checksumFunc = checksumFunc;
        this.file = file;
        updateVersion();
    }

    public void updateVersion() throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            this.currentChecksum = this.checksumFunc.apply(fis);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    public String getChecksum() {
        return this.currentChecksum;
    }

    public boolean compare(String otherChecksum) {
        return this.currentChecksum.equals(otherChecksum);
    }
}
