package edu.catlin.springerj.pr;

import edu.catlin.springerj.g2e.core.Core;
import edu.catlin.springerj.g2e.event.EventManager;
import edu.catlin.springerj.g2e.lwjgl.LWJGLManager;
import edu.catlin.springerj.g2e.lwjgl.view.View;
import edu.catlin.springerj.g2e.math.Vector2;
import edu.catlin.springerj.pr.extra.MovingPlanet;
import edu.catlin.springerj.pr.graphics.GreyStripedBackground;
import edu.catlin.springerj.pr.physics.PhysicsManager;
import edu.catlin.springerj.pr.planet.Planet;
import edu.catlin.springerj.pr.player.Player;

public class Plandet {
	
	private static Runnable level = new Runnable() {
		
		@Override
		public void run() {
			Core.getRootManager().add(new EventManager(), new PhysicsManager());
			Core.getRootManager().add(new GreyStripedBackground());
//			Player player = new Player(new Vector2(200.0d, 20.0d));
			Planet earth = new MovingPlanet(100.0d, new Vector2(000.0d, -200.0d), new Vector2());
			Core.getRootManager().add(new View(earth));
//			Core.getRootManager().add(new MovingPlanet(100.0d, new Vector2(0, 300), new Vector2(0, 0)), 
//					new MovingPlanet(100.0d, new Vector2(0, -300), new Vector2(0, 0))); 
			
			Core.getRootManager().add(earth);
//					new MovingPlanet(10.0d, new Vector2(200, 500), new Vector2(-50.0d, 0.0d)),
//					new MovingPlanet(10.0d, new Vector2(200, -100), new Vector2(50.0d, 0.0d)),
//					new Planet(100.0d, -320.0d, 100.0d),
//					new Planet(460.0d, -210.0d, 10.0d),
//					new Planet(400.0d, -0.0d, 10.0d),
//					new Planet(720.0d, -27.0d, 10.0d),
//					new Planet(72.0d, 72.0d, 10.0d));
			Core.getRootManager().add(new MovingPlanet(50.0d, new Vector2(00, 200), new Vector2(00.0d, -200.0d)));
			
//			Core.getRootManager().add(player);
			
			//Core.getRootManager().add(new MovingPlanet(100.0d, new Vector2(300, -300), new Vector2(-50, 50)));
		}
		
	};
	
	public static void main(String[] args) {
		LWJGLManager lwjgl;
		
		Core.initialize(lwjgl = new LWJGLManager());
		lwjgl.addRoom("level", level);
		lwjgl.setRoom("level");
		Core.run();
	}
}
