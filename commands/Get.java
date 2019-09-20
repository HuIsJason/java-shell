package commands;

import fileSystem.FileTree;
import java.net.*;
import java.io.*;

/**
 * The Get class implements functionality of retrieving files from URLs
 */

public class Get extends Command {

  /**
   * Retrieves contents from a URL and creates a new file with the contents.
   * 
   * @param inputArgs Contains the URL whose contents are to be retrieved
   * @param tree The file system
   * @return Message that reports successful execution, or errors in input
   */
  public static String execute(String inputArgs[], FileTree tree) throws Exception {
    // String message = "";

    // check if user specified URL
    if (inputArgs.length <= 1)
      return "Must specify URL\n";
    else if (inputArgs.length > 2)
      return "Too many arguments\n";
    else {
      URL website = new URL(inputArgs[1]);
      BufferedReader in = new BufferedReader(new InputStreamReader(website.openStream()));

      // now get the name of the file
      String[] list = inputArgs[1].split("/");
      String websiteName = list[list.length - 1];
      websiteName = websiteName.substring(0, websiteName.indexOf('.'));

      // loop that gets the contents located at URL
      String line = new String();
      String fileContent = new String();
      while ((line = in.readLine()) != null) {
        fileContent += "\n" + line;
      }
      in.close();

      // now add the new node to the current working directory
      tree.getCwd().addFile(websiteName, fileContent);
      return "File successfully retrieved\n";
    }
  }
}
