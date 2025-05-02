package gitlet;

import java.io.File;

public class Fileder implements Comparable {
    private String name;
    private String hash;
    private File file;
    private Fileder is_file;

    public Fileder(String name, File file, Fileder is_file) {
        this.name = name;
        this.file = file;
        this.is_file = is_file;
        hash = Utils.sha1(file);
    }

    @Override
    public int compareTo(Object o) {
        return name.compareTo(((Fileder) o).name);
    }
}


