import java.util.ArrayList;

public class GameRecorder {
   private final ArrayList<String> moves = new ArrayList<>();

   private final ArrayList<Color[][]> boardStates = new ArrayList<>();

   public void recordBoardState(Color[][] boardState) {
      boardStates.add(boardState);
   }

   public void recordMove(String move) {
      moves.add(move);
   }

   public boolean gameHalted() {
      if (moves.size() < 2) {
         return false;
      } else {
         String lastMove = moves.getLast().split(" ")[1].strip();
         String secondLastMove = moves.get(moves.size() - 2).split(" ")[1].strip();
         return lastMove.equals("pas") && secondLastMove.equals("pas");
      }
   }
}
