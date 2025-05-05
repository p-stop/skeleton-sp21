package gitlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.TreeMap;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File STAGING_DIR = join(GITLET_DIR, "staging_area");
    public static final File REPO_DIR = join(GITLET_DIR, "repo");
    public static final File COMMITS_DIR = join(GITLET_DIR, "commits_log");

    public static String current_commit;

    public static void init(){
        if (Files.exists(GITLET_DIR.toPath())) {
            throw new GitletException("A Gitlet version-control system already exists in the current directory.");
        }
        GITLET_DIR.mkdirs();
        STAGING_DIR.mkdir();
        REPO_DIR.mkdir();
        COMMITS_DIR.mkdir();
        Commit init_commit = new Commit();
        init_commit.init();
        String name = Commit.encode_commit(init_commit);
        File init = join(COMMITS_DIR, name+".txt");
        Utils.writeObject(init,init_commit);
        current_commit = name;
    }

    public static void add(String filename){
        File added = join(CWD, filename);
        if (!(added.exists())) {
            throw new GitletException("File does not exist.");
        }
        //read the current commit
        File cur_com_point = join(COMMITS_DIR, current_commit+".txt");
        Commit cur_com = Utils.readObject(cur_com_point, Commit.class);
        //judge if stage the file
        String added_id = Utils.sha1(added);
        if (cur_com.containsFile(filename)) {
            if(added_id == cur_com.getID(filename)){
                File is_staged = join(STAGING_DIR, filename);
                if(is_staged.exists()){
                    Utils.restrictedDelete(is_staged);
                }
                return;
            }
        }
        File dest = join(STAGING_DIR, filename);
        Utils.writeContents(dest, added);
    }

    public static void commit(String message) {
        if(STAGING_DIR.list() == null){
            throw new GitletException("No changes added to the commit.");
        }
        File cur_com_point = join(COMMITS_DIR, current_commit+".txt");
        Commit cur_com = Utils.readObject(cur_com_point, Commit.class);
        File[] files = STAGING_DIR.listFiles();
        for (File file : files) {
            String id = Utils.sha1(file);
            cur_com.putFile(file.getName(), id);
            File source = join(STAGING_DIR, file.getName());
            File dest = join(REPO_DIR, id+".txt");
            Utils.writeContents(dest, Utils.readContents(source));
        }
        String com_id = Commit.encode_commit(cur_com);
        File com_point = join(COMMITS_DIR, com_id+".txt");
        Utils.writeObject(com_point, cur_com);
        cur_com.change(message, current_commit);
        current_commit = com_id;
    }
    public static void checkout_1_2(String commit_id,String filename) {
        File target_com = join(COMMITS_DIR, commit_id+".txt");
        if(target_com.exists()){
            Commit cur_com = Utils.readObject(target_com, Commit.class);
            if(cur_com.containsFile(filename)){
                File old_file = join(REPO_DIR, cur_com.getID(filename));
                Utils.writeContents(join(CWD, filename), Utils.readContentsAsString(old_file));
            }
            else {
                throw new GitletException("File does not exist in that commit.");
            }
        }
        else {
            throw new GitletException("No commit with that id exists.");
        }
    }
    public static void log() {
        File target_com = join(COMMITS_DIR, current_commit+".txt");
        String cur_id = current_commit;
        Commit cur_com = Utils.readObject(target_com, Commit.class);
        while(cur_com.getMessage()!="initial commit"){
            System.out.printf("===\ncommit %s\nDate: %s\n%s\n",cur_id,cur_com.gettimestamp(),cur_com.getMessage());
            target_com = join(COMMITS_DIR, cur_com.getParent_hash()+".txt");
            cur_id = cur_com.getParent_hash();
            cur_com = Utils.readObject(target_com, Commit.class);
        }
        System.out.printf("===\ncommit %s\nDate: %s\n%s\n",cur_id,cur_com.gettimestamp(),cur_com.getMessage());
    }

}
