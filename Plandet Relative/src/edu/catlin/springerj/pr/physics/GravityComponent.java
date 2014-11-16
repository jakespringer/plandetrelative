package edu.catlin.springerj.pr.physics;

import edu.catlin.springerj.g2e.core.AbstractComponent;
import edu.catlin.springerj.g2e.core.AbstractEntity;

public class GravityComponent extends AbstractComponent {

	public double mass;
	
	public GravityComponent() {
		this(0.0d);
	}
	
	public GravityComponent(double m) {
		mass = m;
	}
	
	@Override
	public void initialize(AbstractEntity e) {
		
	}

}
