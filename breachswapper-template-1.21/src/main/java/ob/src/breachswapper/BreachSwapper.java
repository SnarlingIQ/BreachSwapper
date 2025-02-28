package ob.src.breachswapper;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.text.Text;
import net.minecraft.server.command.CommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BreachSwapper implements ModInitializer {
	public static final String MOD_ID = "breachswapper";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static boolean breachSwapEnabled = true;

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("breachswap")
				.executes(context -> {
					breachSwapEnabled = !breachSwapEnabled;
					String status = breachSwapEnabled ? "enabled" : "disabled";
					context.getSource().sendMessage(Text.literal("§6Breach swapping has been " + status));
					return 1;
				}));
		});

		LOGGER.info("BreachSwapper initialized!");
	}

	public static boolean isBreachSwapEnabled() {
		return breachSwapEnabled;
	}
}