package test;

import static org.junit.Assert.*;
import org.junit.Test;
import commands.Exit;

public class ExitTest {

  String[] inputArgs;

  @Test
  public void testExecute() {
    // Single argument, exit
    inputArgs = "exit".split("\\s+");
    assertEquals(Exit.execute(inputArgs), "Exiting the program\n");
    // Multiple arguments
    inputArgs = "  exit    extra   arguments    whitespace".split("\\s+");
    assertEquals(Exit.execute(inputArgs), "Too many arguments for exit command\n");
  }

}
