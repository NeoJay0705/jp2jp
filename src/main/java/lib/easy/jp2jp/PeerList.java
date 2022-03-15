package lib.easy.jp2jp;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PeerList {
    private List<URL> peers;

    public PeerList() {
        this.peers = new ArrayList<>();
    }

    public void addPeer(URL url) {
        peers.add(url);
    }

    public List<URL> getPeers() {
        return peers;
    }
}
