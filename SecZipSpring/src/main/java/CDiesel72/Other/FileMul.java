package CDiesel72.Other;

/**
 * Created by Diesel on 07.04.2019.
 */
public class FileMul {
    private String name;
    private byte[] bytes;

    public FileMul(String name, byte[] bytes) {
        this.name = name;
        this.bytes = bytes;
    }

    public String getName() {
        return name;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
