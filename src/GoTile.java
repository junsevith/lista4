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
   private boolean[] breath = {false, false, false, false};
   private int breathCount = 0;

   /**
    * Sąsiedzi, którzy otrzymują oddech od pola
    * 0 - Góra
    * 1 - Dół
    * 2 - Lewo
    * 3 - Prawo
    */
   private boolean[] dependent = {false, false, false, false};

   /**
    * Licznik zbitych kamieni
    */
   private GameCounter counter;

   /**
    * Mapa zwracająca przeciwny kierunek
    */
   private final Map<Integer, Integer> revDir = Map.of(
         0, 1,
         1, 0,
         2, 3,
         3, 2
   );
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
   public void giveBreath(int direction) {
      breath[direction] = true;
      breathCount += 1;
   }

   public void takeBreath(int direction) {
      breath[direction] = false;
      breathCount -= 1;

      if (breathCount == 0) {
         counter.addCapturedStone(stoneColor);
         for (int i = 0; i < 4; i++) {
            if (dependent[i]) {
               neighbors[i].takeBreath(reverseDirection(i));
            }
         }
         resetTile();
      }
   }

   /**
    * Ustawia kamień na polu
    *
    * @param color Kolor kamienia
    * @return True, jeśli udało się ustawić kamień
    */
   public boolean setStone(Color color) {
      if (stoneColor != null) {
         return false;
      } else if (getNeighbors(color.opposite()).size() == 4) {
         //TODO: sprawdzić czy skuje jakieś kamienie
         return false;
      } else {
         stoneColor = color;
         checkNeighbors();
         return true;
      }
   }

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
    * Sprawdza sąsiadów i aktualizuje oddechy oraz zależności
    */
   private void checkNeighbors() {
      for (Integer direction : getNeighbors(null)) {
         this.giveBreath(direction);
      }

      for (Integer direction : getNeighbors(stoneColor)) {
         if (neighbors[direction].thereAreOtherBreaths(reverseDirection(direction))) {
            this.giveBreath(direction);
         } else {
            dependent[direction] = true;
         }
      }

      for (Integer direction : getNeighbors(stoneColor.opposite())) {
           takeBreath(reverseDirection(direction));
      }

      for (int i = 0; i < 4; i++) {
         if (breath[i] && thereAreOtherBreaths(i)) {
            dependent[i] = true;
         }
      }
   }

   /**
    * Sprawdza, czy są inni dostawcy oddechu poza sąsiadem w danym kierunku
    *
    * @param direction Kierunek sąsiada
    * @return True, jeśli są inni dostawcy oddechu poza sąsiadem w danym kierunku
    */
   private boolean thereAreOtherBreaths(int direction) {
      if (!breath[direction]) {
         throw new IllegalArgumentException("Nie można sprawdzić, czy sąsiad jest jedynym dostawcą oddechu, jeśli nie jest dostawcą oddechu");
      }
      return breathCount > 1;

   }
}
