package com.anygine.game.client.view;

import playn.core.Canvas;
import playn.core.Canvas.LineCap;
import playn.core.Color;
import playn.core.Key;
import playn.core.Keyboard;
import playn.core.Keyboard.Event;
import playn.core.Keyboard.TypedEvent;

import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.core.common.client.input.Input;
import com.anygine.game.client.FontManager;
import com.anygine.game.client.GameplayClientInjectorManager;
import com.anygine.game.client.common.AlignmentX;
import com.anygine.game.client.common.AlignmentY;
import com.anygine.game.client.font.BitmapFont;

public class EditText extends SingleViewBase 
implements View, Keyboard.Listener {

  private final static int BG_COLOR = Color.argb(200, 225, 225, 225);
  private final static int DARKER_SHADE = Color.argb(200, 100, 100, 100);
  private final static int LIGHTER_SHADE = Color.argb(200, 200, 200, 200);

  private final static int CURSOR_WIDTH = 2;

  private final FontManager fontManager;
  private final float boxHeight;

  private String text;
  private boolean defaultText;
  private double cursorBrightness;
  
  public EditText(
      String id, Width width, Height height, AlignmentX alignmentX, 
      AlignmentY alignmentY, String text) {
    super(id, width, height, alignmentX, alignmentY);
    this.setText(text);
    fontManager = GameplayClientInjectorManager.getInjector().getFontManager();
    defaultText = true;
    cursorBrightness = 0.0d;
    boxHeight = fontManager.getFont().getHeight() + 1;
  }


  public void setText(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  @Override
  public ViewAction update(Input input) {
    cursorBrightness += 0.03d;
    if (action != ViewAction.NOTHING) {
      return action;
    }
    if (defaultText) {
      text = "";
      defaultText = false;
    }
    return action;
  }

  @Override
  public void onKeyDown(Event event) {
    super.onKeyDown(event);
    if (action == ViewAction.NOTHING) {
      if (event.key() == Key.BACKSPACE) {
        text = text.substring(0, Math.max(0, text.length() - 1));
      }
    }
  }

  @Override
  public void onKeyTyped(TypedEvent event) {
    if (fontManager.getFont().getRenderSize(text + event.typedChar()).X 
        < width.getMaxSize(parentView)) {
      text += event.typedChar();
    }
  }

  @Override
  public float getContentWidth() {
    return fontManager.getFont().getRenderSize(text).X;
  }

  @Override
  public float getContentHeight() {
    return fontManager.getFont().getRenderSize(text).Y;
  }

  @Override
  public Vector2 renderSingleView(
      Canvas canvas, Vector2 offset, float startX, float startY,
      float renderWidth, float renderHeight) {
    canvas.setFillColor(BG_COLOR);
    canvas.setStrokeColor(DARKER_SHADE);
    canvas.fillRect(startX, startY, renderWidth, boxHeight);
    canvas.setStrokeWidth(3);
    canvas.setLineCap(LineCap.ROUND);
    canvas.drawLine(startX, startY, startX + renderWidth, startY);
    canvas.drawLine(
        startX + renderWidth, startY, startX + renderWidth, 
        startY + boxHeight);
    canvas.setStrokeColor(LIGHTER_SHADE);
    canvas.drawLine(
        startX + renderWidth, startY + boxHeight, startX, 
        startY + boxHeight);
    canvas.drawLine(startX, startY + boxHeight, startX, startY);
    BitmapFont font = fontManager.getFont();
    Vector2 textSize = font.renderText(
        canvas, text, startX, startY + 1, AlignmentX.LEFT, AlignmentY.TOP);
    if (!defaultText) {
      renderCursor(canvas, startX, startY, textSize.X);
    }
    return new Vector2(renderWidth, renderHeight);
  }

  private void renderCursor(
      Canvas canvas, float startX, float startY, float textWidth) {
    float cursorX = startX + textWidth - 3.0f;
    int cursorColorComponent = 255 - (int) (Math.abs(Math.sin(cursorBrightness) * 64));
    int cursorColor = Color.argb(
        255, cursorColorComponent, cursorColorComponent, cursorColorComponent);
    canvas.setStrokeColor(cursorColor);
    canvas.setStrokeWidth(CURSOR_WIDTH);
    canvas.setLineCap(LineCap.SQUARE);
    canvas.drawLine(
        cursorX, startY + 5.0f, cursorX, startY + boxHeight - 5.0f);
  }

}
