package lib.easy.jp2jp.common;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FileStates {
    private Map<String, FileState> fileStates;

    public FileStates() {
        this.fileStates = new HashMap<>();
    }

    public String getChecksum(File file) {
        return this.fileStates.get(file.getAbsolutePath()).getChecksum();
    }

    public void updateState(File file) throws IOException {
        this.fileStates.putIfAbsent(file.getAbsolutePath(), new FileState(file));
        this.fileStates.get(file.getAbsolutePath()).updateVersion();
    }

    public boolean compare(File file, String otherChecksum) {
        return fileStates.containsKey(file.getAbsolutePath()) ? fileStates.get(file.getAbsolutePath()).compare(otherChecksum) : false;
    }

    public Set<String> getFiles() {
        return this.fileStates.keySet();
    }
}
