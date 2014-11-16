package edu.catlin.springerj.pr.physics.collision.shape;

import java.util.ArrayList;
import java.util.List;

import edu.catlin.springerj.g2e.math.Vector2;
import edu.catlin.springerj.pr.extra.Utilities;
import edu.catlin.springerj.pr.physics.collision.math.AABB;
import edu.catlin.springerj.pr.physics.collision.math.ProjectionRange;

public class Polygon implements CollisionShape {

    private final int size;
    private final Vector2[] basicPointList;
    private Vector2 pos;
    private double rot;
    private boolean needsUpdate;
    private Vector2[] accuratePointList;
    private AABB aabb;

    public Polygon(Vector2[] basicPointList) {
        this.basicPointList = basicPointList;
        size = basicPointList.length;
        pos = new Vector2();
        rot = 0;
        needsUpdate = false;
        accuratePointList = basicPointList.clone();
        update();
    }

    public Polygon(int vertices, double size) {
        this(vertices, size, false);
    }

    public Polygon(int vertices, double size, boolean pointRight) {
        basicPointList = new Vector2[vertices];
        double i = 0;
        if (!pointRight) {
            i = .5;
            size /= Math.cos(Math.PI / vertices);
        }
        for (; i < vertices; i++) {
            basicPointList[(int) i] = new Vector2(size * Math.cos(Math.PI * 2 * i / vertices), size * Math.sin(Math.PI * 2 * i / vertices));
        }
        this.size = basicPointList.length;
        pos = new Vector2();
        rot = 0;
        needsUpdate = false;
        accuratePointList = basicPointList.clone();
        update();
    }

    public boolean contains(Vector2 point) {
        for (Edge e : getEdgeList()) {
            if (point.subtract(e.p1).dot(e.getDisplacement()) > 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public AABB getAABB() {
        if (needsUpdate) {
            update();
        }
        return aabb;
    }

    public List<Edge> getEdgeList() {
        ArrayList<Edge> ae = new ArrayList<Edge>();
        for (int i = 0; i < size; i++) {
            ae.add(new Edge(getPoint(i), getPoint((i + 1) % size)));
        }
        return ae;
    }

    public Vector2 getPoint(int n) {
        if (needsUpdate) {
            update();
        }
        return accuratePointList[n];
    }

    public Vector2[] getPointList() {
        if (needsUpdate) {
            update();
        }
        return accuratePointList;
    }

    @Override
    public ArrayList<Vector2> getProjectionAxes(CollisionShape other) {
        ArrayList<Vector2> r = new ArrayList<Vector2>();
        for (Edge edge : getEdgeList()) {
            r.add(edge.getNormal());
        }
        return r;
    }

    @Override
    public ProjectionRange getProjectionRange(Vector2 axis) {
        ProjectionRange r = new ProjectionRange();
        r.max = r.min = getPoint(0).dot(axis);
        r.max_low = r.max_high = r.min_low = r.min_high = getPoint(0).dot(axis.normal());
        for (int i = 1; i < size; i++) {
            double x = getPoint(i).dot(axis);
            if (x > r.max - Utilities.SMALL && x < r.max + Utilities.SMALL) {
                double y = getPoint(i).dot(axis.normal());
                if (y > r.max_high) {
                    r.max_high = y;
                } else if (y < r.max_low) {
                    r.max_low = y;
                }
            } else if (x > r.max) {
                r.max = x;
                r.max_high = r.max_low = getPoint(i).dot(axis.normal());
            }
            if (x > r.min - Utilities.SMALL && x < r.min + Utilities.SMALL) {
                double y = getPoint(i).dot(axis.normal());
                if (y > r.min_high) {
                    r.min_high = y;
                } else if (y < r.min_low) {
                    r.min_low = y;
                }
            } else if (x < r.min) {
                r.min = x;
                r.min_high = r.min_low = getPoint(i).dot(axis.normal());

            }
        }
        return r;
    }

    public int getSize() {
        return size;
    }

    @Override
    public void setRotation(double rot) {
        this.rot = rot;
        needsUpdate = true;
    }

    @Override
    public void setPosition(Vector2 pos) {
        this.pos = pos;
        needsUpdate = true;
    }

    private void update() {
        needsUpdate = false;
        for (int i = 0; i < size; i++) {
            double r = basicPointList[i].length();
            double t = basicPointList[i].direction();
            accuratePointList[i] = new Vector2(pos.x + r * Math.cos(t + rot), pos.y + r * Math.sin(t + rot));
        }
        Vector2 min = getPoint(0);
        Vector2 max = getPoint(0);
        for (int i = 1; i < size; i++) {
            if (getPoint(i).x < min.x) {
                min = min.setX(getPoint(i).x);
            }
            if (getPoint(i).y < min.y) {
                min = min.setY(getPoint(i).y);
            }
            if (getPoint(i).x > max.x) {
                max = max.setX(getPoint(i).x);
            }
            if (getPoint(i).y > max.y) {
                max = max.setY(getPoint(i).y);
            }
        }
        aabb = new AABB(min, max);
    }

}
