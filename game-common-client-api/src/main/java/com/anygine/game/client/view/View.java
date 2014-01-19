package com.anygine.game.client.view;

import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.core.common.client.input.Input;

public interface View {

	static enum Type { 
	  LinearLayout, TextView, EditText, ImageView, Button, TableLayout, TableRow, CheckBox };
	
	static enum Orientation { 
	  Horizontal, Vertical;
	
	  public static Orientation getValue(String text) {
	    if (text == null) {
	      return null;
	    } else if (text.equalsIgnoreCase("vertical")) {
	      return Vertical;
	    } else if (text.equalsIgnoreCase("horizontal")) {
	      return Horizontal;
	    } else {
	      throw new IllegalArgumentException("Illegal enum string: " + text);
	    }
	  }
	};
	
	Vector2 render(RenderContext renderContext);

	ViewAction update(Input input);
	
	void onActivate();
	
	void onInactivate();
	
	// TODO: Fix this ugliness
	void setParent(CompositeView view);
	
	void setClickListener(ClickListener clickListener);
	
	String getId();
	
	int getWidth();
	
	int getHeight();

  int getMaxWidth();

  int getMaxHeight();
	
}
