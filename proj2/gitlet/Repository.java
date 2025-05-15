package gitlet;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedList;

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
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            return;
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
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }

        File added = join(CWD, filename);
        if (!(added.exists())) {
            System.out.println("File does not exist.");
            return;
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
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }
        //if no staged file?
        String[] fileList = STAGING_DIR.list();
        if (fileList == null || fileList.length == 0){
            System.out.println("No changes added to the commit.");
            return;
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
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }

        File target_com = join(COMMITS_DIR, commit_id+".txt");
        if(target_com.exists()){
            Commit cur_com = Utils.readObject(target_com, Commit.class);
            if(cur_com.containsFile(filename)){
                File old_file = join(REPO_DIR, cur_com.getID(filename)+".txt");
                Utils.writeContents(join(CWD, filename), Utils.readContentsAsString(old_file));
            }
            else {
                System.out.println("File does not exist in that commit.");
                return;
            }
        }
        else {
            System.out.println("No commit with that id exists.");
            return;
        }
    }

    public static void checkout3(String bname) {
        if (!(GITLET_DIR.exists())) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }
        HEAD heads = Utils.readObject(HEADS,HEAD.class);
        if(heads.heads.containsKey(bname)){
            if(heads.heads.get(bname).equals(heads.cur_commit)){
                System.out.println("No need to checkout the current branch.");
                return;
            }
            else {
                reset(heads.heads.get(bname));
            }
        }
        else {
            System.out.println("No such branch exists.");
        }
    }

    public static void rm(String filename) {
        if (!(GITLET_DIR.exists())) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
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
            System.out.println("No reason to remove the file.");
            return;
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
            System.out.println("Not in an initialized Gitlet directory.");
            return;
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
            System.out.println("No commit with that id exists.");
            return;
        }
    }

    public static void log() {
        if (!(GITLET_DIR.exists())) {
            System.out.println("Found no commit with that message.");
            return;
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
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }

        File[] commit_itr = COMMITS_DIR.listFiles();
        for (File file : commit_itr) {
            Commit cur_com = Utils.readObject(file, Commit.class);
            cur_com.print_log(file.getName().replace(".txt",""));
        }
    }

    public static void status() {
        if (!(GITLET_DIR.exists())) {
            System.out.println("Found no commit with that message.");
            return;
        }
        //ready for modified_files
        LinkedList<File> modifications = new LinkedList<>();
        LinkedList<File> deletions = new LinkedList<>();
        HEAD heads = Utils.readObject(HEADS,HEAD.class);
        File current_com = join(COMMITS_DIR, heads.cur_commit+".txt");
        Commit cur_com = Utils.readObject(current_com,Commit.class);

        System.out.println("=== Branches ===");
        heads.print();
        System.out.println("=== Staged Files ===");
        File[] staged_files = STAGING_DIR.listFiles();
        for (File file : staged_files) {
            System.out.println(file.getName());
        }
        System.out.printf("\n");;
        System.out.println("=== Removed Files ===");
        File[] removed_files = REMOVAL_DIR.listFiles();
        for (File file : removed_files) {
            System.out.println(file.getName());
        }
        System.out.printf("\n");
        System.out.println("=== Modifications Not Staged For Commit ===");
        File[] CWD_files = CWD.listFiles();
        if(cwd_file.exists()){
            String cwd_id = Utils.sha1(Utils.readContents(cwd_file));
            if(cwd_id.equals(cur_com.getID(cwd_file.getName()))){

            }
        }

    }

    public static void branch(String branch_name) {
        if (!(GITLET_DIR.exists())) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }
        HEAD head_class = Utils.readObject(HEADS,HEAD.class);
        if(head_class.heads.containsKey(branch_name)){
            System.out.println("A branch with that name already exists.");
            return;
        }
        head_class.heads.put(branch_name, head_class.cur_commit);
        Utils.writeObject(HEADS,head_class);
    }

    public static void rmb(String branch_name) {
        if (!(GITLET_DIR.exists())) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }
        HEAD head_class = Utils.readObject(HEADS,HEAD.class);
        if(head_class.heads.containsKey(branch_name)){
            if(head_class.heads.get(branch_name).equals(head_class.cur_commit)){
                System.out.println("Cannot remove the current branch.");
                return;
            }
            head_class.heads.remove(branch_name);
        }
        else {
            System.out.println("A branch with that name does not exist.");
            return;
        }
        Utils.writeObject(HEADS,head_class);
    }

    public static void reset(String com_id) {
        if (!(GITLET_DIR.exists())) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }
        File given_com = join(COMMITS_DIR, com_id+".txt");
        if(!(given_com.exists())) {
            System.out.println("No commit with that id exists.");
            return;
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
        File[] staging_files = STAGING_DIR.listFiles();
        File[] removal_files = REMOVAL_DIR.listFiles();
        if(staging_files.length!=0){
            for(File file : staging_files){
                Utils.delete(file);
            }
        }
        if(removal_files.length!=0){
            for(File file : removal_files){
                Utils.delete(file);
            }
        }
        //move head
        heads.cur_commit = com_id;
        Utils.writeObject(HEADS,heads);
    }




    public static void if_empty(){
        String[] fileList = STAGING_DIR.list();
        System.out.println(fileList.length);
        if (fileList == null || fileList.length == 0){
            System.out.println("No changes added to the commit.");
            return;
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
                    System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                    return;
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
