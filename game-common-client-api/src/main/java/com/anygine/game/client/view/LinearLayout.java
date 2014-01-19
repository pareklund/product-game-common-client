package com.anygine.game.client.view;

import java.util.List;

import playn.core.Canvas;

import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.game.client.common.AlignmentX;
import com.anygine.game.client.common.AlignmentY;

public class LinearLayout extends CompositeView<View> implements View {

  public LinearLayout(
      String id, Width width, Height height, AlignmentX alignmentX, 
      AlignmentY alignmentY, Orientation orientation, List<View> views) {
    super(id, width, height, alignmentX, alignmentY, orientation, views);
  }

  @Override
  public Vector2 render(RenderContext renderContext) {
    Canvas canvas = renderContext.getCanvas();
    canvas.drawText(childViews.get(activeChildIndex).toString(), 0, 50);
    return super.render(renderContext);
  }

}
