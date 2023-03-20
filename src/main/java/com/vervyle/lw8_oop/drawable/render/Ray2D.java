package com.vervyle.lw8_oop.drawable.render;

public class Ray2D {
    public final Point2D origin;
    public final double angle;
    public final Vector2D radiusVector;

    /**
     * @param origin
     * @param angle  in radians
     */
    public Ray2D(Point2D origin, double angle) {
        this.origin = origin;
        this.angle = angle;
        this.radiusVector = new Vector2D(Math.cos(angle), Math.sin(angle));
    }

    public Ray2D(Point2D start, Point2D direction) {
        origin = start;
        angle = Math.atan((direction.y - start.y) / (direction.x - start.x));
        radiusVector = new Vector2D(direction.x - start.x, direction.y - start.y);
    }
}
