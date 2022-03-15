package lib.easy.jp2jp;

import java.util.HashMap;
import java.util.Map;

public class FileStates {
    private Map<String, FileState> fileStates;

    public FileStates() {
        this.fileStates = new HashMap<>();
    }

    public void updateState(String filename, FileState fileState) {
        this.fileStates.put(filename, fileState);
    }

    public boolean compareVersion(String filename, String otherChecksum) {
        return fileStates.containsKey(filename) ? fileStates.get(filename).compareVersion(otherChecksum) : false;

    }
}
