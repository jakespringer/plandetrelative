package edu.catlin.springerj.pr.planet;

import edu.catlin.springerj.g2e.core.AbstractEntity;
import edu.catlin.springerj.g2e.math.Vector2;
import edu.catlin.springerj.g2e.movement.FrictionComponent;
import edu.catlin.springerj.g2e.movement.FrictionSystem;
import edu.catlin.springerj.g2e.movement.PositionComponent;
import edu.catlin.springerj.g2e.movement.VelocityComponent;
import edu.catlin.springerj.g2e.movement.VelocityMovementSystem;
import edu.catlin.springerj.pr.physics.GravityComponent;
import edu.catlin.springerj.pr.physics.MultibodyGravitySystem;
import edu.catlin.springerj.pr.physics.collision.CircleCollisionComponent;
import edu.catlin.springerj.pr.physics.collision.SimpleCollisionSystem;

public class Planet extends AbstractEntity {

	public Planet() {
		this(new Vector2(), 10.0d);
	}
	
	public Planet(double x, double y) {
		this(new Vector2(x, y), 10.0d);
	}
	
	public Planet(double r) {
		this(new Vector2(), r);
	}
	
	public Planet(double x, double y, double r) {
		this(new Vector2(x, y), r);
	}
	
	public Planet(Vector2 initialposition, double radius) {
		// Components
		add(new PlanetComponent(), new CircleCollisionComponent(radius));
		add(new PositionComponent(initialposition), new VelocityComponent());
		add(new GravityComponent(radius*Math.PI), new FrictionComponent(10.0d));
	}
	
	@Override
	public void initialize() {
		// Systems
		add(new PlanetRenderSystem());
		add(new VelocityMovementSystem());//, new FrictionSystem());
		add(new MultibodyGravitySystem(), new SimpleCollisionSystem());
	}

	@Override
	public void update() {
		
	}

}
