package info.crlog.poseidon;

import java.util.HashMap;

/**
 *
 * @author Courtney Robinson <courtney@crlog.info>
 */
public class PoseidonCommand {

    private final String rawCommand;
    private HashMap<String, String> params;
    private String command;
    private String rawParameters;

    /**
     * Creates a new command from the given string
     *
     * @param command
     */
    public PoseidonCommand(String command) {
        this.rawCommand = command;
        this.params = new HashMap<String, String>();
        parseCommand();
    }

    /**
     * split and extract each parts
     */
    private void parseCommand() {
        String temp = rawCommand;
        int idx = temp.indexOf(" ");
        if (idx == -1) {
            command = rawCommand;
            rawParameters = "";
        } else {
            command = temp.substring(0, idx).trim();
            rawParameters = temp.substring(idx).trim();
        }
        System.out.println("command:" + command + "[params]" + rawParameters);
    }

    /**
     * Checks if this is a valid command or not. Mainly if its just an empty
     * string
     *
     * @return true if valid false otherwise
     */
    public boolean isValid() {
        return !rawCommand.isEmpty();
    }

    /**
     * @return The parameters the user wanted to pass to a command. i.e. In a
     * string such as git push origin master, this method will return "push
     * origin master" to get git use
     * <code>getCommand</code>
     */
    public String parameters() {
        return rawParameters;
    }

    /**
     * @return The command the user wishes to execute and pass parameters to.
     * i.e. In a string such as git push origin master, this method will return
     * "git" to get push origin master use
     * <code>getParameters</code>
     */
    public String getCommand() {
        return command;
    }

    /**
     * Get a parameter as a boolean value.
     *
     * @param param the param you want to get the value of
     * @return true or false if for e.g. the user set a param prefix as
     * -prefix=true or --prefix=true then doing
     * <code>getBool("prefix")</code> will return a boolean value of true. Note
     * that if prefix is set to any value other than true this method returns
     * false. True,TRUE,TrUe all yield true
     */
    public boolean getBool(String param) {
        return Boolean.parseBoolean(params.get(param));
    }

    /**
     * Gets the value of the given parameter as an integer
     *
     * @param param the value the user set when they entered the command
     * @return -1 if an error occurred or the value set by the user
     */
    public int getInt(String param) {
        try {
            return Integer.parseInt(params.get(param));
        } catch (Exception e) {
            return -1;
        }
    }

    public long getLong(String param) {
        try {
            return Long.parseLong(params.get(param));
        } catch (Exception e) {
            return -1L;
        }
    }

    @Override
    public String toString() {
        return rawCommand;
    }
}
