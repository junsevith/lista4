public class GoBoard {
   private final GoTile[][] board = new GoTile[19][19];

   private final GameCounter counter = new GameCounter();

   public GoBoard(){
      setNeighbors();
   }

   /**
    * Ustawia sąsiadów dla każdego pola
    */
   private void setNeighbors() {
      for (int i = 0; i < 19; i++) {
         for (int j = 0; j < 19; j++) {
            board[i][j] = new GoTile(counter);
         }
      }

      for(int i = 0; i < 19; i++){
         for(int j = 0; j < 19; j++){
            GoTile[] neighbors = new GoTile[4];
            if(i > 0){
               neighbors[0] = board[i-1][j];
            }
            if(i < 18){
               neighbors[1] = board[i+1][j];
            }
            if(j > 0){
               neighbors[2] = board[i][j-1];
            }
            if(j < 18){
               neighbors[3] = board[i][j+1];
            }
            board[i][j].setNeighbors(neighbors);
         }
      }
   }

   public boolean placeStone(int x, int y, Color color){
      return board[x][y].setTile(color);
   }

   public String printBoard(){
      StringBuilder sb = new StringBuilder();
      for(int i = 0; i < 19; i++){
         for(int j = 0; j < 19; j++){
            if(board[i][j].getStoneColor() == null){
               sb.append("  ");
            } else if(board[i][j].getStoneColor() == Color.BLACK){
               sb.append("B ");
            } else {
               sb.append("W ");
            }
         }
         sb.append("\n");
      }
      return sb.toString();
   }
}
