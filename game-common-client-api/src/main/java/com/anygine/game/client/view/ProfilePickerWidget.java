package com.anygine.game.client.view;

import java.util.ArrayList;
import java.util.List;

import playn.core.Canvas;
import playn.core.Canvas.LineCap;
import playn.core.Color;
import playn.core.Image;
import playn.core.Keyboard;
import playn.core.Keyboard.TypedEvent;

import com.anygine.core.common.client.Profile;
import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.core.common.client.input.Input;
import com.anygine.game.client.FontManager;
import com.anygine.game.client.GameplayClientInjectorManager;
import com.anygine.game.client.common.AlignmentX;
import com.anygine.game.client.common.AlignmentY;
import com.anygine.game.client.font.BitmapFont;
import com.anygine.game.client.util.ImageUtil;

public class ProfilePickerWidget extends ViewBase implements View, Keyboard.Listener {

	private final FontManager fontManager;

	private String heading;
	private List<Profile> profiles;
	
	private int profileMarked;
	
	public ProfilePickerWidget(
			String id, Width width, Height height, AlignmentX alignmentX, 
			AlignmentY alignmentY, String heading) {
		super(id, width, height, alignmentX, alignmentY);
		fontManager = GameplayClientInjectorManager.getInjector().getFontManager();
		this.heading = heading;
		profiles = new ArrayList<Profile>();
		profileMarked = 0;
	}
	
	public void addProfile(Profile profile) {
		profiles.add(profile);
	}
	
	public Profile getSelectedProfile() {
		return profiles.get(profileMarked);
	}

	@Override
	public ViewAction update(Input input) {
		if (action == ViewAction.NEXT_VIEW) {
			profileMarked = (profileMarked + 1) % profiles.size();
		} else if (action == ViewAction.PREVIOUS_VIEW) {
			profileMarked = ((profileMarked - 1) + profiles.size()) % profiles.size();
		} else {
			return action;
		}
		action = ViewAction.NOTHING;
		return action;
	}

	@Override
	public Vector2 render(RenderContext renderContext) {
		Canvas canvas = renderContext.getCanvas();
		BitmapFont font = fontManager.getFont();
		font.renderText(
				canvas, heading, 0.0f, 0.0f, AlignmentX.CENTER, AlignmentY.TOP);
		int xOffset = 10;
		int i = 0;
		float maxHeight = 0.0f;
		for (Profile profile : profiles) {
			Image profileImage = profile.getImage();
			canvas.drawImage(
					profileImage, 
					xOffset, 
					50.0f);
			if (i == profileMarked) {
				ImageUtil.drawSquare(
						canvas, xOffset, 50.0f, 
						xOffset + profileImage.width(), 50.0f + profileImage.height(),
						Color.argb(255, 50, 150, 255), 5, LineCap.ROUND);
			}
			if (profileImage.height() > maxHeight) {
			  maxHeight = profileImage.height();
			}
			i++;
			xOffset += profileImage.width();
		}
		return new Vector2(xOffset, maxHeight);
	}

	@Override
	public void onKeyTyped(TypedEvent event) {
		if (event.typedChar() == ' ') {
			action = clickListener.onClick();
		}
	}

}

