package lib.easy.jp2jp;

import java.nio.file.Path;

public class Terminal {
    private FileStates fileStates;
    private PeerList peers;

    public Terminal(PeerList peers) {
        this.fileStates = new FileStates();
        this.peers = peers;
    }

    public boolean isEqual(String filename, String otherChecksum) {
        return fileStates.compareVersion(filename, otherChecksum);
    }

    public void fetch(Path path) {

    }

    public void reFetch(Path path) {

    }

    public void syncFromSeeders() {
        // broadcast
        // rnd to choose one of it
        // if exist fetch from there
        // else hash to one of it
        // go to next file
        
        // refetch the pending files by hash
    }
}
