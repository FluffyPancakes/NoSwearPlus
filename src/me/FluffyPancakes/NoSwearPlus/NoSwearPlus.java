package me.FluffyPancakes.NoSwearPlus;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class NoSwearPlus extends JavaPlugin implements Listener {

	public static NoSwearPlus plugin;

	@Override
	public void onEnable() {
		plugin = this;
		PluginManager manager = getServer().getPluginManager();
		manager.registerEvents(this, this);
		manager.registerEvents(new SwearBlocker(), this);
		getLogger().info("- Enabled");
		Config.saveDefaultConfig();
		BlockedWords.saveDefaultConfig();
	}

	@Override
	public void onDisable() {
		plugin = null;
		getLogger().info("- Disabled");
	}

	public static Plugin getPlugin() {
		return plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] a) {
		Player player = (Player) sender;
		@SuppressWarnings("unchecked")
		List<String> Words = (List<String>) BlockedWords.getConfig().getList(
				"BlockedWords");
		String prefix = "&4&l[&c&lNoSwearPlus&4&l]".replaceAll("&", "§");
		if (cmd.getName().equalsIgnoreCase("NoSwearPlus")) {
			for (String CurseWords : Words) {
				CurseWords = CurseWords.toLowerCase();
				if (a.length == 0) {
					player.sendMessage("&c&m----------".replaceAll("&", "§")
							+ prefix + "&c&m----------".replaceAll("&", "§"));
					player.sendMessage("  &cAdd words: /NoSwearPlus add (word)"
							.replaceAll("&", "§"));
					player.sendMessage("  &cRemove words: /NoSwearPlus remove (word)"
							.replaceAll("&", "§"));
					player.sendMessage("  &cReload plugin: /NoSwearPlus reload"
							.replaceAll("&", "§"));
					player.sendMessage("&c&m---------------------------------"
							.replaceAll("&", "§"));
					return true;
				}

				else if (a[0].equalsIgnoreCase("add")
						&& player.hasPermission(Config.getConfig().getString(
								"Config.AddWordPerm"))) {
					if (a.length == 1) {
						player.sendMessage(prefix
								+ " &cUsage: /NoSwearPlus add (word)"
										.replaceAll("&", "§"));
					} else if (Words.contains(a[1].toLowerCase())
							&& a.length == 2) {
						player.sendMessage(prefix
								+ " &cThat word is already on the list!"
										.replaceAll("&", "§"));
					} else {
						player.sendMessage(prefix
								+ " &cAdded the word: ".replaceAll("&", "§")
								+ a[1].toLowerCase());
						Words.add(a[1].toLowerCase());
						BlockedWords.saveConfig();
						BlockedWords.reloadConfig();
					}
					return true;
				}

				else if (a[0].equalsIgnoreCase("remove")
						&& player.hasPermission(Config.getConfig().getString(
								"Config.RemoveWordPerm"))) {
					if (a.length == 1) {
						player.sendMessage(prefix
								+ " &cUsage: /NoSwearPlus remove (word)"
										.replaceAll("&", "§"));
					} else if (!Words.contains(a[1].toLowerCase())
							&& a.length == 2) {
						player.sendMessage(prefix
								+ " &cThat word is not on the list!"
										.replaceAll("&", "§"));
					} else {
						player.sendMessage(prefix
								+ " &cRemoved the word: ".replaceAll("&", "§")
								+ a[1].toLowerCase());
						Words.remove(a[1].toLowerCase());
						BlockedWords.saveConfig();
						BlockedWords.reloadConfig();
					}
					return true;
				}

				else if (a[0].equals("reload")
						&& player.hasPermission(Config.getConfig().getString(
								"Config.ReloadWordPerm"))) {
					if (a.length == 1) {
						player.sendMessage(prefix
								+ " &cPlugin reloaded!".replaceAll("&", "§"));
						Config.reloadConfig();
						BlockedWords.reloadConfig();
						return true;
					}
				}
			}
		}
		return false;
	}
}
