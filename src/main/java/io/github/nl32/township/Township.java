package io.github.nl32.township;

import io.github.nl32.township.commands.TownCommands;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.base.api.entrypoint.server.DedicatedServerModInitializer;

import java.util.HashMap;
import java.util.Map;

public class Township implements ModInitializer {


	static Map<String,Town> towns;
	@Override
	public void onInitialize(ModContainer mod) {
		towns = new HashMap<>();
		TownCommands.register();
	}

	public static Map<String, Town> getTowns() {
		return towns;
	}
}
