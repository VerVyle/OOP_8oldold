package com.vervyle.lw8_oop.drawable.render;

public class Ray2D {
    public final Point2D origin;
    public final Point2D direction;
    public final double angle;
    public final Vector2D radiusVector;

    /**
     * @param angle in radians
     */
    public Ray2D(Point2D origin, double angle) {
        this.origin = origin;
        this.angle = angle;
        this.radiusVector = new Vector2D(Math.cos(angle), Math.sin(angle));
        this.direction = new Point2D(origin.x + radiusVector.x, origin.y + radiusVector.y);
    }

    public Ray2D(Point2D origin, Point2D direction) {
        this.origin = origin;
        angle = Math.atan((direction.y - origin.y) / (direction.x - origin.x));
        double val = Math.sqrt(Math.pow(direction.x - origin.x, 2) + Math.pow(direction.y - origin.y, 2));
        //radiusVector = new Vector2D((direction.x - origin.x) / val, (direction.y - origin.y) / val);
        //this.direction = new Point2D(origin.x + radiusVector.x, origin.y + radiusVector.y);

        this.direction = direction;
        radiusVector = new Vector2D(direction.x - origin.x, direction.y - origin.y);
    }
}
