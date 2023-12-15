public enum Color {
   BLACK, WHITE;

   static Color opposite(Color color){
      if(color == Color.BLACK){
         return Color.WHITE;
      } else {
         return Color.BLACK;
      }
   }
}