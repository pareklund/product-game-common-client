package com.anygine.game.client.view;

import playn.core.Canvas;

import com.anygine.core.common.client.geometry.Vector2;

public class RenderContext {

  private final Canvas canvas;
  private final Vector2 offset;

  public RenderContext(Canvas canvas, Vector2 offset) {
    this.canvas = canvas;
    this.offset = offset;
  }

  public Canvas getCanvas() {
    return canvas;
  }

  public Vector2 getOffset() {
    return offset;
  }


}
