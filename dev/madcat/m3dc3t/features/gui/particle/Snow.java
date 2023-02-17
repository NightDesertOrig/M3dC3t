//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.gui.particle;

import dev.madcat.m3dc3t.util.RenderUtil;
import java.util.Random;
import net.minecraft.client.gui.ScaledResolution;

public class Snow {
  private int _x;
  
  private int _y;
  
  private int _fallingSpeed;
  
  private int _size;
  
  public Snow(int x, int y, int fallingSpeed, int size) {
    this._x = x;
    this._y = y;
    this._fallingSpeed = fallingSpeed;
    this._size = size;
  }
  
  public int getX() {
    return this._x;
  }
  
  public void setX(int x) {
    this._x = x;
  }
  
  public int getY() {
    return this._y;
  }
  
  public void setY(int _y) {
    this._y = _y;
  }
  
  public void Update(ScaledResolution res) {
    RenderUtil.drawRect(getX(), getY(), (getX() + this._size), (getY() + this._size), -1714829883);
    setY(getY() + this._fallingSpeed);
    if (getY() > res.getScaledHeight() + 10 || getY() < -10) {
      setY(-10);
      Random rand = new Random();
      this._fallingSpeed = rand.nextInt(10) + 1;
      this._size = rand.nextInt(4) + 1;
    } 
  }
}
