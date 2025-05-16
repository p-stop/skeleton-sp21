package gitlet;
import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class Commit implements Serializable {
    private String message;
    private String timestamp;
    private HashMap<String,String> tracked_files;
    private String parent_hash;

    public Commit() {
        tracked_files = new HashMap<>();
    }

    public void init() {
        message = "initial commit";
        timestamp = "Thu Jan 1 00:00:00 1970 +0000";
        tracked_files = new HashMap<>();
        parent_hash = null;
    }

    public String getMessage() {
        return message;
    }
    public String gettimestamp() {
        return timestamp;
    }
    public String getParent_hash() {
        return parent_hash;
    }
    public String getID(String filename) {
        return tracked_files.get(filename);
    }
    public String[] getnames() {
        return tracked_files.keySet().toArray(new String[0]);
    }

    public void del(String filename) {
        tracked_files.remove(filename);
    }
    public void change(String message,String parent_hash) {
        timestamp = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z",Locale.US).format(new Date());
        this.message = message;
        this.parent_hash = parent_hash;
    }
    public void putFile(String filename, String hash) {
        tracked_files.put(filename,hash);
    }
    public boolean containsFile(String filename) {
        return tracked_files.containsKey(filename);
    }
    public void delFile(String filename) {
        tracked_files.remove(filename);
    }
    public void restore_all() {
        for (String filename : tracked_files.keySet()) {
            File sour = Utils.join(Repository.REPO_DIR,tracked_files.get(filename)+".txt");
            File dest = Utils.join(Repository.CWD,filename);
            Utils.writeContents(dest,Utils.readContents(sour));
        }
    }
    public static String encode_commit(Commit commit) {
        if(commit.tracked_files.isEmpty()){
            return Utils.sha1(commit.message,commit.timestamp);
        }
        return Utils.sha1(commit.message,commit.timestamp,commit.parent_hash,Utils.serialize(commit.tracked_files));
    }

    //log helper function
    public void print_log(String hashcode){
        System.out.printf("===\ncommit %s\nDate: %s\n%s\n\n",hashcode,this.gettimestamp(),this.getMessage());
    }
}