package com.bad.code2;

public class Cube implements Shape3D {
    private Double x;
    private Double y;
    private Double z;
    private Double edgeLength;

    public Cube(Double x,
                Double y,
                Double z,
                Double edgeLength) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.edgeLength = edgeLength;
    }

    @Override
    public Double getX() {
        return x;
    }

    @Override
    public Double getY() {
        return y;
    }

    @Override
    public Double getZ() {
        return z;
    }

    @Override
    public Double getVolume() {
        return Math.pow(edgeLength, 3);
    }
}
