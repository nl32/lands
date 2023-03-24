package io.github.nl32.township;

import com.mojang.authlib.GameProfile;

import java.util.HashSet;
import java.util.Set;

public class Town {
	Set<GameProfile> members;
	String name;

	public Town(String name) {
		this.name = name;
		members = new HashSet<>();
	}
	public boolean addPlayer(GameProfile playerProfile){
		return members.add(playerProfile);
	}
}
