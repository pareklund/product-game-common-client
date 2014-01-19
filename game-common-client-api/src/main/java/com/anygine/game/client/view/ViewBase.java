package com.anygine.game.client.view;

import playn.core.Key;
import playn.core.Keyboard;
import playn.core.Keyboard.Event;
import playn.core.Keyboard.TypedEvent;
import playn.core.PlayN;

import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.core.common.client.input.Input;
import com.anygine.core.common.client.input.KeyboardState;
import com.anygine.game.client.common.AlignmentX;
import com.anygine.game.client.common.AlignmentY;

public abstract class ViewBase implements View, Keyboard.Listener {

	protected final String id;
	protected final Width width;
	protected final Height height;
	protected final AlignmentX alignmentX;
  protected final AlignmentY alignmentY;
	
	protected Vector2 offset;
	
	protected CompositeView parentView;
	
	protected ViewAction action;
	
	protected ClickListener clickListener;

	protected ViewBase(
	    String id, Width width, Height height, AlignmentX alignmentX, 
	    AlignmentY alignmentY) {
		this.id = id;
		this.width = width;
		this.height = height;
		this.alignmentX = alignmentX;
    this.alignmentY = alignmentY;
		action = ViewAction.NOTHING;
	}

	public void setParent(CompositeView parentView) {
		this.parentView = parentView;
	}
	
	@Override
	public ViewAction update(Input input) {
		return action;
	}

	@Override
	public void onActivate() {
		PlayN.keyboard().setListener(this);
	}

	@Override
	public void onInactivate() {
		action = ViewAction.NOTHING;
		// Reset global key listener when inactivated
		PlayN.keyboard().setListener(KeyboardState.GetState());
	}
	
	@Override
	public void onKeyDown(Event event) {
		if (event.key() == Key.LEFT) {
			action = ViewAction.PREVIOUS_VIEW;
		} else if (event.key() == Key.RIGHT) {
			action = ViewAction.NEXT_VIEW;
		}
	}

	@Override
	public void onKeyTyped(TypedEvent event) {
    if (event.typedChar() == ' ') {
      if (clickListener != null) {
        action = clickListener.onClick();
      }
    }
	}

	@Override
	public void onKeyUp(Event event) {
	}

	@Override
	public void setClickListener(ClickListener clickListener) {
		this.clickListener = clickListener;
	}
	
	@Override
	public String getId() {
	  return id;
	}
	
	@Override
	public int getWidth() {
	  return width.getSize(parentView);
	}
	
	@Override
	public int getHeight() {
	  return height.getSize(parentView);
	}
	
	@Override
	public int getMaxWidth() {
    return width.getMaxSize(parentView);
	}
  
  @Override
  public int getMaxHeight() {
    return height.getMaxSize(parentView);
  }
}
