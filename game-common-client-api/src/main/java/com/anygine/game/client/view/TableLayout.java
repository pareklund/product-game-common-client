package com.anygine.game.client.view;

import java.util.List;

import com.anygine.game.client.common.AlignmentX;
import com.anygine.game.client.common.AlignmentY;

public class TableLayout extends CompositeView<TableRow> implements View {

  protected TableLayout(
      String id, Width width, Height height, AlignmentX alignmentX,
      AlignmentY alignmentY, List<TableRow> rows) {
    super(
        id, width, height, alignmentX, alignmentY, Orientation.Vertical, rows);
  }

}
