package com.anygine.game.client.view;

import playn.core.Canvas;
import playn.core.Canvas.LineCap;
import playn.core.Color;

import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.game.client.common.AlignmentX;
import com.anygine.game.client.common.AlignmentY;

public class CheckBox extends SingleViewBase implements View {

  private final static int BG_COLOR = Color.argb(200, 225, 225, 225);
  private final static int DARKER_SHADE = Color.argb(200, 100, 100, 100);
  private final static int LIGHTER_SHADE = Color.argb(200, 200, 200, 200);
  
  private final static float WIDTH = 20;
  private final static float HEIGHT = 20;
  
  private boolean checked;
  
  protected CheckBox(
      String id, Width width, Height height, AlignmentX alignmentX,
      AlignmentY alignmentY) {
    super(id, width, height, alignmentX, alignmentY);
    setClickListener(new ClickListener() {
      
      @Override
      public ViewAction onClick() {
        checked = !checked;
        return ViewAction.NOTHING;
      }
    });
  }

  @Override
  public float getContentWidth() {
    return WIDTH;
  }

  @Override
  public float getContentHeight() {
    return HEIGHT;
  }

  @Override
  public Vector2 renderSingleView(
      Canvas canvas, Vector2 offset, float startX, float startY, 
      float renderWidth, float renderHeight) {
    canvas.setFillColor(BG_COLOR);
    canvas.setStrokeColor(DARKER_SHADE);
    canvas.fillRect(startX, startY, WIDTH, HEIGHT);
    canvas.setStrokeWidth(3);
    canvas.setLineCap(LineCap.ROUND);
    canvas.drawLine(startX, startY, startX + WIDTH, startY);
    canvas.drawLine(
        startX + renderWidth, startY, startX + WIDTH, 
        startY + HEIGHT);
    canvas.setStrokeColor(LIGHTER_SHADE);
    canvas.drawLine(
        startX + WIDTH, startY + HEIGHT, startX, 
        startY + HEIGHT);
    canvas.drawLine(startX, startY + HEIGHT, startX, startY);
    if (checked) {
      canvas.setStrokeColor(Color.argb(250, 250, 250, 250));
      canvas.drawLine(startX + 4, startY + 4, startX + WIDTH - 4, startY + HEIGHT - 4);
      canvas.drawLine(startX + 4, startY + HEIGHT - 4, startX + WIDTH - 4, startY + 4);
    }
    return new Vector2(renderWidth, renderHeight);
  }

}
