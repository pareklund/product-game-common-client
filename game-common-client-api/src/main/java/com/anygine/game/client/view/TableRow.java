package com.anygine.game.client.view;

import java.util.List;

import com.anygine.game.client.common.AlignmentX;
import com.anygine.game.client.common.AlignmentY;

public class TableRow extends CompositeView<View> implements View {

  protected TableRow(
      String id, Width width, Height height, AlignmentX alignmentX, 
      AlignmentY alignmentY, List<View> childViews) {
    super(
        id, width, height, alignmentX, alignmentY, Orientation.Horizontal, 
        childViews);
  }

}
