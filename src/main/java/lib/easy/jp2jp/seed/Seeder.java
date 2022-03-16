package lib.easy.jp2jp.seed;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

public class Seeder {
    public InputStream syncFromServer(Path path) {
        return null;
    }

    public void syncByTerminal(Path path, OutputStream outputStream) {
        // if newest
        // outputStream.write().flush()
        // else
        syncFromServer(path);
        // copy to outputsream
    }

    public void autoSync() {
        // from near seeds first
        // from server
    }
}
