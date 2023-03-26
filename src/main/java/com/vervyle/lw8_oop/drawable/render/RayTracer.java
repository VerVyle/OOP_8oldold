package com.vervyle.lw8_oop.drawable.render;

public class RayTracer {

    public final double EPS = 1e-9;

    double det(double a, double b, double c, double d) {
        return a * d - b * c;
    }

    /**
     * @param ray
     * @param segment2D
     * @return
     * @// TODO: 20.03.2023 поправить это место и добавить изменение свойств обьектов
     */
    public static boolean doesCross(Ray2D ray, Segment2D segment2D) {
        double x0 = segment2D.start.x;
        double x1 = segment2D.end.x;
        double x2 = ray.origin.x;
        double x3 = ray.direction.x;

        double y0 = segment2D.start.y;
        double y1 = segment2D.end.y;
        double y2 = ray.origin.y;
        double y3 = ray.direction.y;

        double v = ray.radiusVector.x;
        double w = ray.radiusVector.y;

        // koefs of linear equation of a line
        double a = y1 - y0;
        double b = x0 - x1;
        double c = -1 * a * x0 - b * y0;

        double t = (-1 * a * x0 - b * y0 - c) / (a * v + b * w);

        return t >= 0;
    }

    public static boolean doesCrossV2(Ray2D ray2D, Segment2D segment2D) {
        Point2D P1 = ray2D.origin;
        Point2D P2 = ray2D.direction;

        Point2D M1 = segment2D.start;
        Point2D M2 = segment2D.end;

        double v = P2.x - P1.x;
        double w = P2.y - P1.y;

        double a = M2.y - M1.y;
        double b = M1.x - M2.x;
        double c = -M1.x * M2.y + M1.y * M2.x;

        double t = (-a * P1.x - b * P1.y - c) / (a * v + b * w);

        double x = P1.x + v * t;
        double y = P1.y + w * t;

        boolean fitX = M1.x <= x && M2.x >= x || M2.x <= x && M1.x >= x;
        boolean fitY = M1.y <= y && M2.y >= y || M2.y <= y && M1.y >= y;

        return t >= 0 && fitX && fitY;
    }
}
