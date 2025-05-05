package gitlet;

import java.io.File;

public class Fileder implements Comparable {
    private String name;
    private String hash;
    private String path;

    public Fileder(String name, String hash) {
        this.name = name;
        this.hash = hash;
    }

    @Override
    public int compareTo(Object o) {
        return name.compareTo(((Fileder) o).name);
    }
}


