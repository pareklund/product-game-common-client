package com.anygine.game.client;

import com.anygine.core.common.client.AnygineCallback;
import com.anygine.core.common.client.AnygineException;
import com.anygine.core.common.client.Profile;
import com.anygine.core.common.client.Session;
import com.anygine.core.common.client.api.SessionManager;
import com.anygine.core.common.client.domain.Player;

public interface ClientSessionManager extends SessionManager {
	void login(Profile profile);
	void login(
	    Profile profile, String password, 
	    AnygineCallback<Session<? extends Player<?, ?>>, 
	        AnygineException> callback);
}
