import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class FourMillions {


  /**
   * Класс-счётчик.
   */
  static class Counter {

    /**
     * Буфер счёта
     */
    private long count = 0;

    /**
     * Считаем +1
     *
     * Комментарий к исправлению (добавление "synchronized" в заголовке метода):
     * данный метод с операцией инкремента выполняется за несколько инструкций,
     * что порождает race condition при выполнении данного кода несколькими потоками.
     * Необходимо ограничить доступ к методу, пока с ним работает один из потоков,
     * поскольку одновременный вызов метода двумя и более потоками может привести к непредсказуемым результатам.
     * Кроме того, возможен и немного другой способ решения проблемы:
     * добавление “монитора” непосредственно внутри цикла в методе “runCounting”
     */
    public synchronized void increment() {
      count++;
    }

    /**
     * Получить текущее значение счётчика
     */
    public long getCount() {
      return count;
    }
  }

  private final static int N_THREADS = 4;

  /**
   * Точка входа в программу
   *
   * @param args арг-ты командной строки
   */
  public static void main(String[] args) {
    Counter counter = new Counter();

    ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);

    // создаём java.Util.Stream для интов щт 0 до 4 (искд.)
    // * не путать Stream и Thread
    CompletableFuture<?>[] futures = IntStream.range(0, N_THREADS)
        // вместо каждой цифры запускаем инкременты счётчика
        .mapToObj(ignored -> runCounting(counter, executorService))
        // собираем CompletableFuture'ы в массив
        .toArray(CompletableFuture[]::new);

    // когда все потоки завершат свою работу
    CompletableFuture.allOf(futures).thenRun(() -> {
      // имеем шанс не получить 4 млн
      System.out.println("Total count: " + counter.getCount());
      executorService.shutdown();
    });
  }

  /**
   * Запускает миллион инкрементов счётчика в отдельном потоке
   *
   * @param counter         счётчик для инкрементов
   * @param executorService пул потоков для работы
   *
   * @return CompletableFuture без результата, разрешаемый после завершения инкрементаций
   */
  private static CompletableFuture<?> runCounting(Counter counter, ExecutorService executorService) {
    return CompletableFuture.runAsync(
        () -> {
          for (int j = 0; j < 1_000_000; j++) {
            counter.increment();
          }
        },
        executorService
    );
  }
}

