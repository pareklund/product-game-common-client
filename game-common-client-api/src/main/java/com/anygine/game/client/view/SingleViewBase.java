package com.anygine.game.client.view;

import playn.core.Canvas;

import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.game.client.common.AlignmentX;
import com.anygine.game.client.common.AlignmentY;

public abstract class SingleViewBase extends ViewBase implements View {

  protected SingleViewBase(
      String id, Width width, Height height, AlignmentX alignmentX,
      AlignmentY alignmentY) {
    super(id, width, height, alignmentX, alignmentY);
  }

  @Override
  public Vector2 render(RenderContext renderContext) {
    offset = renderContext.getOffset();
    float renderWidth = getRenderWidth();
    float renderHeight = getRenderHeight(renderWidth);
    float startX = getStartX(renderWidth);
    float startY = getStartY(renderHeight);
    Vector2 renderSize = renderSingleView(
        renderContext.getCanvas(), offset, startX, startY, 
        renderWidth, renderHeight);

    float viewWidth = renderSize.X;
    if (width.getType() != Dimension.Type.WRAP_CONTENT) {
      viewWidth = width.getSize(parentView);
    }
    float viewHeight = renderSize.Y;
    if (height.getType() != Dimension.Type.WRAP_CONTENT) {
      viewHeight = height.getSize(parentView);
    }
    return new Vector2(viewWidth, viewHeight);
  }

  public abstract float getContentWidth();

  public abstract float getContentHeight();

  public abstract Vector2 renderSingleView(
      Canvas canvas, Vector2 offset, float startX, float startY, 
      float renderWidth, float renderHeight);

  protected float getStartX(float scaledWidth) {
    float startX = offset.X;
    if (width.getType() == Dimension.Type.WRAP_CONTENT) {
      startX = alignmentX.getOffset(
          parentView.getWidth(), getContentWidth(), startX);
    } else { 
      startX = alignmentX.getOffset(scaledWidth, getContentWidth(), startX);
    }
    return startX;
  }

  protected float getStartY(float renderHeight) {
    float startY = offset.Y;
    if (height.getType() == Dimension.Type.WRAP_CONTENT) {
      startY = alignmentY.getOffset(
          parentView.getHeight(this), getContentHeight(), startY);
    } else {
      if (renderHeight >= getContentHeight()) {
        startY = alignmentY.getOffset(renderHeight, getContentHeight(), startY);
      }
    }
    return startY;
  }

  protected float getRenderWidth() {
    float renderWidth = getContentWidth();
    if (width.getType() != Dimension.Type.WRAP_CONTENT) {
      renderWidth = width.getSize(parentView);
    } 
    return renderWidth;
  }

  protected float getRenderHeight(float renderWidth) {
    float renderHeight = getContentHeight();
    if (height.getType() != Dimension.Type.WRAP_CONTENT) {
      renderHeight = height.getSize(parentView);
    } 
    return renderHeight;
  }

}
