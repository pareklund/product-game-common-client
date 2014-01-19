package com.anygine.game.client;

import java.util.Collection;

import com.anygine.common.exception.AnygineException;
import playn.core.Json;
import playn.core.PlayN;
import playn.core.util.Callback;

import com.anygine.core.common.client.AnygineCallback;
import com.anygine.core.common.client.AnygineException_Embeddable;
import com.anygine.core.common.client.Profile;
import com.anygine.core.common.client.api.UniqueConstraintViolationException;
import com.anygine.core.common.client.domain.JsonObjects;
import com.anygine.core.common.codegen.api.JsonWritableInternal.TypeOfData;
import com.anygine.game.client.storage.ProfileStorage;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ProfileManager {

  private final static String PROFILE_SERVICE_URL = "http://localhost:8080/rpc/profile";
  
	private final ProfileStorage storage;
	private final JsonObjects jsonObjects;
	
	@Inject
	public ProfileManager(ProfileStorage storage, JsonObjects jsonObjects) {
		this.storage = storage;
		this.jsonObjects = jsonObjects;
	}
	
	public Profile getDefaultProfile() {
		return storage.getDefaultProfile();
	}

	public Profile getProfile(String id) {
		return storage.getProfile(id);
	}
	
	public Collection<Profile> getProfiles() {
		return storage.getProfiles();
	}

  public void addProfile(
      final String username, final String password, final String email, 
      final boolean autoLogin, final String pictureUrl, 
      final AnygineCallback<Profile, AnygineException> callback) {
    Collection<Profile> profiles = getProfiles();
    for (Profile profile : profiles) {
      if (profile.getUsername().equals(username) 
          || profile.getEmail().equals(email)) {
        callback.onFailure(new UniqueConstraintViolationException(
            "Username or email already in use"));
      }
    }
    
    String addProfileJson = toAddProfileJson(
        username, password, email, autoLogin, pictureUrl);
    PlayN.net().post(PROFILE_SERVICE_URL, addProfileJson, new Callback<String>() {

      // TODO: Encapsulate low-level details ...
      
      @Override
      public void onSuccess(String resultStr) {
        // TODO: Extract into common CallbackBase
        Json.Object resultObj = PlayN.json().parse(resultStr);        
        if (jsonObjects.isException(resultObj)) {
          callback.onFailure(new AnygineException_Embeddable(resultObj.getObject(
              TypeOfData.Object.name())));
        } else {
          // TODO: Check that returned object is of type "Profile"
//          Profile profile = new Profile(resultObj.getObject(
//              TypeOfData.Object.name()));
          Profile profile = null;
          // TODO: Set default from submitted form data
          storage.storeProfile(profile, false);
          callback.onSuccess(profile);
        }
      }

      @Override
      public void onFailure(Throwable cause) {
        callback.onFailure(new AnygineException(cause));
      }
    });
  }

  private String toAddProfileJson(
      String username, String password, String email, boolean autoLogin, 
      String pictureUrl) {
    Json.Writer writer = PlayN.json().newWriter();
    writer.object();
    writer.value("operation", "AddProfile");
    writer.object("data");
    writer.value("username", username);
    writer.value("password", password);
    writer.value("email", email);
    writer.value("autoLogin", autoLogin);
    writer.value("pictureUrl", pictureUrl);
    writer.end();
    writer.end();
    return writer.write();
  }
  
}
