package com.anygine.game.client.font;

import static playn.core.PlayN.graphics;

import java.util.ArrayList;
import java.util.List;

import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.Image;

import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.game.client.common.AlignmentX;
import com.anygine.game.client.common.AlignmentY;

// Simple bitmap-based font implementation
// TODO
// 1) Centered drawing 
// 2) Variable char width
// 3) Dynamically set font size
// (2) and (3) will require accompanying meta info file
public class BitmapFont {

  private final static class TextRow {

    private final CanvasImage row;
    private final float width;

    private TextRow(CanvasImage row, float width) {
      this.row = row;
      this.width = width;
    }
  }

  private final static float WIDTH_COMPENSATION = 12.0f;
  private final static float ROW_SPACE = 6.0f;

  private final Image texture;
  private final float width;
  private final float height;
  private final int charsPerLine;

  public BitmapFont(Image texture, float width, float height) {
    this.texture = texture;
    this.width = width;
    this.height = height;
    this.charsPerLine = (int) (texture.width() / width);
  }

  public float getHeight() {
    return height;
  }

  // TODO: Fix correct returned size
  public Vector2 renderText(
      Canvas canvas, String text, float xDst, float yDst, 
      AlignmentX alignX, AlignmentY alignY) {
    List<TextRow> textRows = generateTextRows(canvas, text);
    renderTextRows(canvas, textRows, xDst, yDst, alignX, alignY);
    return doGetSize(textRows);
  }

  // Convenience method (huge max width guarantees single row calculation)
  public Vector2 getRenderSize(String text) {
    return getRenderSize(text, 1000000);
  }
  
  // TODO: Cache render size (keyed by text + width)
  public Vector2 getRenderSize(String text, int maxWidth) {
    List<TextRow> textRows = generateTextRows(text, maxWidth);
    return doGetSize(textRows);
  }

  private Vector2 doGetSize(List<TextRow> textRows) {
    float sizeX = 0.0f;
    for (TextRow textRow : textRows) {
      if (textRow.width > sizeX) {
        sizeX = textRow.width;
      }
    }
    float sizeY = textRows.size() * height + (textRows.size() - 1) * ROW_SPACE;
    return new Vector2(sizeX, sizeY);
  }

  private List<TextRow> generateTextRows(String text, int canvasWidth) {
    return generateTextRows(null, text, canvasWidth);
  }

  private List<TextRow> generateTextRows(Canvas canvas, String text) {
    return generateTextRows(canvas, text, canvas.width());
  }

  // Send in canvas == null to just support calculating size of 
  // resulting text rows (see #getRenderSize above)
  private List<TextRow> generateTextRows(
      Canvas canvas, String text, float canvasWidth) {
    List<TextRow> textRows = new ArrayList<TextRow>();
    if (text == null) {
      return textRows;
    }
    CanvasImage textRowImage = null;
    if (canvas != null) {
      textRowImage = graphics().createImage(canvasWidth, (int) height);
    }
    float xPos = 0.0f;
    for (char c : text.toCharArray()) {
      Vector2 charOffset = getCharOffset(c);
      if (canvas != null) {
        textRowImage.canvas().drawImage(
            texture, xPos, 0.0f, width, height, charOffset.X, charOffset.Y, 
            width, height);
      }
      xPos += (width - WIDTH_COMPENSATION);
      if (xPos >= canvasWidth - width) {
        xPos -= (width - WIDTH_COMPENSATION);
        xPos += 2 * WIDTH_COMPENSATION;
        textRows.add(new TextRow(textRowImage, xPos));
        if (canvas != null) {
          textRowImage = graphics().createImage(canvasWidth, (int) height);
        }
        xPos = 0.0f;
      }
    }
    xPos += WIDTH_COMPENSATION;
    textRows.add(new TextRow(textRowImage, xPos));
    return textRows;
  }

  private void renderTextRows(
      Canvas canvas, List<TextRow> textRows, float xDst, float yDst, 
      AlignmentX alignX, AlignmentY alignY) {
    if (alignY == AlignmentY.CENTER) {
      // Destination pos = (canvas height - total text height) / 2
      yDst += (canvas.height() 
          - ((textRows.size() * (height + ROW_SPACE)) - ROW_SPACE)) / 2;
    } else if (alignY == AlignmentY.BOTTOM) {
      yDst += (canvas.height() 
          - ((textRows.size() * (height + ROW_SPACE)) - ROW_SPACE));
    }

    for (TextRow textRow : textRows) {
      // Alignment.LEFT
      float xDstOffset = 0.0f;
      xDstOffset = alignX.getOffset(canvas.width(), textRow.width, xDstOffset);
/*
      if (alignX == AlignmentX.CENTERED) {
        xDstOffset = (canvas.width() - textRow.width) / 2;
      } else if (alignX == AlignmentX.RIGHT) {
        // Alignment.RIGHT
        xDstOffset = canvas.width() - textRow.width;
      }
      */
      canvas.drawImage(
          textRow.row, xDst + xDstOffset, yDst, textRow.width, height, 
          0.0f, 0.0f, textRow.width, height); 
      yDst += height + ROW_SPACE;
    }
  }

  private Vector2 getCharOffset(char c) {
    int value = (int) c;
    int yOffset = value / charsPerLine;
    int xOffset = value % charsPerLine;
    return new Vector2(xOffset * width, yOffset * height);
  }

}
