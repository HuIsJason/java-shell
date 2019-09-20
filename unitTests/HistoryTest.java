package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.*;
import fileSystem.FileTree;
import fileSystem.Node;

public class HistoryTest {

  History history;
  FileTree tree;
  String[] inputArgs;
  String userInput;

  @Before
  public void setUp() throws Exception {
    tree = new FileTree(new Node("", "", false));
    history = new History();
  }

  @Test
  public void testExecute() {
    // test history as first command
    userInput = "history";
    history.addToHistory(userInput);
    inputArgs = "history".split("\\s+");
    assertTrue(history.execute(inputArgs, tree).equals("1. history\n"));
    // random user input
    userInput = "asdfasdfasdf";
    history.addToHistory(userInput);
    inputArgs = "asdfasdfasdf".split("\\s+");
    assertTrue(history.execute(inputArgs, tree).equals("1. history\n2. asdfasdfasdf\n"));
    // history with a parameter
    userInput = "history 2";
    history.addToHistory(userInput);
    inputArgs = "history 2".split("\\s+");
    assertTrue(history.execute(inputArgs, tree).equals("3. history 2\n"));
    // history with parameter equal to number of commands
    userInput = "history 4";
    history.addToHistory(userInput);
    inputArgs = "history 4".split("\\s+");
    assertTrue(history.execute(inputArgs, tree).equals(""));
    // history with parameter greater than number of commands
    userInput = "history 9001";
    history.addToHistory(userInput);
    inputArgs = "history 9001".split("\\s+");
    assertTrue(history.execute(inputArgs, tree)
        .equals("1. history\n2. asdfasdfasdf\n3. " + "history 2\n4. history 4\n5. history 9001\n"));


  }

}
