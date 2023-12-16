import java.util.*;

public class GoTile {
   /**
    * Kolor kamienia na polu, jeśli null to pole jest puste
    */
   private Color stoneColor = null;

   /**
    * Tablica sąsiadów pola
    * 0 - Góra
    * 1 - Dół
    * 2 - Lewo
    * 3 - Prawo
    */
   private GoTile[] neighbors = new GoTile[4];

   /**
    * Sąsiedzi dający oddech polu
    * 0 - Góra
    * 1 - Dół
    * 2 - Lewo
    * 3 - Prawo
    */
   private final boolean[] breath = {false, false, false, false};
   private int breathCount = 0;

   /**
    * Sąsiedzi, którzy otrzymują oddech od pola
    * 0 - Góra
    * 1 - Dół
    * 2 - Lewo
    * 3 - Prawo
    */
   private final boolean[] dependent = {false, false, false, false};

   /**
    * Licznik zbitych kamieni
    */
   private final GameCounter counter;

   /**
    * Mapa zwracająca przeciwny kierunek
    */
   private final Map<Integer, Integer> revDir = Map.of(
         0, 1,
         1, 0,
         2, 3,
         3, 2
   );

   /**
    * Zwraca przeciwny kierunek, używane do wysyłania informacji do sąsiadów, tak aby wiedzieli skąd przyszła informacja
    * @param direction Kierunek
    * @return Przeciwny kierunek
    */
   private Integer reverseDirection(Integer direction) {
      return revDir.get(direction);
   }

   public GoTile(GameCounter counter) {
      this.counter = counter;
   }

   public void setNeighbors(GoTile[] neighbors) {
      this.neighbors = neighbors;
   }

   /**
    * Resetuje pole do stanu początkowego
    */
   private void resetTile() {
      stoneColor = null;
      breathCount = 0;
      Arrays.fill(breath, false);
      Arrays.fill(dependent, false);
   }

   /**
    * Zwraca kolor kamienia na polu
    * Null, jeśli pole jest puste
    * @return Kolor kamienia na polu
    */
   public Color getStoneColor() {
      return stoneColor;
   }

   /**
    * Zwraca sąsiadów o danym kolorze
    *
    * @param color Kolor do dopasowania
    * @return Lista sąsiadów o danym kolorze
    */
   public List<Integer> getNeighbors(Color color) {
      List<Integer> matchingNeighbors = new ArrayList<>();
      for (int i = 0; i < 4; i++) {
         if (neighbors[i] != null && neighbors[i].getStoneColor() == color) {
            matchingNeighbors.add(i);
         }
      }
      return matchingNeighbors;
   }

   /**
    * Sprawdza, czy pole ma więcej niż jeden oddech
    * @return True, jeśli pole ma więcej niż jeden oddech
    */
   private boolean thereAreOtherBreaths() {
      return breathCount > 1;
   }

   /**
    * Dodaje oddech w danym kierunku
    * @param direction Kierunek z kąd przychodzi oddech
    */
   public void giveBreath(int direction) {
      breath[direction] = true;
      breathCount += 1;
   }

   /**
    * Odbiera oddech z danego kierunku
    * @param direction Kierunek z kąd przychodzi oddech
    */
   public void takeBreath(int direction) {
      breath[direction] = false;
      breathCount -= 1;

      if (breathCount == 0) {
         killStone();
      }
   }

   /**
    * Zabija kamień na polu
    */
   private void killStone() {
      counter.addCapturedStone(stoneColor);
      for ( Integer dir : getNeighbors(stoneColor.opposite())) {
         neighbors[dir].giveBreath(reverseDirection(dir));
      }
      for ( Integer dir : getNeighbors(stoneColor)) {
         neighbors[dir].takeBreath(reverseDirection(dir));
      }

      resetTile();
   }

   /**
    * Ustawia kamień na polu
    *
    * @param color Kolor kamienia
    * @return True, jeśli udało się ustawić kamień
    */
   public boolean placeStone(Color color) {
      if (stoneColor != null) {
         return false;
      } else if (getNeighbors(color.opposite()).size() == 4) {
         for (Integer direction : getNeighbors(color.opposite())) {
              if (!neighbors[direction].thereAreOtherBreaths()) {
                 setupStone(color);
                 return true;
              }
         }
         return false;
      } else {
         setupStone(color);
         return true;
      }
   }

   /**
    * Ustawia na polu kamień danego koloru
    * @param color Kolor kamienia
    */
   private void setupStone(Color color) {
      stoneColor = color;
      checkNeighbors();
   }

   /**
    * Sprawdza sąsiadów i aktualizuje oddechy oraz zależności
    */
   private void checkNeighbors() {
      for (Integer direction : getNeighbors(null)) {
         this.giveBreath(direction);
      }

      for (Integer direction : getNeighbors(stoneColor)) {
         if (neighbors[direction].thereAreOtherBreaths()) {
            this.giveBreath(direction);
         } else {
            dependent[direction] = true;
         }
      }

      for (Integer direction : getNeighbors(stoneColor.opposite())) {
         takeBreath(reverseDirection(direction));
      }

      if (thereAreOtherBreaths()) {
         for ( Integer direction : getNeighbors(stoneColor)) {
            dependent[direction] = true;
         }
      }
   }
}
