package dev.madcat.m3dc3t.util;

import java.awt.Color;

public class Rainbow {
  public static Color getColour() {
    return Colour.fromHSB((float)(System.currentTimeMillis() % 11520L) / 11520.0F, 1.0F, 1.0F);
  }
}
