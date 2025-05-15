package gitlet;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;

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
    public static final File REMOVAL_DIR = join(GITLET_DIR, "removal");
    public static final File REPO_DIR = join(GITLET_DIR, "repo");
    public static final File COMMITS_DIR = join(GITLET_DIR, "commits_log");

    public static final File HEADS = join(GITLET_DIR, "HEAD.txt");

    public static void init(){
        if (Files.exists(GITLET_DIR.toPath())) {
            throw new GitletException("A Gitlet version-control system already exists in the current directory.");
        }
        GITLET_DIR.mkdirs();
        STAGING_DIR.mkdir();
        REMOVAL_DIR.mkdir();
        REPO_DIR.mkdir();
        COMMITS_DIR.mkdir();
        Commit init_commit = new Commit();
        init_commit.init();
        String name = Commit.encode_commit(init_commit);
        File init = join(COMMITS_DIR, name+".txt");
        Utils.writeObject(init,init_commit);
        HEAD heads = new HEAD();
        heads.cur_commit = name;
        heads.heads.put(heads.bname,name);
        Utils.writeObject(HEADS,heads);
    }

    public static void add(String filename){
        if (!(GITLET_DIR.exists())) {
            throw new GitletException("Not in an initialized Gitlet directory.");
        }

        File added = join(CWD, filename);
        if (!(added.exists())) {
            throw new GitletException("File does not exist.");
        }
        //read the current commit
        HEAD heads = Utils.readObject(HEADS,HEAD.class);
        File cur_com_point = join(COMMITS_DIR, heads.cur_commit+".txt");
        Commit cur_com = Utils.readObject(cur_com_point, Commit.class);
        //judge if stage the file
        String added_id = Utils.sha1(Utils.readContents(added));
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
        Utils.writeContents(dest, Utils.readContents(added));
    }

    public static void commit(String message) {
        if (!(GITLET_DIR.exists())) {
            throw new GitletException("Not in an initialized Gitlet directory.");
        }
        //if no staged file?
        String[] fileList = STAGING_DIR.list();
        if (fileList == null || fileList.length == 0){
            throw new GitletException("No changes added to the commit.");
        }
        //call cur_commit
        HEAD heads = Utils.readObject(HEADS,HEAD.class);
        File cur_com_point = join(COMMITS_DIR, heads.cur_commit+".txt");
        Commit cur_com = Utils.readObject(cur_com_point, Commit.class);
        //track staged files one by one
        File[] files = STAGING_DIR.listFiles();
        for (File file : files) {
            //save files in repo
            String id = Utils.sha1(Utils.readContents(file));
            cur_com.putFile(file.getName(), id);
            File source = join(STAGING_DIR, file.getName());
            File dest = join(REPO_DIR, id+".txt");
            Utils.writeContents(dest, Utils.readContents(source));
            //clear staging area
            Utils.delete(source);
        }
        //process removal area
        File[] rm_files = REMOVAL_DIR.listFiles();
        for (File file : rm_files) {
            cur_com.del(file.getName());
        }
        //update commit messages
        cur_com.change(message, heads.cur_commit);
        //save commit
        String com_id = Commit.encode_commit(cur_com);
        File com_point = join(COMMITS_DIR, com_id+".txt");
        Utils.writeObject(com_point, cur_com);
        heads.cur_commit = com_id;
        Utils.writeObject(HEADS,heads);
    }
    public static void checkout_1_2(String commit_id,String filename) {
        if (!(GITLET_DIR.exists())) {
            throw new GitletException("Not in an initialized Gitlet directory.");
        }

        File target_com = join(COMMITS_DIR, commit_id+".txt");
        if(target_com.exists()){
            Commit cur_com = Utils.readObject(target_com, Commit.class);
            if(cur_com.containsFile(filename)){
                File old_file = join(REPO_DIR, cur_com.getID(filename)+".txt");
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
    public static void rm(String filename) {
        if (!(GITLET_DIR.exists())) {
            throw new GitletException("Not in an initialized Gitlet directory.");
        }

        //call the cur_commit
        HEAD heads = Utils.readObject(HEADS,HEAD.class);
        File target_com = join(COMMITS_DIR, heads.cur_commit+".txt");
        String cur_id = heads.cur_commit;
        Commit cur_com = Utils.readObject(target_com, Commit.class);
        //if file exist in stage area
        File is_staged = join(STAGING_DIR, filename);
        //if rm is useless
        if(!(is_staged.exists()) && !(cur_com.containsFile(filename))){
            throw new GitletException("No reason to remove the file.");
        }
        //do two things for rm()
        if(is_staged.exists()){
            is_staged.delete();
        }
        if(cur_com.containsFile(filename)){
            File workfile = join(CWD,filename);
            if(workfile.exists()){
                Utils.restrictedDelete(workfile);
            }
            File source = join(REPO_DIR, cur_com.getID(filename)+".txt");
            File dest = join(REMOVAL_DIR, filename);
            Utils.writeContents(dest, Utils.readContentsAsString(source));
        }
    }

    public static void find(String message) {
        if (!(GITLET_DIR.exists())) {
            throw new GitletException("Not in an initialized Gitlet directory.");
        }

        File[] commit_itr = COMMITS_DIR.listFiles();
        boolean found = false;
        for (File file : commit_itr) {
            Commit cur_com = Utils.readObject(file, Commit.class);
            if(cur_com.getMessage().equals(message)){
                System.out.println(file.getName().replace(".txt",""));
                found = true;
            }
        }
        if(!found){
            throw new GitletException("No commit with that id exists.");
        }
    }

    public static void log() {
        if (!(GITLET_DIR.exists())) {
            throw new GitletException("Found no commit with that message.");
        }

        HEAD heads = Utils.readObject(HEADS,HEAD.class);
        File target_com = join(COMMITS_DIR, heads.cur_commit+".txt");
        String cur_id = heads.cur_commit;
        Commit cur_com = Utils.readObject(target_com, Commit.class);
        while(cur_com.getParent_hash() != null){
            cur_com.print_log(cur_id);
            target_com = join(COMMITS_DIR, cur_com.getParent_hash()+".txt");
            cur_id = cur_com.getParent_hash();
            cur_com = Utils.readObject(target_com, Commit.class);
        }
        cur_com.print_log(cur_id);
    }

    public static void global_log() {
        if (!(GITLET_DIR.exists())) {
            throw new GitletException("Not in an initialized Gitlet directory.");
        }

        File[] commit_itr = COMMITS_DIR.listFiles();
        for (File file : commit_itr) {
            Commit cur_com = Utils.readObject(file, Commit.class);
            cur_com.print_log(file.getName().replace(".txt",""));
        }
    }

    public static void branch(String branch_name) {
        if (!(GITLET_DIR.exists())) {
            throw new GitletException("Not in an initialized Gitlet directory.");
        }
        HEAD head_class = Utils.readObject(HEADS,HEAD.class);
        if(head_class.heads.containsKey(branch_name)){
            throw new GitletException("A branch with that name already exists.");
        }
        head_class.heads.put(branch_name, head_class.cur_commit);
    }

    public static void rmb(String branch_name) {
        if (!(GITLET_DIR.exists())) {
            throw new GitletException("Not in an initialized Gitlet directory.");
        }
        HEAD head_class = Utils.readObject(HEADS,HEAD.class);
        if(head_class.heads.containsKey(branch_name)){
            if(head_class.heads.get(branch_name).equals(head_class.cur_commit)){
                throw new GitletException("Cannot remove the current branch.");
            }
            head_class.heads.remove(branch_name);
        }
        else {
            throw new GitletException("A branch with that name does not exist.");
        }
    }

    public static void reset(String com_id) {
        if (!(GITLET_DIR.exists())) {
            throw new GitletException("Not in an initialized Gitlet directory.");
        }
        File given_com = join(COMMITS_DIR, com_id+".txt");
        if(!(given_com.exists())) {
            throw new GitletException("No commit with that id exists.");
        }
        HEAD heads = Utils.readObject(HEADS,HEAD.class);
        File target_com = join(COMMITS_DIR, heads.cur_commit+".txt");
        Commit cur_com = Utils.readObject(target_com, Commit.class);
        Commit tar_com = Utils.readObject(given_com, Commit.class);
        //delete all files which shouldn't show and remain files need to show
        re_help_reset(CWD,cur_com,tar_com);
        //supplement files in tar_cur
        tar_com.restore_all();
        //clear staging area
        
    }




    public static void if_empty(){
        String[] fileList = STAGING_DIR.list();
        System.out.println(fileList.length);
        if (fileList == null || fileList.length == 0){
            throw new GitletException("No changes added to the commit.");
        }

    }
    private static void re_help_reset(File file, Commit cur, Commit tar) {
        if(file.isDirectory()){
            File[] files = file.listFiles();
            if(files.length != 0){
                for(File re_file:files){
                    re_help_reset(re_file, cur, tar);
                }
            }
        }
        else {
            if(cur.containsFile(file.getName())){
                if(!(tar.containsFile(file.getName()))){
                    Utils.delete(file);
                }
                else {
                    tar.delFile(file.getName());
                }
            }
            else {
                if(!(tar.containsFile(file.getName()))){
                    throw new GitletException("There is an untracked file in the way; delete it, or add and commit it first.");
                }
                else {
                    tar.delFile(file.getName());
                }
            }
        }
    }
    public static void main(String[] args) {
        Commit init_commit = new Commit();
        init_commit.init();

        System.out.println(init_commit.containsFile("wug.txt"));
    }

}
