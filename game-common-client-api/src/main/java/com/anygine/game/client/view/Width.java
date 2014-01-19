package com.anygine.game.client.view;

public class Width extends Dimension {

	public Width(String widthStr) {
	  super(widthStr);
	}

	public Width(int width) {
	  super(width);
	}
	
  @Override
  public int getParentSize(View parentView) {
    return parentView.getWidth();
  }

  @Override
  public int getParentMaxSize(View parentView) {
    return parentView.getMaxWidth();
  }

}
