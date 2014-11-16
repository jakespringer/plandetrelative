package edu.catlin.springerj.pr.physics.collision.shape;

import edu.catlin.springerj.g2e.math.Vector2;
import edu.catlin.springerj.pr.physics.collision.math.AABB;
import edu.catlin.springerj.pr.physics.collision.math.ProjectionRange;

import java.util.ArrayList;
import java.util.List;

public class Circle implements CollisionShape {

    private double radius;
    private Vector2 pos;

    public Circle(double radius) {
        this.radius = radius;
        pos = new Vector2();
    }

    @Override
    public AABB getAABB() {
        return new AABB(pos.subtract(new Vector2(radius, radius)), pos.add(new Vector2(radius, radius)));
    }

    @Override
    public List<Vector2> getProjectionAxes(CollisionShape other) {
        ArrayList<Vector2> a = new ArrayList<Vector2>();
        if (other instanceof Circle) {
            Circle c = (Circle) other;
            a.add(pos.subtract(c.pos));
        } else if (other instanceof Polygon) {
            Polygon p = (Polygon) other;
            Vector2 point = p.getPoint(0);
            for (Vector2 v : p.getPointList()) {
                if (v.subtract(pos).lengthSquared() < point.subtract(pos).lengthSquared()) {
                    point = v;
                }
            }
            a.add(pos.subtract(point));
        }
        return a;
    }

    @Override
    public ProjectionRange getProjectionRange(Vector2 axis) {
        double x = pos.dot(axis);
        double y = pos.dot(axis.normal());
        ProjectionRange r = new ProjectionRange();
        r.max = x + radius;
        r.min = x - radius;
        r.max_high = r.max_low = r.min_high = r.min_low = y;
        return r;
    }

    @Override
    public void setPosition(Vector2 pos) {
        this.pos = pos;
    }

    @Override
    public void setRotation(double rot) {
        // lol nope
    }
}
