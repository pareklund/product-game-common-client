package com.anygine.game.client.view;

import java.util.HashMap;
import java.util.Map;

public class ViewData {

	public final static ViewData NOTHING = new ViewData(null);
	
	private final Map<String, Object> data;
	
	public ViewData(String key, Object value) {
		data = new HashMap<String, Object>();
		data.put(key, value);
	}
	
	public ViewData(Map<String, Object> data) {
		this.data = data;
	}
	
	public Object get(String key) {
		return data.get(key);
	}

}
