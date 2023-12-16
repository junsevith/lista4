public class GoBoard {
   private final GoTile[][] board = new GoTile[19][19];

   private final int boardSize;

   private final GameCounter counter = new GameCounter();

   /**
    * Tworzy planszę o domyślnym rozmiarze 19x19
    */
   public GoBoard(){
      boardSize = 19;
      setNeighbors();
   }

   /**
    * Tworzy planszę o podanym rozmiarze
    * @param boardSize rozmiar planszy
    */
   public GoBoard(int boardSize){
      this.boardSize = boardSize;
      setNeighbors();
   }

   /**
    * Ustawia sąsiadów dla każdego pola
    */
   private void setNeighbors() {
      for (int i = 0; i < boardSize; i++) {
         for (int j = 0; j < boardSize; j++) {
            board[i][j] = new GoTile(counter);
         }
      }

      for(int i = 0; i < boardSize; i++){
         for(int j = 0; j < boardSize; j++){
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

   /**
    * Ustawia kamień na planszy
    * @param x współrzędna x
    * @param y współrzędna y
    * @param color kolor kamienia
    * @return true jeśli udało się ustawić kamień, false jeśli nie
    */
   public boolean placeStone(int x, int y, Color color){
      return board[x][y].setStone(color);
   }

   /**
    * Zwraca planszę w postaci Stringa
    * @return plansza w postaci Stringa
    */
   public String printBoard(){
      StringBuilder sb = new StringBuilder();
      for(int i = 0; i < boardSize; i++){
         for(int j = 0; j < boardSize; j++){
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
