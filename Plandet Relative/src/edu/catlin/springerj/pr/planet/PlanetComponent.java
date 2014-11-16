package edu.catlin.springerj.pr.planet;

import edu.catlin.springerj.g2e.core.AbstractComponent;
import edu.catlin.springerj.g2e.core.AbstractEntity;
import edu.catlin.springerj.g2e.math.Color4;
import edu.catlin.springerj.g2e.math.Vector2;
import edu.catlin.springerj.g2e.movement.PositionComponent;

public class PlanetComponent extends AbstractComponent {

	public Vector2 initialPosition;
	public Color4 colorInner, colorOuter;
	
	@Override
	public void initialize(AbstractEntity e) {
		initialPosition = e.get(PositionComponent.class).position;
		colorInner = new Color4(0.95d, 0.95d, 0.95d);
		colorOuter = new Color4(0.05d, 0.05d, 0.05d);
	}
	
}
