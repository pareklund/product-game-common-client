package com.anygine.game.client.storage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import playn.core.Json;
import playn.core.PlayN;

import com.anygine.core.common.client.Profile;
import com.anygine.core.common.client.Profile.Type;
import com.anygine.core.common.client.api.EntityFactory;
import com.google.inject.Singleton;
import com.google.inject.Inject;

// TODO: Replace this with / adjust to EntityStorage
@Singleton
public class ProfileStorage {

  private static final String PROFILES_OBJ_ID = "aztecadventure.profiles";
  private static final String PROFILES_ARRAY_ID = "profiles";

  private final EntityFactory entityFactory;
  
  private String defaultUser;
  private Map<String, Profile> profiles;

  // TODO: Fix this
  @Inject
  public ProfileStorage(EntityFactory entityFactory) {
    this.entityFactory = entityFactory;
    profiles = new HashMap<String, Profile>();
    String profilesJson = PlayN.storage().getItem(PROFILES_OBJ_ID);
    if (profilesJson != null && false) {
      getProfiles(profilesJson);
    } else {
      Profile profile = new Profile(
          Profile.Type.DEV, "Anonymous", "password", "anon@anon.com", false, 
      "Icons/Anonymous.png");
      profiles.put(profile.getUsername(), profile);
      profile = new Profile(
          Profile.Type.AUTHENTICATED, "Par Eklund (dev)", 
          "password", "par.eklund@gmail.com", false, "Icons/ParEklund.png");
      defaultUser = profile.getUsername();
      profiles.put(profile.getUsername(), profile);
      profile = new Profile(
          Type.ADD_PROFILE, "Add Profile", "", "", false, 
      "Icons/AddUser.png");
      profiles.put(profile.getUsername(), profile);
      persist();
    }
  }

  public void storeProfile(Profile profile, boolean defalt) {
    profiles.put(profile.getUsername(), profile);
    if (defalt) {
      defaultUser = profile.getUsername();
    }
    persist();
  }

  public Collection<Profile> getProfiles() {
    return profiles.values();
  }

  public Profile getDefaultProfile() {
    if (defaultUser != null) {
      return profiles.get(defaultUser);
    }
    return null;
  }

  public Profile getProfile(String id) {
    return profiles.get(id);
  }

  private void getProfiles(String profilesJson) {
    profiles = new HashMap<String, Profile>();
    Json.Object profilesObj = PlayN.json().parse(profilesJson);
    defaultUser = profilesObj.getString("defaultId");
    Json.Array profilesArray = profilesObj.getArray(PROFILES_ARRAY_ID);
    for (int i = 0; i < profilesArray.length(); i++) {
      Json.Object profileObj = profilesArray.getObject(i);
      Profile profile = entityFactory.newEntity(Profile.class, profileObj).getObject();
      profiles.put(profile.getUsername(), profile);
    }
  }

  private void persist() {
    Json.Writer writer = PlayN.json().newWriter();
    writer.object();
    writer.value("defaultUser", defaultUser);
    writer.array(PROFILES_ARRAY_ID);
    int i = 0;
    for (Profile profile : profiles.values()) {
      if (i == 10) {
        break;
      }
      writer.object();
      writer.value("type", profile.getType().toString());
      writer.value("username", profile.getUsername());
      writer.value("autoLogin", profile.isAutoLogin());
      // TODO: Refactor package names to ensure default access
      writer.value("pictureUrl", profile.getImageWithPath().getPath());
      writer.end();
      i++;
    }
    writer.end();
    writer.end();
    PlayN.storage().setItem(PROFILES_OBJ_ID, writer.write());
  }

}
