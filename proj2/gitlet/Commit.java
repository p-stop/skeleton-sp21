package gitlet;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

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
        timestamp = "00:00:00 UTC, Thursday, 1 January 1970";
        tracked_files = null;
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
    public void change(String message,String parent_hash) {
        timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS'Z'").format(new Date());
        this.message = message;
        this.parent_hash = parent_hash;
    }
    public void putFile(String filename, String hash) {
        tracked_files.put(filename,hash);
    }
    public boolean containsFile(String filename) {
        return tracked_files.containsKey(filename);
    }

    public static void  main(String[] args) {
        Date date = new Date();
        String timestamp;
        // ���� SimpleDateFormat ����ָ�����ڸ�ʽ
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z");
//        // ����ʱ��
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT-08:00"));
//        // ��ʽ������
//        String formattedDate = sdf.format(date);
        timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS'Z'").format(new Date());
        System.out.println(timestamp);


    }
}
