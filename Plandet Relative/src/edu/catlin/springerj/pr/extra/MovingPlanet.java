package edu.catlin.springerj.pr.extra;

import edu.catlin.springerj.g2e.event.CollisionEvent;
import edu.catlin.springerj.g2e.event.EventListener;
import edu.catlin.springerj.g2e.event.EventManager;
import edu.catlin.springerj.g2e.math.Vector2;
import edu.catlin.springerj.g2e.movement.PositionComponent;
import edu.catlin.springerj.g2e.movement.VelocityComponent;
import edu.catlin.springerj.pr.planet.Planet;

public class MovingPlanet extends Planet {
	public MovingPlanet(double radius, Vector2 pos, Vector2 vel) {
		super(radius);
		get(VelocityComponent.class).velocity = vel;
		get(PositionComponent.class).position = pos;
	}
	
	@Override
	public void initialize() {
		super.initialize();
		this.getRootManager().getManager(EventManager.class).register(new EventListener<CollisionEvent>() {

			@Override
			public void onEvent(CollisionEvent event) {
				//System.out.println(event.a.getClass().getSimpleName() + " collided with " + event.b.getClass().getSimpleName());
			}
			
		});
	}
}
