import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
   public static void main(String[] args) {
      GoGame game = new GoGame( System.in, System.out );
      try {
         game.startGame();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}
