package edu.catlin.springerj.pr.player;

import edu.catlin.springerj.g2e.core.AbstractEntity;
import edu.catlin.springerj.g2e.lwjgl.SpriteComponent;
import edu.catlin.springerj.g2e.lwjgl.SpriteRenderSystem;
import edu.catlin.springerj.g2e.math.Vector2;
import edu.catlin.springerj.g2e.movement.PositionComponent;
import edu.catlin.springerj.g2e.movement.RotationComponent;
import edu.catlin.springerj.g2e.movement.VelocityComponent;
import edu.catlin.springerj.g2e.movement.VelocityMovementSystem;
import edu.catlin.springerj.pr.physics.GravityComponent;
import edu.catlin.springerj.pr.physics.MultibodyGravitySystem;

public class Player extends AbstractEntity {

	public Player() {
		this(new Vector2());
	}
	
	public Player(Vector2 position) {
		add(new PositionComponent(position), new VelocityComponent());
		add(new RotationComponent());
		add(new SpriteComponent("character_idle_right", 8)).get(SpriteComponent.class).imageSpeed = 10;
		
		//add(new GravityComponent(0.0d));
	}
	
	@Override
	public void initialize() {
		add(new PlayerControlSystem());
		add(new SpriteRenderSystem());
		add(new VelocityMovementSystem());
		//add(new MultibodyGravitySystem());
	}

	@Override
	public void update() {
		
	}

}
