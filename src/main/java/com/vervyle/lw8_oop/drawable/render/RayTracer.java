package com.vervyle.lw8_oop.drawable.render;

public class RayTracer {

    public final double EPS = 1e-9;

    double det(double a, double b, double c, double d) {
        return a * d - b * c;
    }

    /**
     * @// TODO: 20.03.2023 поправить это место и добавить изменение свойств обьектов
     * @param ray
     * @param segment2D
     * @return
     */
    public static boolean doesCross(Ray2D ray, Segment2D segment2D) {
        double a = segment2D.end.y - segment2D.start.y;
        double b = segment2D.start.x - segment2D.end.x;
        double c = -1 * segment2D.start.x * segment2D.end.y + segment2D.start.y * segment2D.end.x;

        double v = ray.radiusVector.x;
        double w = ray.radiusVector.y;


        double t = (-a * ray.origin.x - b * ray.origin.y - c) / (a * v + b * w);

        return t >= 0;
    }
}
