package edu.catlin.springerj.pr.planet;

import edu.catlin.springerj.g2e.core.AbstractEntity;
import edu.catlin.springerj.g2e.core.AbstractSystem;
import edu.catlin.springerj.g2e.lwjgl.draw.Graphics;
import edu.catlin.springerj.g2e.math.Color4;
import edu.catlin.springerj.g2e.movement.PositionComponent;
import edu.catlin.springerj.pr.physics.collision.CircleCollisionComponent;

public class PlanetRenderSystem extends AbstractSystem {

    private PositionComponent pos;
    private PlanetComponent pc;
    private CircleCollisionComponent cir;

    @Override
    public void initialize(AbstractEntity e) {
        pos = e.get(PositionComponent.class);
        pc = e.get(PlanetComponent.class);
        cir = e.get(CircleCollisionComponent.class);
    }

    @Override
    public void update() {
        Graphics.drawCircle(pos.position.x + 8, pos.position.y - 8, cir.radius, new Color4(0, 0, 0, .2));
        Graphics.drawCircle(pos.position.x, pos.position.y, cir.radius, pc.colorOuter);
        Graphics.drawCircle(pos.position.x, pos.position.y, cir.radius - 10, pc.colorInner);
    }
}
