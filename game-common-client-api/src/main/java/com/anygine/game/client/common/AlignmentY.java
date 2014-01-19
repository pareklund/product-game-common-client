package com.anygine.game.client.common;

public class AlignmentY {

  private static enum Type {
    T_FIXED, T_PERCENTAGE, T_TOP, T_BOTTOM, T_CENTER; 
  }

  public static final AlignmentY TOP = new AlignmentY(Type.T_TOP);
  public static final AlignmentY CENTER = new AlignmentY(Type.T_CENTER);
  public static final AlignmentY BOTTOM = new AlignmentY(Type.T_BOTTOM);
  
  protected final Type type;
  protected final int offset;

  public static AlignmentY getAlignment(String alignStr) {
    if (alignStr == null) {
      return TOP;
    } else if (alignStr.endsWith("%")) {
      return new AlignmentY(
          Type.T_PERCENTAGE, 
          Integer.parseInt(alignStr.substring(0, alignStr.length() - 1)));
    } else if (alignStr.equalsIgnoreCase("top")) {
      return TOP;
    } else if (alignStr.equalsIgnoreCase("bottom")) {
      return BOTTOM;
    } else if (alignStr.equalsIgnoreCase("center")) {
      return CENTER;
    } else {
      return new AlignmentY(Type.T_FIXED, Integer.parseInt(alignStr));
    }
  }

  private AlignmentY(Type type, int offset) {
    this.type = type;
    this.offset = offset;
  }
 
  private AlignmentY(Type type) {
    this.type = type;
    offset = -1;
  }

  public float getOffset(
      float renderHeight, float contentHeight, float currentOffset) {
    if (type == Type.T_TOP) {
      return currentOffset;
    } else if (type == Type.T_CENTER) {
      return currentOffset + (renderHeight - contentHeight) / 2;
    } else if (type == Type.T_BOTTOM) {
      return currentOffset + renderHeight - contentHeight;
    } else if (type == Type.T_PERCENTAGE) {
      return currentOffset + (renderHeight - contentHeight) * offset / 100;
    } else {
      return currentOffset + offset;
    }
  }

}
