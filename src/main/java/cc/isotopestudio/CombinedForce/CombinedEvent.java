package cc.isotopestudio.CombinedForce;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class CombinedEvent implements Listener {
	private final CombinedForce plugin;

	public CombinedEvent(CombinedForce plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onClass1Skill1(PlayerInteractEntityEvent event) {
	}
}
