package gitlet;

import java.nio.file.Files;

import static gitlet.Repository.GITLET_DIR;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter a command");
            return;
        }
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                if(args.length != 1) {
                    System.out.println("Incorrect operands.");
                    return;
                }
                Repository.init();
                break;
            case "add":
                String filename = args[1];
                Repository.add(filename);
                break;
            // TODO: FILL THE REST IN
            case "commit":
                if(args.length != 2) {
                    System.out.println("Incorrect operands.");
                    return;
                }
                String commit_message = args[1];
                Repository.commit(commit_message);
                break;
            case "checkout":
                HEAD heads = Utils.readObject(Repository.HEADS,HEAD.class);
                String what = args[1];
                if(what.equals("--")) {
                    Repository.checkout_1_2(heads.cur_commit,args[2]);
                }
                else if(args.length == 4) {
                    Repository.checkout_1_2(args[1], args[3]);
                }
                else {}

                break;
            case "find":
                String mes = args[1];
                Repository.find(mes);
                break;
            case "branch":
                String branch_name = args[1];
                Repository.branch(branch_name);
                break;
            case "rm-branch":
                String branch = args[1];
                Repository.rmb(branch);
                break;
            case "log":
                Repository.log();
                break;
            case"global-log":
                Repository.global_log();
                break;
            case "rm":
                Repository.rm(args[1]);
                break;
            case"empty":
                Repository.if_empty();
            default:
                throw new GitletException("No command with that name exists.");
        }
    }
}
