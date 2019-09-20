package commands;

import java.io.*;
import java.nio.file.*;
import driver.JShell;

/**
 * This class is responsible for loading a saved shell session from a real file on the user's
 * computer.
 */
public class Load extends Command {

  /**
   * Loads a JShell from a saved JShell session file.
   * 
   * @param inputArgs the path of the file containing the saved shell session, to be loaded
   * @param shellHistory the history of the current shell session Load is used in
   * @return
   */
  private static JShell loadJShell(String[] inputArgs, History shellHistory) {

    // create a file with the inputed path
    Path filePath = Paths.get(inputArgs[1]);
    File toLoad = new File(filePath.toString());
    BufferedReader br;
    try {
      // make a new BufferedReader to be able to read files
      br = new BufferedReader(new FileReader(toLoad));
    } catch (FileNotFoundException e1) {
      System.out.println("File does not exist\n");
    }

    String st; // string that updates to each line
    try {
      JShell newShell = new JShell();
      while ((st = br.readLine()) != null) {

        // cut off the numbering of each command
        String command = st.substring(3);

        // run the command in JShell
        JShell.main(command.split(" "));

        // NOW IDK IF I RETURN JSHELL OR NOT, AND IDK IF THATLL WORK
      }
      br.close();
      return newShell;
    } catch (IOException e) {
      System.out.println("Cannot read load file\n");
    }
  }

  public static String execute(String[] inputArgs, History shellHistory) {
    if (inputArgs.length > 2)
      return "Too many arguments\n";
    else if (inputArgs.length < 2)
      return "Not enough arguments\n";
    else if (!shellHistory.historyFull().isEmpty())
      return "Session already active, must be an " + "empty shell session before loading\n";
    else {

    }
  }
}
