package ru.philosophyit;

public class DeadLock {

  static class Friend {
    private final String name;
    private static Object lockBow = new Object();
    private static Object lockBowBack = new Object();

    public Friend(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public void bow(Friend bower) {
      synchronized (lockBow) {
      System.out.format("%s: %s подстрелил меня!\n", this.name, bower.getName());
      System.out.format("%s: стреляю в ответ!\n", this.name);
      bower.bowBack(this);
      }
    }

    public void bowBack(Friend bower) {
      synchronized (lockBowBack) { System.out.format("%s: %s подстрелил меня!\n", this.name, bower.getName()); }
    }
  }

  /**
   * Точка входа в программу
   *
   * @param args аргументы командной строки
   */
  public static void main(String[] args) {
    Friend alphonse = new Friend("Alphonse");
    Friend gaston = new Friend("Gaston");

    new Thread(() -> alphonse.bow(gaston)).start();
    new Thread(() -> gaston.bow(alphonse)).start();
  }
}

