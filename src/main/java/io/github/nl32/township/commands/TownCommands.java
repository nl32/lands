package io.github.nl32.township.commands;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.nl32.township.Town;
import io.github.nl32.township.Township;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.command.CommandSource;
import net.minecraft.network.message.OutgoingMessage;
import net.minecraft.network.message.SignedChatMessage;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.chat.api.types.ChatS2CMessage;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class TownCommands {
	public static void register() {
		CommandRegistrationCallback.EVENT.register(((dispatcher, buildContext, environment) -> {
			dispatcher.register(
				literal("town")
					.then(
						literal("create").then(
							argument("name", StringArgumentType.string()).executes(TownCommands::createTown)
						))
					.then(
						literal("list").executes(TownCommands::listTowns)
					));
		}));
	}

	private static int createTown(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
		var player = ctx.getSource().getPlayer();
		if (player == null) return 1;
		var townName = ctx.getArgument("name", String.class);
		Town town = new Town(townName);
		town.addPlayer(player.getGameProfile());
		Township.getTowns().put(townName, town);
		return 0;
	}

	private static int listTowns(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
		var keys = Township.getTowns().keySet();
		for (String key : keys) {
			ctx.getSource().sendSystemMessage(Text.of(key));
		}
		return 0;
	}
}
