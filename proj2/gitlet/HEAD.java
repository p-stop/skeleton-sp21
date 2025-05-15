package gitlet;

import java.io.Serializable;
import java.util.HashMap;

public class HEAD implements Serializable {
    public  String cur_commit;
    //about branch
    public  String bname;
    public HashMap<String,String> heads;
    public HEAD() {
        cur_commit = "";
        bname = "init";
        heads = new HashMap<>();
    }
}
