package gitlet;

import java.io.Serializable;
import java.util.TreeMap;

public class HEAD implements Serializable {
    public String cur_commit;
    public String bname;
    public TreeMap<String, String> heads;

    public HEAD() {
        cur_commit = "";
        bname = "master";
        heads = new TreeMap<>();
    }

    public void print() {
        System.out.println("*" + bname);
        for (String key : heads.keySet()) {
            if (key.equals(bname)) {
                continue;
            }
            System.out.println(key);
        }
        System.out.printf("\n");
    }
}