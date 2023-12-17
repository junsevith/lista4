import java.io.*;

public abstract class GoPlayer {
   final Color color;
   final GoBoard board;
   GoPlayer(Color color, GoBoard board){
      this.color = color;
      this.board = board;
   }
   abstract String takeTurn() throws IOException;

   abstract String getName();

   abstract boolean askFinish();
}
