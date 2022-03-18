package lib.easy.jp2jp.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import lib.easy.jp2jp.common.FileStates;
import lib.easy.jp2jp.common.Exception.ConnectionTimeout;
import lib.easy.jp2jp.common.Exception.ReadTimeout;

public class Terminal {
    private FileStates newestStates;
    private File dir;
    private Deque<File> unAskedFiles;
    private Deque<File> pendingFiles;
    private List<URI> peers;
    private URI self;

    public Terminal(FileStates newestStates, File dir) throws URISyntaxException {
        this.newestStates = newestStates;
        this.dir = dir;
        this.unAskedFiles = new ArrayDeque<>();
        this.newestStates.getFiles().forEach(path -> {
            unAskedFiles.addLast(new File(path));
        });
        this.pendingFiles = new ArrayDeque<>();
        this.peers = broadcast();
        this.self = new URI("");
    }

    public URI selectSeed(List<URI> ownedPeers, String checksum) {
        if (ownedPeers.size() == 0)
            return null;

        Checksum crc32 = new CRC32();
        byte[] bChecksum = checksum.getBytes();
        crc32.update(bChecksum, 0, bChecksum.length);
        return ownedPeers.get((int)crc32.getValue() % ownedPeers.size());
    }

    // http
    public boolean downloadFromSeed(URI targetSeed, URI uri) throws ConnectionTimeout, ReadTimeout, FileNotFoundException {

        InputStream fromServer;
        OutputStream out = new FileOutputStream(new File(this.dir, this.getQueryValue(uri, "file")));
        out.
        return false;
    }

    private String getQueryValue(URI uri, String key) {
        return Stream.of(uri.getQuery().split("&")).map(p -> p.split("=")).map(kv -> {
            try {
                return new String[] { URLDecoder.decode(kv[0], "UTF-8"), URLDecoder.decode(kv[1], "UTF-8") };
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        })
        .filter(kv -> kv[0].equals(key))
        .map(kv -> kv[1])
        .findFirst().orElse(null);
    }

    // http
    public List<URI> broadcast() {
        return null;
    }

    public void syncFromSeeds() {
        while (this.unAskedFiles.size() != 0) {
            updateFileFlow(this.unAskedFiles);
        }

        while (this.pendingFiles.size() != 0) {
            updateFileFlow(this.pendingFiles);
        }
    }

    private void updateFileFlow(Deque<File> fromQueue) {
        File file = fromQueue.pop();
        String newestChecksum = this.newestStates.getChecksum(file);
        URI targetSeed;
        if (this.peers.size() == 0) {
            targetSeed = this.self;
        } else {
            targetSeed = selectSeed(this.peers, newestChecksum);
        }

        try {
            downloadFromSeed(targetSeed, file);
        } catch (ConnectionTimeout e) {
            this.peers = broadcast();
            Collections.shuffle(this.peers, new Random());
            pendingFiles.addLast(file);
        } catch (ReadTimeout e) {
            pendingFiles.addLast(file);
        }
    }
}
