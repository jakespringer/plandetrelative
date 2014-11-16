package edu.catlin.springerj.pr.physics.collision;

import edu.catlin.springerj.g2e.core.AbstractEntity;
import edu.catlin.springerj.g2e.core.AbstractSystem;
import edu.catlin.springerj.g2e.core.Core;
import edu.catlin.springerj.g2e.event.CollisionEvent;
import edu.catlin.springerj.g2e.event.EventListener;
import edu.catlin.springerj.g2e.event.EventManager;
import edu.catlin.springerj.g2e.math.Vector2;
import edu.catlin.springerj.g2e.movement.PositionComponent;
import edu.catlin.springerj.g2e.movement.VelocityComponent;
import edu.catlin.springerj.pr.physics.GravityComponent;
import edu.catlin.springerj.pr.physics.PhysicsComponent;
import edu.catlin.springerj.pr.physics.PhysicsManager;

public class SimpleCollisionSystem extends AbstractSystem {

	private PositionComponent pc;
	private VelocityComponent vc;
	private GravityComponent gc;
	private CircleCollisionComponent ccc;

	@Override
	public void initialize(AbstractEntity e) {
		pc = e.get(PositionComponent.class);
		vc = e.get(VelocityComponent.class);
		gc = e.get(GravityComponent.class);
		ccc = e.get(CircleCollisionComponent.class);

		final SimpleCollisionSystem thus = this;
		e.getRootManager().getManager(EventManager.class).register(new EventListener<CollisionEvent>() {
			@Override
			public void onEvent(CollisionEvent event) {
				thus.onEvent(event);
			}
		});
	}

	@Override
	public void update() {

	}

	private void onEvent(CollisionEvent event) {
		PhysicsManager pm = Core.getRootManager().getManager(PhysicsManager.class);
		
		AbstractEntity other = null;
		if (event.a.get(SimpleCollisionSystem.class).equals(this)) other = event.b;
		else return;

		PositionComponent opc = other.get(PositionComponent.class);
		VelocityComponent ovc = other.get(VelocityComponent.class);
		GravityComponent ogc = other.get(GravityComponent.class);
		CircleCollisionComponent occc = other.get(CircleCollisionComponent.class);
		
		Vector2 vectorto = opc.position.subtract(pc.position);
		Vector2 vectorton = vectorto.normalize();

		Vector2 axis = vectorton.reverse();
        double depth = ccc.radius + occc.radius - vectorto.length();
		
		pc.position = pc.position.add(axis.multiply((depth * (1/ogc.mass)) / ((1/gc.mass) + (1/ogc.mass))));
        opc.position = opc.position.add(axis.multiply(-(depth * (1/ogc.mass)) / ((1/gc.mass) + (1/ogc.mass))));
		
		Vector2 ovcvelocity = ovc.velocity, vcvelocity = vc.velocity;
	
		Vector2 vel = ovcvelocity.subtract(vcvelocity);
		
		if (vel.dot(axis) > 0) {
            double impulse = 2.0 * vel.dot(axis) / ((1/gc.mass) + (1/ogc.mass));
            vc.velocity = pm.applyImpulse(axis.multiply(impulse), vc.velocity, gc.mass);
            ovc.velocity = pm.applyImpulse(axis.multiply(-impulse), ovc.velocity, ogc.mass);
		}
	}
}
