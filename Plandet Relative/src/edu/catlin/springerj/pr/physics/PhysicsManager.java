package edu.catlin.springerj.pr.physics;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.catlin.springerj.g2e.core.AbstractEntity;
import edu.catlin.springerj.g2e.core.AbstractManager;
import edu.catlin.springerj.g2e.event.CollisionEvent;
import edu.catlin.springerj.g2e.event.EventManager;
import edu.catlin.springerj.g2e.exception.InvalidComponentException;
import edu.catlin.springerj.g2e.math.Vector2;
import edu.catlin.springerj.g2e.movement.PositionComponent;
import edu.catlin.springerj.pr.physics.collision.CircleCollisionComponent;

public class PhysicsManager extends AbstractManager {

	public final double getGravitationalConstant() {
		return 80000.0d;
	}
	
	List<AbstractEntity> collision;
	List<AbstractEntity> gravity;
	
	public PhysicsManager() {
		collision = new ArrayList<AbstractEntity>();
		gravity = new ArrayList<AbstractEntity>();
	}
	
	@Override
	public void initialize() {
		
	}

	@Override
	public void update() {
		Set<Entry<AbstractEntity, AbstractEntity>> collides = collisionCircleCircle();
		for (Entry<AbstractEntity, AbstractEntity> collided : collides) {
			this.getRootManager().getManager(EventManager.class)
			.fire(new CollisionEvent(collided.getKey(), collided.getValue()));
		}
	}
	
	@Override
	public PhysicsManager add(AbstractEntity e) {
		try {
			e.get(CircleCollisionComponent.class);
//			e.get(PolygonCollisionComponent.class);
			collision.add(e);
		} catch (InvalidComponentException ex) { }
		
		try {
			e.get(GravityComponent.class);
			gravity.add(e);
		} catch (InvalidComponentException ex) { }
		return this;
	}
	
	public List<AbstractEntity> getGravityEntities() {
		return new ArrayList<AbstractEntity>(gravity);
	}
	
	public List<AbstractEntity> getCollisionEntities() {
		return new ArrayList<AbstractEntity>(collision);
	}

	public boolean collides(Vector2 position1, double radius1, Vector2 position2, double radius2) {
		double xdiff = position1.x - position2.x;
		double ydiff = position1.y - position2.y;
		if (Math.sqrt(xdiff*xdiff + ydiff*ydiff) <= radius1+radius2) return true;
		else return false;
	}
	
	private Set<Entry<AbstractEntity, AbstractEntity>> collisionCircleCircle() {
		Set<Entry<AbstractEntity, AbstractEntity>> collisions = new HashSet<Entry<AbstractEntity, AbstractEntity>>();
		for (int i=0; i<collision.size(); i++) {
			for (int j=i+1; j<collision.size(); j++) {
				try {
					CircleCollisionComponent cc1 = collision.get(i).get(CircleCollisionComponent.class),
											 cc2 = collision.get(j).get(CircleCollisionComponent.class);
					PositionComponent		 pc1 = collision.get(i).get(PositionComponent.class),
											 pc2 = collision.get(j).get(PositionComponent.class);
					if (collides(pc1.position, cc1.radius, pc2.position, cc2.radius)) collisions.add
					(new AbstractMap.SimpleEntry<AbstractEntity, AbstractEntity>(collision.get(i), collision.get(j)));
				} catch (InvalidComponentException e) { continue; }
			}
		}
		
		return collisions;
	}
	
	private Map<AbstractEntity, AbstractEntity> collisionCirclePolygon() {
		return null;
	}
	
	private Map<AbstractEntity, AbstractEntity> collisionPolygonPolygon() {
		return null;
	}
	
	public double getGravitationalForce(AbstractEntity a, AbstractEntity b) {
		try {
			double distance = a.get(PositionComponent.class).position.subtract(b.get(PositionComponent.class).position).length();
			return (a.get(GravityComponent.class).mass*b.get(GravityComponent.class).mass)/(distance*distance);
		} catch (InvalidComponentException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Vector2 applyImpulse(Vector2 impulse, Vector2 velocity, double mass) {
		return velocity.add(impulse.multiply(1/mass));
	}
}
