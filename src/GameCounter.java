public class GameCounter {
   private int blackStones = 0;

   private int whiteStones = 0;

   public void addCapturedStone(Color stoneColor) {
      if (stoneColor == Color.BLACK) {
         blackStones += 1;
      } else {
         whiteStones += 1;
      }
   }

   public int getBlackStones() {
      return blackStones;
   }

   public int getWhiteStones() {
      return whiteStones;
   }
}
