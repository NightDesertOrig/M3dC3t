package dev.madcat.m3dc3t.event.events;

import dev.madcat.m3dc3t.event.EventStage;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class NoRenderEvent extends EventStage {
  public NoRenderEvent(int a) {
    super(a);
  }
}
