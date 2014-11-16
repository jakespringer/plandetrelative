package edu.catlin.springerj.pr.physics.collision;

import edu.catlin.springerj.g2e.core.AbstractComponent;
import edu.catlin.springerj.g2e.core.AbstractEntity;

public class CircleCollisionComponent extends AbstractComponent {

	public double radius;
	
	public CircleCollisionComponent() {
		this(1.0d);
	}
	
	public CircleCollisionComponent(double r) {
		radius = r;
	}
	
	@Override
	public void initialize(AbstractEntity e) {
		
	}

}
