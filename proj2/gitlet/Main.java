package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                Repository.init();
                break;
            case "add":
                String filename = args[1];
                Repository.add(filename);
                break;
            // TODO: FILL THE REST IN
            case "commit":
                String commit_message = args[1];
                Repository.commit(commit_message);
                break;
            case "checkout":
                String what = args[1];
                if(what.equals("--")) {
                    Repository.checkout_1_2(Repository.current_commit,args[2]);
                }
                else if(args.length == 4) {
                    Repository.checkout_1_2(args[1], args[3]);
                }
                else {}

                break;
            case "log":
                Repository.log();
                break;
        }
    }
}
