package edu.catlin.springerj.pr.physics.collision.shape;

import edu.catlin.springerj.g2e.math.Vector2;
import edu.catlin.springerj.pr.physics.collision.math.AABB;
import edu.catlin.springerj.pr.physics.collision.math.ProjectionRange;

import java.util.List;

public interface CollisionShape {

    public abstract AABB getAABB();

    public abstract List<Vector2> getProjectionAxes(CollisionShape other);

    public abstract ProjectionRange getProjectionRange(Vector2 axis);
    
    public abstract void setPosition(Vector2 pos);
    
    public abstract void setRotation(double rot);
}
