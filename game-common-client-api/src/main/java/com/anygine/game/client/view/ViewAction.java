package com.anygine.game.client.view;

public class ViewAction {

	final static ViewAction NOTHING = new ViewAction("NOTHING", ViewData.NOTHING);
	final static ViewAction NEXT_VIEW = new ViewAction("NEXT_VIEW", ViewData.NOTHING);
	final static ViewAction PREVIOUS_VIEW = new ViewAction("PREVIOUS_VIEW", ViewData.NOTHING);
	
	private final String action;
	private final ViewData data;
	
	public ViewAction(String action, ViewData data) {
		this.action = action;
		this.data = data;
	}
	
	public String getAction() {
		return action;
	}
	
	public ViewData getData() {
		return data;
	}

}
