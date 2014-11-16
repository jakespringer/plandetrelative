package edu.catlin.springerj.pr.player;

import edu.catlin.springerj.g2e.core.AbstractEntity;
import edu.catlin.springerj.g2e.core.AbstractSystem;
import edu.catlin.springerj.g2e.event.EventListener;
import edu.catlin.springerj.g2e.event.EventManager;
import edu.catlin.springerj.g2e.event.KeyboardEvent;

public class PlayerControlSystem extends AbstractSystem {

	@Override
	public void initialize(AbstractEntity e) {
		EventListener<KeyboardEvent> keyboardlistener = new EventListener<KeyboardEvent>() {

			@Override
			public void onEvent(KeyboardEvent event) {
				if (event.pressed) {
					System.out.print("Pressed: ");
					System.out.println((char) event.key);
				} else {
					System.out.print("Released: ");
					System.out.println((char) event.key);
				}
			}
			
		};
		
		this.getRootManager().getManager(EventManager.class).register(keyboardlistener);
	}

	@Override
	public void update() {
		
	}
	
}
