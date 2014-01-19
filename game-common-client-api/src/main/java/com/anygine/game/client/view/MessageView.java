package com.anygine.game.client.view;

import playn.core.Canvas;
import playn.core.Color;
import playn.core.Font;
import playn.core.Font.Style;
import playn.core.Image;
import playn.core.PlayN;
import playn.core.TextFormat;
import playn.core.TextLayout;

import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.game.client.common.AlignmentX;
import com.anygine.game.client.common.AlignmentY;
import com.anygine.game.client.screen.Overlay;

// TODO: Change to CompositeView to be able to re-use views more
public class MessageView extends TextView {

  private Image okButton;
  
  public MessageView(
      String id, Width width, Height height, AlignmentX alignmentX,
      AlignmentY alignmentY, String text, final Overlay overlayAfterMsg) {
    super(id, width, height, alignmentX, alignmentY, text);
    // TODO: Remove hard coding
    okButton = PlayN.assets().getImage("Icons/Button_Smaller.png");
    setClickListener(new ClickListener() {
      
      @Override
      public ViewAction onClick() {
        return new ViewAction(
            "InformedOfTechnicalError", 
            new ViewData("overlay", overlayAfterMsg));
      }
    });
  }

  @Override
  public float getContentHeight() {
    float textHeight = super.getContentHeight();
    // TODO: Add height of button + space as well
    return textHeight;
  }

  @Override
  public Vector2 renderSingleView(
      Canvas canvas, Vector2 offset, float startX, float startY,
      float renderWidth, float renderHeight) {
    Vector2 renderSize = super.renderSingleView(
        canvas, offset, startX, startY, renderWidth, renderHeight);
    float buttonStartX = (renderWidth - okButton.width()) / 2;
    float buttonStartY = startY + renderSize.Y + (renderSize.Y / 2); 
    canvas.drawImage(okButton, buttonStartX, buttonStartY);
    Font font = PlayN.graphics().createFont("Test", Style.BOLD, 15);
/*
    TextFormat textFormat = new TextFormat(
        font, 100, TextFormat.Alignment.LEFT, Color.rgb(0, 0, 0), Effect.NONE);
        */
    TextFormat textFormat = new TextFormat(
        font, 100, TextFormat.Alignment.LEFT);
    TextLayout textLayout = PlayN.graphics().layoutText("OK", textFormat);
    canvas.setStrokeColor(Color.rgb(0, 0, 0));
    canvas.strokeText(textLayout, buttonStartX + 7.0f, buttonStartY + 4.0f);
    renderSize = renderSize.add(
        new Vector2(0, (renderSize.Y / 2) + okButton.height())); 
    return renderSize;
  }

}
