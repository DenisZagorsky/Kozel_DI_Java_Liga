package com.bad.code2;

public class Square implements Shape2D {
    private Double x;
    private Double y;
    private Double edgeLength;

    public Square(Double x, Double y, Double edgeLength) {
        this.x = x;
        this.y = y;
        this.edgeLength = edgeLength;
    }

    @Override
    public Double getX() { return x; }

    @Override
    public Double getY() {
        return y;
    }

    @Override
    public Double getArea() {
        return edgeLength * edgeLength;
    }
}
