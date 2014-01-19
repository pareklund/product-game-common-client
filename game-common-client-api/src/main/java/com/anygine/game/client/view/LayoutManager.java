package com.anygine.game.client.view;

import static playn.core.PlayN.json;
import static playn.core.PlayN.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import playn.core.Image;
import playn.core.Json;
import playn.core.Json.Object;
import playn.core.PlayN;
import playn.core.ResourceCallback;

import com.anygine.core.common.client.input.Input;
import com.anygine.game.client.common.AlignmentX;
import com.anygine.game.client.common.AlignmentY;
import com.anygine.game.client.view.View.Orientation;

public class LayoutManager {

  private View view;
  private Map<String, View> viewMap;

  public LayoutManager(
      final String layoutRef, final int width, final int height, 
      final LayoutReadyCallback layoutReadyCallback) {
    viewMap = new HashMap<String, View>();
    PlayN.assets().getText(layoutRef, new ResourceCallback<String>() {
      @Override
      public void error(Throwable err) {
        log().error(err.getMessage());
        throw new RuntimeException("Could not load view info: " + layoutRef);
      }

      @Override
      public void done(String viewInfoText) {
        view = parseView(json().parse(viewInfoText));
        addStandinParent(width, height);
        if (layoutReadyCallback != null) {
          layoutReadyCallback.onLayoutReady(LayoutManager.this);
        }
        view.onActivate();
      }
    });		
  }

  public LayoutManager(View view, int width, int height) {
    this.view = view;
    addStandinParent(width, height);
    view.onActivate();
  }

  public ViewAction update(Input input) {
    return view.update(input);

  }

  public void render(RenderContext renderContext) {
    view.render(renderContext);
  }

  public View findViewById(String id) {
    return viewMap.get(id);
  }

  public void onInactivate() {
    view.onInactivate();
  }

  public void onActivate() {
    view.onActivate();
  }

  private void addStandinParent(int width, int height) {
    // Add a parent to represent screen/overlay holding view
    // This will make recursive calculation of widths/heights work
    List<View> viewHolder = new ArrayList<View>(1);
    viewHolder.add(view);
    LinearLayout standinParent = 
      new LinearLayout(
          "StandinParent", new Width(width), new Height(height), 
          AlignmentX.LEFT, AlignmentY.TOP, Orientation.Vertical, viewHolder);
    view.setParent(standinParent);
  }

  private View parseView(Json.Object jso) {
    View.Type type = View.Type.valueOf(jso.getString("type"));
    String id = jso.getString("id");
    Width width = new Width(jso.getString("width"));
    Height height = new Height(jso.getString("height"));
    AlignmentX alignmentX = AlignmentX.getAlignment(jso.getString("alignX"));
    AlignmentY alignmentY = AlignmentY.getAlignment(jso.getString("alignY"));
    switch (type) {
      case LinearLayout:
        return parseLinearLayout(
            jso, id, width, height, alignmentX, alignmentY);
      case TextView:
        return parseTextView(
            jso, id, width, height, alignmentX, alignmentY);
      case EditText:
        return parseEditText(
            jso, id, width, height, alignmentX, alignmentY);
      case ImageView:
        return parseImageView(
            jso, id, width, height, alignmentX, alignmentY);
      case Button:
        return parseButton(
            jso, id, width, height, alignmentX, alignmentY);
      case TableLayout:
        return parseTableLayout(
            jso, id, width, height, alignmentX, alignmentY);		  
      case TableRow:
        return parseTableRow(
            jso, id, width, height, alignmentX, alignmentY);
      case CheckBox:
        return parseCheckBox(
            jso, id, width, height, alignmentX, alignmentY);            
      default:
        throw new RuntimeException("Illegal view type: " + type); 
    }
  }
  
