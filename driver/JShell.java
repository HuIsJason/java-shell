// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: yujimmy1
// UT Student #: 1005499060
// Author: Jimmy Yu
//
// Student2:
// UTORID user_name: leungh45
// UT Student #: 1005412554
// Author: Howard Leung
//
// Student3:
// UTORID user_name: daijason
// UT Student #: 1005231189
// Author: Jason Dai
//
// Student4:
// UTORID user_name: hujason1
// UT Student #: 1005349204
// Author: Jason Hu
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package driver;

import java.util.Scanner;
import commands.Validate;

/**
 * JShell controls the main loop of the file system. Continuously taking input from the user until
 * exit is entered.
 */
public class JShell {

  public static void main(String[] args) {

    Scanner input = new Scanner(System.in);

    String[] inputArgs;
    String userInput = "";

    Validate check = new Validate();

    // Main loop of the JShell interface
    while (!userInput.trim().contentEquals("exit")) {
      // Creates a string array of each argument in the user input line
      // Separated by white-spaces, where each element of array is one
      // parameter of the command
      System.out.print(check.getTreeCwd() + " # ");
      userInput = input.nextLine();
      inputArgs = userInput.trim().split("\\s+");
      System.out.print(check.validCommand(inputArgs, userInput));
    }
    input.close();
  }
}
