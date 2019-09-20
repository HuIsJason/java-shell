package commands;

/**
 * This function provides a check so that the user can successfully exit the program.
 *
 */
public class Exit extends Command {

  /**
   * This function which is called in Validate class, returns a exit message if the user wants to
   * exit the prompt
   * 
   * @param inputArgs the user input
   * @return a success message or an error message
   */
  public static String execute(String[] inputArgs) {
    // Checks if only argument given is "exit" command
    if (inputArgs.length == 1)
      return "Exiting the program\n";
    else
      return "Too many arguments for exit command\n";
  }
}
