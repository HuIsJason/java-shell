package commands;

import java.io.*;
import java.nio.file.*;

/**
 * This class is responsible for saving the current JShell session as a real file on the user's
 * computer.
 */
public class SaveFile extends Command {

  /**
   * Creates a new file and saves the current shell's history onto the file.
   * 
   * @param inputArgs the full path of the file that will store the session's information
   * @param shellHistory the history (past commands) of the current shell session
   * @return an output message indicating successful execution or errors
   */
  public static String execute(String[] inputArgs, History shellHistory) {
    // evaluate arguments first
    if (inputArgs.length > 2)
      return "Too many arguments\n";
    else if (inputArgs.length < 2)
      return "Not enough arguments\n";

    // now perform the save
    else {
      // store the inputed path, file name, and directory to be saved in
      Path filePath = Paths.get(inputArgs[1]);
      String fileName = filePath.getFileName().toString();
      String parentPath = filePath.getParent().toString();

      // make the new file at the path with the name
      File shellSave = new File(parentPath, fileName);
      try {
        FileWriter shellSaveWriter = new FileWriter(shellSave);
        shellSaveWriter.write(shellHistory.historyFull());
        shellSaveWriter.close();
      } catch (IOException e) {
        return "File could not be written/created successfully\n";
      }
      return "Current shell session saved successfully\n";
    }
  }
}
