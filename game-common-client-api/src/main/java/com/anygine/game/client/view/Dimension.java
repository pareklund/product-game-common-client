package com.anygine.game.client.view;

public abstract class Dimension {

  public static enum Type { 
    FIXED, PERCENTAGE, WRAP_CONTENT, FILL_PARENT;
  }
  
  private final Type type; 
  private final int size;
  
  public Dimension(String sizeStr) {
    if (sizeStr.endsWith("%")) {
      type = Type.PERCENTAGE;
      size = Integer.parseInt(sizeStr.substring(0, sizeStr.length() - 1));
    } else if (sizeStr.equalsIgnoreCase("wrap_content")) {
      type = Type.WRAP_CONTENT;
      size = -1;
    } else if (sizeStr.equalsIgnoreCase("fill_parent")) {
      type = Type.FILL_PARENT;
      size = -1;
    } else {
      type = Type.FIXED;
      size = Integer.parseInt(sizeStr);
    }
  }

  public Dimension(int size) {
    type = Type.FIXED;
    this.size = size;
  }
  
  public Type getType() {
    return type;
  }
  
  // TODO: Sign that inheritance should be used instead...
  public int getSize(View parentView) {
    if (parentView == null) {
      return size;
    }
    switch (type) {
      case FIXED:
        return size;
      case PERCENTAGE:
        return size * getParentSize(parentView) / 100;
      case FILL_PARENT:
        return getParentSize(parentView);
      default:
        throw new UnsupportedOperationException("Not supported for type: " + type);
    }
  }
  
  public int getMaxSize(View parentView) {
    if (parentView == null) {
      return size;
    }
    switch (type) {
      case FIXED:
        return size;
      case PERCENTAGE:
        return size * getParentMaxSize(parentView) / 100;
      case FILL_PARENT:
        return getParentMaxSize(parentView);
      default:
        return getParentMaxSize(parentView);
    }    
  }
  
  public abstract int getParentSize(View parentView);

  public abstract int getParentMaxSize(View parentView);
}
