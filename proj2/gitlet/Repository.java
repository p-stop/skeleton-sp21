package gitlet;

import java.io.File;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.TreeSet;

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
            if(added_id.equals(cur_com.getID(filename))){
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
        //track staged files one.txt by one.txt
        File[] files = STAGING_DIR.listFiles();
        if (files != null) {
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
        }
        //process removal area
        File[] rm_files = REMOVAL_DIR.listFiles();
        if (rm_files != null) {
            for (File file : rm_files) {
                cur_com.del(file.getName());
            }
        }
        //update commit messages
        cur_com.change(message, heads.cur_commit);
        //save commit
        String com_id = Commit.encode_commit(cur_com);
        File com_point = join(COMMITS_DIR, com_id+".txt");
        Utils.writeObject(com_point, cur_com);
        heads.cur_commit = com_id;
        heads.heads.put(heads.bname,com_id);
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
            }
        }
        else {
            System.out.println("No commit with that id exists.");
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
            }
            else {
                String com_id = heads.heads.get(bname);
                if(!restore(com_id)) {
                    return;
                }
                heads.cur_commit = com_id;
                heads.bname = bname;
                Utils.writeObject(HEADS,heads);
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
        Commit cur_com = Utils.readObject(target_com, Commit.class);
        //if file exist in stage area
        File is_staged = join(STAGING_DIR, filename);
        //if rm is useless
        if(!(is_staged.exists()) && !(cur_com.containsFile(filename))){
            System.out.println("No reason to remove the file.");
            return;
        }
        //do two.txt things for rm()
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
        if (commit_itr != null) {
            for (File file : commit_itr) {
                Commit cur_com = Utils.readObject(file, Commit.class);
                if(cur_com.getMessage().equals(message)){
                    System.out.println(file.getName().replace(".txt",""));
                    found = true;
                }
            }
        }
        if(!found){
            System.out.println("No commit with that id exists.");
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
        if (commit_itr != null) {
            for (File file : commit_itr) {
                Commit cur_com = Utils.readObject(file, Commit.class);
                cur_com.print_log(file.getName().replace(".txt",""));
            }
        }
    }

    public static void status() {
        if (!(GITLET_DIR.exists())) {
            System.out.println("Found no commit with that message.");
            return;
        }
        //ready for modified_files
        TreeSet<String> untracked = new TreeSet<>();
        TreeSet<String> modifications = new TreeSet<>();
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
        find_modified(modifications,cur_com,staged_files);
        for (String mod : modifications) {
            System.out.println(mod);
        }
        System.out.printf("\n");;
        System.out.println("=== Untracked Files ===");
        Commit n_cur_com = Utils.readObject(current_com,Commit.class);
        find_untracked(CWD,untracked,n_cur_com,staged_files);
        for (String un : untracked) {
            System.out.println(un);
        }
        System.out.printf("\n");;
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
        head_class.bname = branch_name;
        Utils.writeObject(HEADS,head_class);
    }

    public static void rmb(String branch_name) {
        if (!(GITLET_DIR.exists())) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }
        HEAD head_class = Utils.readObject(HEADS,HEAD.class);
        if(head_class.heads.containsKey(branch_name)){
            if(branch_name.equals(head_class.bname)){
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
    if (!GITLET_DIR.exists()) {
        System.out.println("Not in an initialized Gitlet directory.");
        return;
    }
    boolean result = restore(com_id);
    if(!result){
        return;
    }
    // Move HEAD
    HEAD heads = Utils.readObject(HEADS, HEAD.class);
    heads.cur_commit = com_id;
    heads.heads.put(heads.bname,com_id);
    Utils.writeObject(HEADS, heads);
}

    private static void clearDirectory(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                Utils.delete(file);
            }
        }
    }

    private static boolean re_help_reset(File file, Commit cur, Commit tar, LinkedList<File> del) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File child : files) {
                    if (child.getName().equals(".gitlet")) {
                        continue;
                    }
                    if (!re_help_reset(child, cur, tar, del)) {
                        return false;
                    }
                }
            }
        } else {
            String fileName = file.getName();
            boolean inCurrent = cur.containsFile(fileName);
            boolean inTarget = tar.containsFile(fileName);

            if (inCurrent && !inTarget) {
                del.add(file);
            } else if (!inCurrent && !inTarget) {
                // File is untracked and would be overwritten
                System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                return false;
            }
            // If file is in target, remove from tar's list to avoid redundant deletion
            if (inTarget) {
                tar.delFile(fileName);
            }
        }
        return true;
    }
    private static boolean restore(String com_id) {
        File givenCom = join(COMMITS_DIR, com_id + ".txt");
        if (!givenCom.exists()) {
            System.out.println("No commit with that id exists.");
            return false;
        }
        HEAD heads = Utils.readObject(HEADS, HEAD.class);
        File currentComFile = join(COMMITS_DIR, heads.cur_commit + ".txt");
        Commit currentCommit = Utils.readObject(currentComFile, Commit.class);
        Commit targetCommit = Utils.readObject(givenCom, Commit.class);

        // Track files to delete
        LinkedList<File> toDelete = new LinkedList<>();
        if (!re_help_reset(CWD, currentCommit, targetCommit, toDelete)) {
            return false;
        }
        for (File file : toDelete) {
            Utils.delete(file);
        }

        // Restore all files from target commit
        targetCommit.restore_all();

        // Clear staging and removal areas
        clearDirectory(STAGING_DIR);
        clearDirectory(REMOVAL_DIR);
        return true;
    }
    private static void find_modified(TreeSet<String> modified,Commit cur, File[] staged_files) {
        for (File file : staged_files) {
            if(cur.containsFile(file.getName())){
                cur.del(file.getName());
            }
            File cwd_file = join(CWD, file.getName());
            if(cwd_file.exists()){
                String cwd_id = Utils.sha1(Utils.readContents(cwd_file));
                String sta_id = Utils.sha1(Utils.readContents(file));
                if(sta_id.equals(cwd_id)){
                    continue;
                }
                else {
                    modified.add(file.getName()+" (modified)");
                }
            }
            else {
                modified.add(file.getName()+" (deleted)");
            }
        }
        String[] tracked_files = cur.getnames();
        if(tracked_files!=null){
            for(String tra_name : tracked_files) {
                File cwd_file = join(CWD, tra_name);
                if(cwd_file.exists()){
                    String cwd_id = Utils.sha1(Utils.readContents(cwd_file));
                    if(cwd_id.equals(cur.getID(tra_name))){
                        continue;
                    }
                    else {
                        modified.add(tra_name+" (modified)");
                    }
                }
                else {
                    File if_removal = join(REMOVAL_DIR, tra_name);
                    if(if_removal.exists()){
                        continue;
                    }
                    else {
                        modified.add(tra_name+" (deleted)");
                    }
                }
            }
        }
    }
    private static void find_untracked(File file,TreeSet<String> untracked, Commit cur, File[] staged_files) {
        if (file.isDirectory()) {
            if (file.getName().equals(".gitlet")) {
                return;
            }
            File[] files = file.listFiles();
            if (files != null) {
                for (File child : files) {
                    find_untracked(child, untracked, cur, staged_files);
                }
            }
        }
        else {
            File if_staged = join(STAGING_DIR, file.getName());
            File is_removed = join(REMOVAL_DIR, file.getName());
            boolean removed = is_removed.exists();
            boolean staged = if_staged.exists();
            boolean inCurrent = cur.containsFile(file.getName());
            if(!staged && (!inCurrent || removed)) {
                untracked.add(file.getName());
            }
        }
    }
    public static void main(String[] args) {
        Commit init_commit = new Commit();
        init_commit.init();

        System.out.println(init_commit.containsFile("wug.txt"));
    }

}
