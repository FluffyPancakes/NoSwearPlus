package me.FluffyPancakes.NoSwearPlus;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class BlockedWords {
	private static FileConfiguration config = null;
	private static File configFile = null;
	public static Reader defConfigStream;

	public static void reloadConfig() {

		if (configFile == null) {
			configFile = new File(NoSwearPlus.getPlugin().getDataFolder(),
					"blockedwords.yml");
		}
		config = YamlConfiguration.loadConfiguration(configFile);
		try {
			defConfigStream = new InputStreamReader(NoSwearPlus.getPlugin()
					.getResource("blockedwords.yml"), "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration
					.loadConfiguration(defConfigStream);
			config.setDefaults(defConfig);
		}
	}

	public static FileConfiguration getConfig() {
		if (config == null) {
			reloadConfig();
		}
		return config;
	}

	public static void saveConfig() {
		if ((config == null) || (configFile == null))
			return;
		try {
			getConfig().save(configFile);
		} catch (IOException ex) {
			NoSwearPlus
					.getPlugin()
					.getLogger()
					.log(Level.SEVERE,
							"Could not save config to " + configFile, ex);
		}
	}

	public static void saveDefaultConfig() {
		if (configFile == null) {
			configFile = new File(NoSwearPlus.getPlugin().getDataFolder(),
					"blockedwords.yml");
		}
		if (!configFile.exists())
			NoSwearPlus.getPlugin().saveResource("blockedwords.yml", false);
	}
}