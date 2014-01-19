package com.anygine.game.client.view;

import playn.core.Canvas;
import playn.core.Image;

import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.game.client.common.AlignmentX;
import com.anygine.game.client.common.AlignmentY;

public class ImageView extends SingleViewBase implements View {

	private Image image;
	
	protected ImageView(
	    String id, Width width, Height height, AlignmentX alignmentX, 
	    AlignmentY alignmentY, Image image) {
		super(id, width, height, alignmentX, alignmentY);
		this.image = image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

  @Override
  public float getContentWidth() {
    return image.width();
  }

  @Override
  public float getContentHeight() {
    return image.height();
  }

  @Override
  public Vector2 renderSingleView(
      Canvas canvas, Vector2 offset, float startX, float startY,
      float renderWidth, float renderHeight) {
    canvas.drawImage(image, startX, startY);
//    canvas.drawImage(image, startX, startY, renderWidth, renderHeight);
    return new Vector2(renderWidth, renderHeight);
  }

}
