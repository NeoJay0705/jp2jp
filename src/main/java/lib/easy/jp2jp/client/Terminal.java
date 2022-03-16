package lib.easy.jp2jp.client;

import java.io.File;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import lib.easy.jp2jp.common.FileStates;

public class Terminal {
    private FileStates newestStates;
    private Deque<File> unAskedFiles;
    private Deque<File> pendingFiles;
    private List<URL> peers;

    public Terminal(List<URL> peers, FileStates newestStates) {
        this.newestStates = newestStates;
        this.unAskedFiles = new ArrayDeque<>();
        this.newestStates.getFiles().forEach(path -> {
            unAskedFiles.addLast(new File(path));
        });
        this.pendingFiles = new ArrayDeque<>();
        this.peers = peers;
    }

    public URL selectSeed(List<URL> ownedPeers, String checksum) {
        if (ownedPeers.size() == 0)
            return null;

        Checksum crc32 = new CRC32();
        byte[] bChecksum = checksum.getBytes();
        crc32.update(bChecksum, 0, bChecksum.length);
        return ownedPeers.get((int)crc32.getValue() % ownedPeers.size());
    }

    // http
    public boolean fetch(URL targetSeed, File file) {
        return false;
    }

    // http
    public List<URL> broadcast() {
        return null;
    }

    public void syncFromSeeders() {
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
        List<URL> ownedPeers = broadcast();
        URL targetSeed;
        if (ownedPeers.size() == 0) {
            targetSeed = selectSeed(this.peers, newestChecksum);
        } else {
            targetSeed = selectSeed(ownedPeers, newestChecksum);
        }

        if(!fetch(targetSeed, file)) {
            pendingFiles.addLast(file);
        }
    }
}
