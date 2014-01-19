package com.anygine.game.client.view;

public class Height extends Dimension {

	public Height(String heightStr) {
	  super(heightStr);
	}
	
	public Height(int height) {
	  super(height);
	}
	
	@Override
	public int getParentSize(View parentView) {
	  return parentView.getHeight();
	}

  @Override
  public int getParentMaxSize(View parentView) {
    return parentView.getMaxHeight();
  }

}
