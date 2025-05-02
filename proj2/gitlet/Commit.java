package gitlet;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.TreeSet;
// TODO: any imports you need here

import java.util.Date; // TODO: You'll likely use this in this class

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit {
    private String message;
    private String timestamp;
    private String hash;
    private TreeSet<Fileder> dictionary;

    public Commit(){
        dictionary = new TreeSet<>();
    }
    public void init(){
        String gitletFolderPath = ".gitlet";
        Path path = Paths.get(gitletFolderPath);
        if (!(Files.exists(path))) {
            throw
        }
        message = "initial commit";
        timestamp = "00:00:00 UTC, Thursday, 1 January 1970";
        hash = "00";
        dictionary = null;
    }
    public static void  main(String[] args) {
        Date date = new Date();
        String timestamp;
        // 创建 SimpleDateFormat 对象，指定日期格式
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z");
//        // 设置时区
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT-08:00"));
//        // 格式化日期
//        String formattedDate = sdf.format(date);
        timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS'Z'").format(new Date());
        System.out.println(timestamp);


    }
}
