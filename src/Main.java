import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
   public static void main(String[] args) {
      String input = "\n\n" +
            "1 2\n" +
            "2 2\n" +
            "1 3\n" +
            "2 3\n" +
            "1 4\n" +
            "2 4\n" +
            "2 5\n" +
            "3 4\n" +
            "3 5\n" +
            "4 3\n" +
            "4 5\n" +
            "4 2\n" +
            "5 4\n" +
            "3 2\n" +
            "5 3\n" +
            "4 4\n" +
            "5 2\n" +
            "9 8\n" +
            "4 1\n" +
            "9 7\n" +
            "3 1\n" +
            "9 6\n" +
            "2 1\n" +
            "9 5\n" +
            "3 3\n";
      InputStream in = new ByteArrayInputStream(input.getBytes());
      GoGame game = new GoGame(in, System.out);
      try {
         game.startGame();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}
