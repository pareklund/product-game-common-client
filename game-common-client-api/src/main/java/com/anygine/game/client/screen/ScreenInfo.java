package com.anygine.game.client.screen;

import com.anygine.game.client.screen.Screen;
import com.anygine.game.client.screen.ScreenType;

public abstract class ScreenInfo {

	protected final String id;
	protected final String name;
	protected final ScreenType type;
	protected final String next;

	public ScreenInfo(String id, String name, ScreenType type, String next) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.next = next;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ScreenType getType() {
		return type;
	}

	public String getNext() {
		return next;
	}

	public abstract Screen getScreen();
}
