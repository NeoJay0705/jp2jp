package lib.easy.jp2jp.seed;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import lib.easy.jp2jp.client.Terminal;

public class Seed {

    private Terminal terminal;

    public Seed(Terminal terminal) {
        this.terminal = terminal;
    }

    public InputStream syncFromServer(URI uri) {
        return null;
    }

    public void syncByTerminal(URI uri, OutputStream outputStream) {
        // if newest
        // outputStream.write().flush()
        // else
        syncFromServer(uri);
        // copy to outputsream
    }

    public void autoSync() {
        // from near seeds first
        // from server
    }
}
