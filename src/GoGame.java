import java.io.*;
import java.util.Set;

public class GoGame {

   GoBoard board;
   GoPlayer black;
   GoPlayer white;

   BufferedReader in;
   PrintWriter out;

   public GoGame(InputStream in, OutputStream out) {
      this.in = new BufferedReader(new InputStreamReader(in));
      this.out = new PrintWriter(out, true);
   }

   public void startGame() throws IOException {
      out.println("Witaj w grze Go!");
      setBoard();
      setPlayers();
      beginGame();
   }

   private void setBoard() throws IOException {
      out.println("Podaj rozmiar planszy: ");
      try {
         String line = in.readLine();
         int size;
         if (line.isEmpty()) {
            size = 9;
            out.println("Ustawiam domyślny rozmiar planszy: " + size + "x" + size);
         } else {
            size = Integer.parseInt(line);
         }
         if (size < 5 || size > 19) {
            throw new IllegalArgumentException();
         }
         if (size % 2 == 0) {
            out.println("Zaleca się nieparzysty rozmiar planszy");
         }
         board = new GoBoard(size);
         out.println("Plansza ustawiona");
      } catch (NumberFormatException e) {
         out.println("Niepoprawny rozmiar planszy");
         setBoard();
      } catch (IllegalArgumentException e) {
         out.println("Rozmiar planszy musi być z przedziału [5, 19]");
         setBoard();
      }
   }

   private void setPlayers() {
      out.println("Czy chcesz grać z komputerem? (t/n)");
      if (readAnswer(Set.of("t", "tak"), Set.of("n", "nie"))) {
         out.println("Wybierz kolor: (b/c)");
         if (readAnswer(Set.of("b", "biały"), Set.of("c", "czarny"))) {
            black = new HumanPlayer(Color.BLACK, board, in);
            white = new ComputerPlayer(Color.WHITE, board);
         } else {
            black = new ComputerPlayer(Color.BLACK, board);
            white = new HumanPlayer(Color.WHITE, board, in);
         }
      } else {
         black = new HumanPlayer(Color.BLACK, board, in);
         white = new HumanPlayer(Color.WHITE, board, in);
      }
   }

   private boolean readAnswer(Set<String> goodAnswers, Set<String> badAnswers) {
      try {
         String line = in.readLine();
         if (line.isEmpty()) {
            out.println("Zastosowano domyślną odpowiedź: " + badAnswers.iterator().next());
            return false;
         } else if (goodAnswers.contains(line)) {
            return true;
         } else if (badAnswers.contains(line)) {
            return false;
         } else {
            throw new IllegalArgumentException();
         }
      } catch (IOException e) {
         throw new RuntimeException(e);
      } catch (IllegalArgumentException e) {
         out.println("Niepoprawna odpowiedź");
         return readAnswer(goodAnswers, badAnswers);
      }
   }

   private void beginGame() throws IOException {
      out.println("Zaczynamy grę!");
      GoPlayer activePlayer = black;
      while (gameIsNotOver()) {
         out.println(board.printBoard());
         out.println("Teraz ruch wykonuje " + activePlayer.getName());
         activePlayer.takeTurn();
         activePlayer = (activePlayer == black ? white : black);
      }
   }

   private boolean gameIsNotOver() {
      return true;
   }
}
