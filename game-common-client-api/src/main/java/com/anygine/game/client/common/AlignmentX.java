package com.anygine.game.client.common;

public class AlignmentX {

  private static enum Type {
    T_FIXED, T_PERCENTAGE, T_LEFT, T_RIGHT, T_CENTER; 
  }

  public static final AlignmentX LEFT = new AlignmentX(Type.T_LEFT);
  public static final AlignmentX CENTER = new AlignmentX(Type.T_CENTER);
  public static final AlignmentX RIGHT = new AlignmentX(Type.T_RIGHT);
  
  protected final Type type;
  protected final int offset;

  public static AlignmentX getAlignment(String alignStr) {
    if (alignStr == null) {
      return LEFT;
    } else if (alignStr.endsWith("%")) {
      return new AlignmentX(
          Type.T_PERCENTAGE, 
          Integer.parseInt(alignStr.substring(0, alignStr.length() - 1)));
    } else if (alignStr.equalsIgnoreCase("left")) {
      return LEFT;
    } else if (alignStr.equalsIgnoreCase("right")) {
      return RIGHT;
    } else if (alignStr.equalsIgnoreCase("center")) {
      return CENTER;
    } else {
      return new AlignmentX(Type.T_FIXED, Integer.parseInt(alignStr));
    }
  }

  private AlignmentX(Type type, int offset) {
    this.type = type;
    this.offset = offset;
  }
 
  private AlignmentX(Type type) {
    this.type = type;
    offset = -1;
  }

  public float getOffset(
      float renderWidth, float contentWidth, float currentOffset) {
    if (type == Type.T_LEFT) {
      return currentOffset;
    } else if (type == Type.T_CENTER) {
      return currentOffset + (renderWidth - contentWidth) / 2;
    } else if (type == Type.T_RIGHT) {
      return currentOffset + renderWidth - contentWidth;
    } else if (type == Type.T_PERCENTAGE) {
      return currentOffset + (renderWidth - contentWidth) * offset / 100;
    } else {
      return currentOffset + offset;
    }
  }

}
