package lib.easy.jp2jp;

import java.util.Date;

public class FileState {
    private String checksum;
    private Date time;

    public FileState(String checksum, Date time) {
        updateVersion(checksum, time);
    }

    public void updateVersion(String checksum, Date time) {
        this.checksum = checksum;
        this.time = time;
    }

    public String getChecksum() {
        return this.checksum;
    }

    public Date time() {
        return this.time;
    }

    public boolean compareVersion(String otherChecksum) {
        return this.checksum.equals(otherChecksum);
    }
}
