import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

//Синхронизация по this
public class DeadLock3 {
  private final static int N_THREADS = 24;

  static class Friend {
    private final String name;
    public int hitpoints = 24_000_000;

    public Friend(String name) {
      this.name = name;
    }

    public void bow(Friend bower) {
      synchronized (this) { this.hitpoints--; }
      bower.bowBack(this);
    }

    public synchronized void bowBack(Friend bower) { this.hitpoints--; }

  }

  /**
   * Точка входа в программу
   *
   * @param args аргументы командной строки
   */
  public static void main(String[] args) {
    Date beginning = new Date();
    Friend alphonse = new Friend("Alphonse");
    Friend gaston = new Friend("Gaston");

    ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);

    CompletableFuture<?>[] futures = IntStream.range(0, N_THREADS)
            .mapToObj(ignored -> runCounting(alphonse, gaston, executorService))
            .toArray(CompletableFuture[]::new);

    CompletableFuture.allOf(futures).thenRun(() -> {
      Date timeSpent = new Date(new Date().getTime() - beginning.getTime());
      System.out.println("Затрачено времени: " + timeSpent.getTime() + " миллисекунд");
      System.out.println("Alphonse's hitpoints " + alphonse.hitpoints);
      System.out.println("Gaston's hitpoints " + gaston.hitpoints);
      executorService.shutdown();
    });
  }

  private static CompletableFuture<?> runCounting(Friend friendOne, Friend friendTwo, ExecutorService executorService) {
    return CompletableFuture.runAsync(
            () -> {
              if ((Thread.currentThread().getId() % 2) != 0) {
                for (int i = 0; i < 1_000_000; i++) { friendOne.bow(friendTwo); }
              }
              else {
                for (int i = 0; i < 1_000_000; i++) { friendTwo.bow(friendOne); }
              }
            },
            executorService
    );
  }

}