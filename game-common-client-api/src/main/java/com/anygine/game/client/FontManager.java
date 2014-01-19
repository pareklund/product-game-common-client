package com.anygine.game.client;

import playn.core.PlayN;

import com.anygine.game.client.font.BitmapFont;
import com.google.inject.Singleton;

// TODO: Introduce support for multiple fonts
@Singleton
public class FontManager {

	private final BitmapFont font;
	
	public FontManager() {
		font = new BitmapFont(PlayN.assets().getImageSync("Fonts/Peric.png"), 24.0f, 24.0f);
	}
	
	public BitmapFont getFont() {
		return font;
	}
}
