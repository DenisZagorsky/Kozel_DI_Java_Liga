public class DeadLock {

  static class Friend {
    private final String name;
    public int hitpoints = 20_000_000;

    public Friend(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public void bow(Friend bower) {
      synchronized (this) {
        for (int i = 0; i < 5_000_000; i++) {
          this.hitpoints--;
        }
      }
      bower.bowBack(this);
    }

    public synchronized void bowBack(Friend bower) {
        for (int i = 0; i < 5_000_000; i++) { this.hitpoints--; }
      System.out.println(this.hitpoints);
    }
  }

  /**
   * Точка входа в программу
   *
   * @param args аргументы командной строки
   */
  public static void main(String[] args) throws InterruptedException {
    Friend alphonse = new Friend("Alphonse");
    Friend gaston = new Friend("Gaston");
    Thread thread1 = new Thread(() -> alphonse.bow(gaston));
    Thread thread2 = new Thread(() -> gaston.bow(alphonse));
    Thread thread3 = new Thread(() -> alphonse.bow(gaston));
    Thread thread4 = new Thread(() -> gaston.bow(alphonse));

    thread1.start();
    thread2.start();
    thread3.start();
    thread4.start();

  }
}

