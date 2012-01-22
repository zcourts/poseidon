package info.crlog.poseidon.console;

import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Courtney Robinson <courtney@crlog.info>
 */
public class PoseidonCommand {

	private final String rawCommand;
	private HashMap<String, String> params;
	private String command;
	private String rawParameters;
	private boolean hasArgs;

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
			hasArgs = false;
		} else {
			hasArgs = true;
			command = temp.substring(0, idx).trim();
			rawParameters = temp.substring(idx).trim();
			String[] paramPairs = rawParameters.split(" ");
			for (String pair : paramPairs) {
				//is it of the form key=value?
				int equalsIdx = pair.indexOf("=");
				//if = is found and character before = is not an escape, i.e. \=
				//then we have a key value pair
				if (equalsIdx != -1 && pair.indexOf("\\") != (equalsIdx - 1)) {
					params.put(pair.substring(0, equalsIdx), pair.substring(equalsIdx + 1));
				} else {
					if (equalsIdx != -1 && pair.indexOf("\\") == (equalsIdx - 1)) {
						//if the user entered git stash\=true replace \
						params.put(pair.replace("\\", ""), "");
					} else {
						//if user enters git stash then stash has no value assigned to it
						//set it's value to empty
						params.put(pair, "");
					}
				}
			}
		}
//        System.out.println("command:" + command + "[params]" + rawParameters);
//        Iterator<String> it = params.keySet().iterator();
//        while (it.hasNext()) {
//            String key = it.next();
//            System.out.println("[Key]:" + key + "[value]:" + params.get(key));
//        }
	}

	/**
	 * 
	 * @return 
	 */
	public String[] getExternalCommand(String absolutePath) {
		if (!absolutePath.isEmpty()) {
			return (getCommand() + " " + rawParameters).split(" ");
		} else {
			return rawCommand.split(" ");
		}
	}

	public String[] getExternalArgs() {
		return rawParameters.split(" ");
	}

	/**
	 * @return true if there are any arguments,false otherwise
	 */
	public boolean hasArguments() {
		return hasArgs;
	}

	/**
	 * This checks if a parameter key has been set by the user
	 *
	 * @param key
	 * @return if the user types git stash then calling
	 * <code>isSet("stash");</code> will yield true, however doing
	 * <code>getString("stash")</code> will return an empty string. The opposite
	 * is true if the user types git stash=true the value returned by getString
	 * will be "true", consequently an invocation of
	 * <code>getBool("stash")</code> will yield the boolean value true.
	 */
	public boolean isSet(String key) {
		return params.containsKey(key);
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
	 * Gets the string value for the given parameter
	 *
	 * @param param
	 * @return the value if one was set or an empty string An empty string is
	 * returned if the user types for e.g. git stash if the user types git
	 * stash=true then "true" is returned. This method also returns an empty
	 * string if the user types e.g. git stash\=true the \ is treated as an
	 * escape sequence and the string "true" is not assigned to stash as it's
	 * key, instead the entire key is stash=true
	 */
	public String getString(String param) {
		String val = params.get(param);
		if (val != null && !val.isEmpty()) {
			return val;
		} else {
			return "";
		}
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
