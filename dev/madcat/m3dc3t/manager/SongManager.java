//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.manager;

import dev.madcat.m3dc3t.util.Globals;
import dev.madcat.m3dc3t.util.songs.DontStop;
import dev.madcat.m3dc3t.util.songs.FireBall;
import dev.madcat.m3dc3t.util.songs.HotelRoom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.audio.ISound;

public class SongManager implements Globals {
  private final List<ISound> songs = Arrays.asList(new ISound[] { FireBall.sound, HotelRoom.sound, DontStop.sound });
  
  private final ISound menuSong;
  
  private ISound currentSong;
  
  public SongManager() {
    this.menuSong = getRandomSong();
    this.currentSong = getRandomSong();
  }
  
  public ISound getMenuSong() {
    return this.menuSong;
  }
  
  public void skip() {
    boolean flag = isCurrentSongPlaying();
    if (flag)
      stop(); 
    this.currentSong = this.songs.get((this.songs.indexOf(this.currentSong) + 1) % this.songs.size());
    if (flag)
      play(); 
  }
  
  public void play() {
    if (!isCurrentSongPlaying())
      mc.soundHandler.playSound(this.currentSong); 
  }
  
  public void stop() {
    if (isCurrentSongPlaying())
      mc.soundHandler.stopSound(this.currentSong); 
  }
  
  private boolean isCurrentSongPlaying() {
    return mc.soundHandler.isSoundPlaying(this.currentSong);
  }
  
  public void shuffle() {
    stop();
    Collections.shuffle(this.songs);
  }
  
  private ISound getRandomSong() {
    return this.songs.get(random.nextInt(this.songs.size()));
  }
}
