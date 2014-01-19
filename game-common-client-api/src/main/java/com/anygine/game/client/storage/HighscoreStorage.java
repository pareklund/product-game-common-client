package com.anygine.game.client.storage;

import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;

import playn.core.Json;
import playn.core.PlayN;

import com.anygine.core.common.client.Session;
import com.google.inject.Singleton;

@Singleton
public class HighscoreStorage {

	private static final String HIGHSCORE_OBJ_ID = "aztecadventure.highscorelist";
	private static final String HIGHSCORE_ARRAY_ID = "highscoreList";
	private static final Comparator<Integer> COMPARATOR = new Comparator<Integer>() {
		@Override
		public int compare(Integer i1, Integer i2) {
			if (i1.intValue() == i2.intValue()) {
				return 0;
			}
			if (i2.intValue() > i1.intValue()) {
				return 1;
			}
			return -1;
		}
	};
	
	private SortedMap<Integer, String> highscoreMap;
	
	public HighscoreStorage() {
		highscoreMap = new TreeMap<Integer, String>(COMPARATOR);
		String highscoreListJson = PlayN.storage().getItem(HIGHSCORE_OBJ_ID);
		if (highscoreListJson != null) {
			getHighScoreMap(highscoreListJson);
		}
	}
	
	public void storeScore(Session session) {
		highscoreMap.put(
		    session.getPlayer().getScore(), session.getProfile().getUsername());
		persist();
	}

	public SortedMap<Integer, String> getHighscores() {
		return highscoreMap;
	}
	
	private void getHighScoreMap(String highscoreListJson) {
		highscoreMap = new TreeMap<Integer, String>(COMPARATOR);
		Json.Object highscoreObj = PlayN.json().parse(highscoreListJson);
		Json.Array highscoreArray = highscoreObj.getArray(HIGHSCORE_ARRAY_ID);
		for (int i = 0; i < highscoreArray.length(); i++) {
			Json.Object nameAndScore = highscoreArray.getObject(i);
			highscoreMap.put(nameAndScore.getInt("score"), nameAndScore.getString("name"));
		}
	}

	private void persist() {
		Json.Writer writer = PlayN.json().newWriter();
		writer.object();
		writer.array(HIGHSCORE_ARRAY_ID);
		int i = 0;
		for (Integer score : highscoreMap.keySet()) {
			if (i == 10) {
				break;
			}
			writer.object();
			writer.value("name", highscoreMap.get(score));
			writer.value("score", score);
			writer.end();
			i++;
 		}
		writer.end();
		writer.end();
		PlayN.storage().setItem(HIGHSCORE_OBJ_ID, writer.write());
	}

}