  private LinearLayout parseLinearLayout(
      Json.Object jso, String id, Width width, Height height, 
      AlignmentX alignmentX, AlignmentY alignmentY) {
    View.Orientation orientation = 
      View.Orientation.getValue(jso.getString("orientation"));
    Json.Array viewArrayJson = jso.getArray("Views");
    List<View> views = new ArrayList<View>();
    for (int i = 0; i < viewArrayJson.length(); i++) {
      views.add(parseView(viewArrayJson.getObject(i)));
    }
    LinearLayout linearLayout = 
      new LinearLayout(
          id, width, height, alignmentX, alignmentY, orientation, views);
    for (View view : views) {
      view.setParent(linearLayout);
    }
    viewMap.put(id, linearLayout);
    return linearLayout;
  }
  
  private TextView parseTextView(
      Json.Object jso, String id, Width width, Height height, 
      AlignmentX alignmentX, AlignmentY alignmentY) {
    String text = jso.getString("text");
    TextView textView = new TextView(
        id, width, height, alignmentX, alignmentY, text);
    viewMap.put(id, textView);
    return textView;
  }
  
  private EditText parseEditText(
      Json.Object jso, String id, Width width, Height height, 
      AlignmentX alignmentX, AlignmentY alignmentY) {
    String text = jso.getString("text");
    EditText editText = new EditText(
        id, width, height, alignmentX, alignmentY, text);
    viewMap.put(id, editText);
    return editText;
  }
  
  private ImageView parseImageView(
      Json.Object jso, String id, Width width, Height height, 
      AlignmentX alignmentX, AlignmentY alignmentY) {
    Image image = null;
    String imagePath = jso.getString("image");
    if (imagePath != null) {
      image = PlayN.assets().getImage(imagePath);
    }
    ImageView imageView = new ImageView(
        id, width, height, alignmentX, alignmentY, image);
    viewMap.put(id, imageView);
    return imageView;
  }
  
  private Button parseButton(
      Json.Object jso, String id, Width width, Height height, 
      AlignmentX alignmentX, AlignmentY alignmentY) {
    Image image = null;
    String text = jso.getString("text");
    String imagePath = jso.getString("image");
    if (imagePath != null) {
      image = PlayN.assets().getImage(imagePath);
    }
    Button button = new Button(
        id, width, height, alignmentX, alignmentY, text, image);
    viewMap.put(id, button);
    return button;  
  }

  private TableLayout parseTableLayout(
      Json.Object jso, String id, Width width, Height height, 
      AlignmentX alignmentX, AlignmentY alignmentY) {
    Json.Array viewArrayJson = jso.getArray("Rows");
    List<TableRow> tableRows = new ArrayList<TableRow>();
    for (int i = 0; i < viewArrayJson.length(); i++) {
      tableRows.add((TableRow) parseView(viewArrayJson.getObject(i)));
    }
    TableLayout tableLayout = new TableLayout(
          id, width, height, alignmentX, alignmentY, tableRows);
    for (TableRow tableRow : tableRows) {
      tableRow.setParent(tableLayout);
    }
    viewMap.put(id, tableLayout);
    return tableLayout;
  }

  private TableRow parseTableRow(
      Json.Object jso, String id, Width width, Height height, 
      AlignmentX alignmentX, AlignmentY alignmentY) {      
    Json.Array viewArrayJson = jso.getArray("Columns");
    List<View> views = new ArrayList<View>();
    for (int i = 0; i < viewArrayJson.length(); i++) {
      views.add(parseView(viewArrayJson.getObject(i)));
    }
    TableRow tableRow = new TableRow(
      id, width, height, alignmentX, alignmentY, views);
    for (View view : views) {
      view.setParent(tableRow);
    }
    viewMap.put(id, tableRow);
    return tableRow;
  }

  private View parseCheckBox(
      Object jso, String id, Width width, Height height,
      AlignmentX alignmentX, AlignmentY alignmentY) {
    CheckBox checkBox = new CheckBox(
        id, width, height, alignmentX, alignmentY);
    viewMap.put(id, checkBox);
    return checkBox;  
  }

}
