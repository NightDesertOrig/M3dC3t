package dev.madcat.m3dc3t.util;

import java.awt.Color;

public class DonatorItem implements Globals {
  private final String name;
  
  private final int size;
  
  private double x;
  
  private double y;
  
  private final double width;
  
  private final double height;
  
  private final int canvasWidth;
  
  private final int canvasHeight;
  
  private final Color colour;
  
  private double xSpeed;
  
  private double ySpeed;
  
  public DonatorItem(String name, int size, int width, int height, int canvasWidth, int canvasHeight) {
    this.name = name;
    this.size = size;
    this.x = MathsUtil.random(20, canvasWidth - 20);
    this.y = MathsUtil.random(20, canvasHeight - 20);
    this.canvasWidth = canvasWidth;
    this.canvasHeight = canvasHeight;
    this.width = width;
    this.height = height;
    this.colour = new Color((int)(Math.random() * 1.6777216E7D));
    this.xSpeed = offsetStart(0.1D);
    this.ySpeed = offsetStart(0.1D);
  }
  
  public void updatePos() {
    if (this.x + this.width >= this.canvasWidth || this.x <= 0.0D)
      this.xSpeed *= -1.0D; 
    if (this.y + this.height >= this.canvasHeight || this.y <= 0.0D)
      this.ySpeed *= -1.0D; 
    this.x += this.xSpeed;
    this.y += this.ySpeed;
  }
  
  public double getX() {
    return this.x;
  }
  
  public double getY() {
    return this.y;
  }
  
  public String getName() {
    return this.name;
  }
  
  public int getSize() {
    return this.size;
  }
  
  public int getRgb() {
    return this.colour.getRGB();
  }
  
  private double offsetStart(double i) {
    if (random.nextInt(2) == 0)
      return -i; 
    return i;
  }
}
