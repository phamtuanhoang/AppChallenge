package camonia.appchallenge;

/**
 * Created by tuanhoang.pham on 5/10/16.
 */

public class Beacon {
    private String UUID;

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    private int major;
    private int minor;

    public Beacon(){
    }

}
