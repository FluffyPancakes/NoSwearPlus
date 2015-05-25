package me.FluffyPancakes.NoSwearPlus;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class SwearBlocker implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		@SuppressWarnings("unchecked")
		List<String> Words = (List<String>) BlockedWords.getConfig().getList(
				"BlockedWords");
		for (String CurseWords : Words) {
			CurseWords = CurseWords.toLowerCase();
			for (String msg : event.getMessage().toLowerCase().split(" ")) {
			if (msg.equals(CurseWords)
					&& !player.hasPermission(Config.getConfig().getString(
							"Config.BypassPerm"))
					&& Config.getConfig().getBoolean(
							"Config.WordBlockedMessage") == true) {
				event.setCancelled(true);
				player.sendMessage(Config.getConfig()
						.getString("Config.WordBlocked").replaceAll("&", "§"));
			} else if (msg.equals(CurseWords)
					&& !player.hasPermission(Config.getConfig().getString(
							"Config.BypassPerm"))
					&& Config.getConfig().getBoolean(
							"Config.WordBlockedReplaceLetter") == true) {
				String crossout = "";
				for (int x = 0; x < CurseWords.length(); x++) {
					crossout += Config.getConfig().getString(
							"Config.WordBlockedReplacerLetter");
				}
				event.setMessage(event.getMessage().replaceAll(
						"(?i)" + CurseWords, crossout.replaceAll("&", "§")));
			} else if (msg.equals(CurseWords)
					&& !player.hasPermission(Config.getConfig().getString(
							"Config.BypassPerm"))
					&& Config.getConfig().getBoolean(
							"Config.WordBlockedReplace") == true) {
				String replace = Config.getConfig().getString(
						"Config.WordBlockedReplacer");
				event.setMessage(event.getMessage().replaceAll(
						"(?i)" + CurseWords, replace.replaceAll("&", "§")));
			}
			}
		}
	}
}