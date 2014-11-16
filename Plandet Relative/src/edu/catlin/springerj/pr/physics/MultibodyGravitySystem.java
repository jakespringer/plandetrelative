package edu.catlin.springerj.pr.physics;

import java.util.List;

import edu.catlin.springerj.g2e.core.AbstractEntity;
import edu.catlin.springerj.g2e.core.AbstractSystem;
import edu.catlin.springerj.g2e.core.Core;
import edu.catlin.springerj.g2e.exception.InvalidComponentException;
import edu.catlin.springerj.g2e.exception.InvalidSystemException;
import edu.catlin.springerj.g2e.math.Vector2;
import edu.catlin.springerj.g2e.movement.PositionComponent;
import edu.catlin.springerj.g2e.movement.VelocityComponent;

public class MultibodyGravitySystem extends AbstractSystem {
	
	private VelocityComponent vc;
	private PositionComponent pc;
	private GravityComponent gc;
	
	@Override
	public void initialize(AbstractEntity e) {
		vc = e.get(VelocityComponent.class);
		pc = e.get(PositionComponent.class);
		gc = e.get(GravityComponent.class);
	}

	@Override
	public void update() {
		List<AbstractEntity> gravity = getRootManager().getManager(PhysicsManager.class).getGravityEntities();
		for (AbstractEntity e : gravity) {
			try { if (e.get(MultibodyGravitySystem.class).equals(this)) continue; } catch (InvalidSystemException | InvalidComponentException ex) {};
			Vector2 vectorto = e.get(PositionComponent.class).position.subtract(pc.position);
			vc.velocity = Core.getRootManager().getManager(PhysicsManager.class).applyImpulse(vectorto.normalize()
					.multiply((gc.mass*e.get(GravityComponent.class).mass)/(vectorto.lengthSquared()))
					.multiply(Core.getRootManager().getManager(PhysicsManager.class).getGravitationalConstant())
					.multiply(Core.getDefaultTimer().getDeltaTime()), vc.velocity, gc.mass);
		}
	}

}
