
package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.Mv;
import commands.Pwd;
import commands.Mkdir;
import commands.Cd;
import fileSystem.FileTree;
import fileSystem.Node;

public class MvTest {

  FileTree tree;
  String[] inputArgs;

  @Before
  public void setUp() {
    tree = new FileTree(new Node("", "", false));
  }

  @Test
  public void testExecute() {
    // single argument
    inputArgs = "mv".split("\\s+");
    assertTrue(Mv.execute(inputArgs, tree).equals("Must specify paths\n"));
    // destination directory does not exist
    inputArgs = "mkdir dir".split("\\s+");
    Mkdir.execute(inputArgs, tree);
    inputArgs = "mv dir dir2".split("\\s+");
    assertTrue(Mv.execute(inputArgs, tree).equals("Target item/destination directory does not "
        + "exist, or you are currently in target/destination to be modified\n"));
    // move file to file using relative path
    inputArgs = "mkdir dir dir2".split("\\s+");
    Mkdir.execute(inputArgs, tree);
    inputArgs = "mv dir2 dir".split("\\s+");
    Mv.execute(inputArgs, tree);
    inputArgs = "cd dir/dir2".split("\\s+");
    Cd.execute(inputArgs, tree);
    assertTrue(Pwd.execute(inputArgs, tree).equals("/dir/dir2\n"));
  }

}
