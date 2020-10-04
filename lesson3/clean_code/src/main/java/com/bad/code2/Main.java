package com.bad.code2;

public class Main {
    public static void main(String... args) {
        Cube cube = new Cube(1d, 1d, 1d, 10d);
        System.out.println("Cube volume: " + cube.getVolume());

        Square square = new Square(1d, 1d, 5d);
        System.out.println("Square area: " + square.getArea());
    }
}
