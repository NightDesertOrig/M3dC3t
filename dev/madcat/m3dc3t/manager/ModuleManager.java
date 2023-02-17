//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.manager;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.madcat.m3dc3t.event.events.Render2DEvent;
import dev.madcat.m3dc3t.event.events.Render3DEvent;
import dev.madcat.m3dc3t.features.Feature;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.modules.client.Chat;
import dev.madcat.m3dc3t.features.modules.client.ClickGui;
import dev.madcat.m3dc3t.features.modules.client.FontMod;
import dev.madcat.m3dc3t.features.modules.client.GUIBlur;
import dev.madcat.m3dc3t.features.modules.client.HUD;
import dev.madcat.m3dc3t.features.modules.client.HitMarkers;
import dev.madcat.m3dc3t.features.modules.client.HudComponents;
import dev.madcat.m3dc3t.features.modules.client.IQBoost;
import dev.madcat.m3dc3t.features.modules.client.ItemStats;
import dev.madcat.m3dc3t.features.modules.client.NickHider;
import dev.madcat.m3dc3t.features.modules.client.Peek;
import dev.madcat.m3dc3t.features.modules.client.TargetHUD;
import dev.madcat.m3dc3t.features.modules.client.Title;
import dev.madcat.m3dc3t.features.modules.combat.AntiBurrow;
import dev.madcat.m3dc3t.features.modules.combat.AntiCev;
import dev.madcat.m3dc3t.features.modules.combat.AntiCity;
import dev.madcat.m3dc3t.features.modules.combat.AntiCrystal;
import dev.madcat.m3dc3t.features.modules.combat.AutoCity;
import dev.madcat.m3dc3t.features.modules.combat.AutoCiv;
import dev.madcat.m3dc3t.features.modules.combat.AutoCrystal;
import dev.madcat.m3dc3t.features.modules.combat.BreakCheck;
import dev.madcat.m3dc3t.features.modules.combat.Burrow;
import dev.madcat.m3dc3t.features.modules.combat.Criticals;
import dev.madcat.m3dc3t.features.modules.combat.FeetPad;
import dev.madcat.m3dc3t.features.modules.combat.Flatten;
import dev.madcat.m3dc3t.features.modules.combat.HoleFiller;
import dev.madcat.m3dc3t.features.modules.combat.InstantMine;
import dev.madcat.m3dc3t.features.modules.combat.KillAura;
import dev.madcat.m3dc3t.features.modules.combat.NewSurround;
import dev.madcat.m3dc3t.features.modules.combat.Offhand;
import dev.madcat.m3dc3t.features.modules.combat.OldAntiCity;
import dev.madcat.m3dc3t.features.modules.combat.PistonKicker;
import dev.madcat.m3dc3t.features.modules.combat.Quiver;
import dev.madcat.m3dc3t.features.modules.combat.SmartTrap;
import dev.madcat.m3dc3t.features.modules.combat.Surround;
import dev.madcat.m3dc3t.features.modules.combat.TrapSelf;
import dev.madcat.m3dc3t.features.modules.misc.AntiBookBan;
import dev.madcat.m3dc3t.features.modules.misc.AntiPackets;
import dev.madcat.m3dc3t.features.modules.misc.ArmorWarner;
import dev.madcat.m3dc3t.features.modules.misc.AutoDupe;
import dev.madcat.m3dc3t.features.modules.misc.AutoLog;
import dev.madcat.m3dc3t.features.modules.misc.AutoRejoin;
import dev.madcat.m3dc3t.features.modules.misc.Bloom;
import dev.madcat.m3dc3t.features.modules.misc.ChatQueue;
import dev.madcat.m3dc3t.features.modules.misc.ChatSuffix;
import dev.madcat.m3dc3t.features.modules.misc.Crasher;
import dev.madcat.m3dc3t.features.modules.misc.FakePlayer;
import dev.madcat.m3dc3t.features.modules.misc.MCF;
import dev.madcat.m3dc3t.features.modules.misc.MCP;
import dev.madcat.m3dc3t.features.modules.misc.Message;
import dev.madcat.m3dc3t.features.modules.misc.Nuker;
import dev.madcat.m3dc3t.features.modules.misc.PearlNotify;
import dev.madcat.m3dc3t.features.modules.misc.PopCounter;
import dev.madcat.m3dc3t.features.modules.misc.Respawn;
import dev.madcat.m3dc3t.features.modules.movement.Anchor;
import dev.madcat.m3dc3t.features.modules.movement.AntiLevitate;
import dev.madcat.m3dc3t.features.modules.movement.AntiVoid;
import dev.madcat.m3dc3t.features.modules.movement.AntiWeb;
import dev.madcat.m3dc3t.features.modules.movement.AutoJump;
import dev.madcat.m3dc3t.features.modules.movement.BlockFly;
import dev.madcat.m3dc3t.features.modules.movement.BoatFly;
import dev.madcat.m3dc3t.features.modules.movement.Flight;
import dev.madcat.m3dc3t.features.modules.movement.HoleSnap;
import dev.madcat.m3dc3t.features.modules.movement.InventoryMove;
import dev.madcat.m3dc3t.features.modules.movement.NoSlow;
import dev.madcat.m3dc3t.features.modules.movement.ReverseStep;
import dev.madcat.m3dc3t.features.modules.movement.Sprint;
import dev.madcat.m3dc3t.features.modules.movement.StairSpeed;
import dev.madcat.m3dc3t.features.modules.movement.Step;
import dev.madcat.m3dc3t.features.modules.movement.Strafe;
import dev.madcat.m3dc3t.features.modules.movement.TestPhase;
import dev.madcat.m3dc3t.features.modules.movement.TickShift;
import dev.madcat.m3dc3t.features.modules.movement.Timers;
import dev.madcat.m3dc3t.features.modules.movement.Velocity;
import dev.madcat.m3dc3t.features.modules.player.AntiPistonKick;
import dev.madcat.m3dc3t.features.modules.player.AutoArmor;
import dev.madcat.m3dc3t.features.modules.player.BetterPortal;
import dev.madcat.m3dc3t.features.modules.player.Blink;
import dev.madcat.m3dc3t.features.modules.player.ChestStealer;
import dev.madcat.m3dc3t.features.modules.player.FastElytra;
import dev.madcat.m3dc3t.features.modules.player.FastPlace;
import dev.madcat.m3dc3t.features.modules.player.Interact;
import dev.madcat.m3dc3t.features.modules.player.MultiTask;
import dev.madcat.m3dc3t.features.modules.player.NoFall;
import dev.madcat.m3dc3t.features.modules.player.NoRotate;
import dev.madcat.m3dc3t.features.modules.player.PacketEat;
import dev.madcat.m3dc3t.features.modules.player.PacketXP;
import dev.madcat.m3dc3t.features.modules.player.PhaseTrace;
import dev.madcat.m3dc3t.features.modules.player.Reach;
import dev.madcat.m3dc3t.features.modules.player.Replenish;
import dev.madcat.m3dc3t.features.modules.player.Speedmine;
import dev.madcat.m3dc3t.features.modules.player.XCarry;
import dev.madcat.m3dc3t.features.modules.render.Animations;
import dev.madcat.m3dc3t.features.modules.render.ArrowESP;
import dev.madcat.m3dc3t.features.modules.render.BreakESP;
import dev.madcat.m3dc3t.features.modules.render.CameraClip;
import dev.madcat.m3dc3t.features.modules.render.Chams;
import dev.madcat.m3dc3t.features.modules.render.CityESP;
import dev.madcat.m3dc3t.features.modules.render.CrystalScale;
import dev.madcat.m3dc3t.features.modules.render.FullBright;
import dev.madcat.m3dc3t.features.modules.render.HoleESP;
import dev.madcat.m3dc3t.features.modules.render.LogESP;
import dev.madcat.m3dc3t.features.modules.render.NameTags;
import dev.madcat.m3dc3t.features.modules.render.NoRender;
import dev.madcat.m3dc3t.features.modules.render.PopChams;
import dev.madcat.m3dc3t.features.modules.render.Ranges;
import dev.madcat.m3dc3t.features.modules.render.Shaders;
import dev.madcat.m3dc3t.features.modules.render.SkyColor;
import dev.madcat.m3dc3t.features.modules.render.StorageESP;
import dev.madcat.m3dc3t.features.modules.render.Tracer;
import dev.madcat.m3dc3t.features.modules.render.Trajectories;
import dev.madcat.m3dc3t.features.modules.render.ViewModel;
import dev.madcat.m3dc3t.features.modules.render.WorldModify;
import dev.madcat.m3dc3t.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

public class ModuleManager extends Feature {
  public static ArrayList<Module> modules = new ArrayList<>();
  
  public List<Module> sortedModules = new ArrayList<>();
  
  public List<String> sortedModulesABC = new ArrayList<>();
  
  public Animation animationThread;
  
  public static ArrayList<Module> nigger;
  
  public void init() {
    modules.add(new ClickGui());
    modules.add(new FontMod());
    modules.add(new PopCounter());
    modules.add(new AntiWeb());
    modules.add(new GUIBlur());
    modules.add(new HUD());
    modules.add(new Criticals());
    modules.add(new WorldModify());
    modules.add(new AutoRejoin());
    modules.add(new Message());
    modules.add(new KillAura());
    modules.add(new Crasher());
    modules.add(new Peek());
    modules.add(new PhaseTrace());
    modules.add(new SmartTrap());
    modules.add(new CameraClip());
    modules.add(new NoRotate());
    modules.add(new Trajectories());
    modules.add(new TargetHUD());
    modules.add(new FullBright());
    modules.add(new Offhand());
    modules.add(new ArrowESP());
    modules.add(new NickHider());
    modules.add(new Respawn());
    modules.add(new CityESP());
    modules.add(new Shaders());
    modules.add(new Nuker());
    modules.add(new Timers());
    modules.add(new NameTags());
    modules.add(new AntiVoid());
    modules.add(new CrystalScale());
    modules.add(new NoSlow());
    modules.add(new BreakESP());
    modules.add(new Velocity());
    modules.add(new Sprint());
    modules.add(new AntiCrystal());
    modules.add(new ArmorWarner());
    modules.add(new Quiver());
    modules.add(new BlockFly());
    modules.add(new Animations());
    modules.add(new AntiBookBan());
    modules.add(new HoleSnap());
    modules.add(new ReverseStep());
    modules.add(new AntiLevitate());
    modules.add(new LogESP());
    modules.add(new SkyColor());
    modules.add(new HudComponents());
    modules.add(new BetterPortal());
    modules.add(new AutoDupe());
    modules.add(new ViewModel());
    modules.add(new Tracer());
    modules.add(new Speedmine());
    modules.add(new AutoLog());
    modules.add(new FastPlace());
    modules.add(new InventoryMove());
    modules.add(new MultiTask());
    modules.add(new Surround());
    modules.add(new StairSpeed());
    modules.add(new NoFall());
    modules.add(new PearlNotify());
    modules.add(new Blink());
    modules.add(new Anchor());
    modules.add(new Interact());
    modules.add(new OldAntiCity());
    modules.add(new StorageESP());
    modules.add(new NewSurround());
    modules.add(new Flight());
    modules.add(new IQBoost());
    modules.add(new Title());
    modules.add(new TestPhase());
    modules.add(new AntiPackets());
    modules.add(new PistonKicker());
    modules.add(new Strafe());
    modules.add(new Chat());
    modules.add(new Burrow());
    modules.add(new AutoArmor());
    modules.add(new AutoCrystal());
    modules.add(new ItemStats());
    modules.add(new AutoCity());
    modules.add(new BoatFly());
    modules.add(new Chams());
    modules.add(new ChestStealer());
    modules.add(new BreakCheck());
    modules.add(new Bloom());
    modules.add(new FeetPad());
    modules.add(new HitMarkers());
    modules.add(new HoleESP());
    modules.add(new Ranges());
    modules.add(new PopChams());
    modules.add(new NoRender());
    modules.add(new AntiCity());
    modules.add(new InstantMine());
    modules.add(new AntiBurrow());
    modules.add(new AntiCev());
    modules.add(new AutoCiv());
    modules.add(new TrapSelf());
    modules.add(new AntiPistonKick());
    modules.add(new HoleFiller());
    modules.add(new Flatten());
    modules.add(new Replenish());
    modules.add(new FakePlayer());
    modules.add(new Reach());
    modules.add(new FastElytra());
    modules.add(new MCP());
    modules.add(new PacketEat());
    modules.add(new MCF());
    modules.add(new ChatQueue());
    modules.add(new PacketXP());
    modules.add(new XCarry());
    modules.add(new TickShift());
    modules.add(new AutoJump());
    modules.add(new Step());
    modules.add(new ChatSuffix());
  }
  
  public static Module getModuleByName(String name) {
    for (Module module : modules) {
      if (!module.getName().equalsIgnoreCase(name))
        continue; 
      return module;
    } 
    return null;
  }
  
  public static <T extends Module> T getModuleByClass(Class<T> clazz) {
    for (Module module : modules) {
      if (!clazz.isInstance(module))
        continue; 
      return (T)module;
    } 
    return null;
  }
  
  public void enableModule(Class<Module> clazz) {
    Module module = getModuleByClass(clazz);
    if (module != null)
      module.enable(); 
  }
  
  public void disableModule(Class<Module> clazz) {
    Module module = getModuleByClass(clazz);
    if (module != null)
      module.disable(); 
  }
  
  public void enableModule(String name) {
    Module module = getModuleByName(name);
    if (module != null)
      module.enable(); 
  }
  
  public void disableModule(String name) {
    Module module = getModuleByName(name);
    if (module != null)
      module.disable(); 
  }
  
  public boolean isModuleEnabled(String name) {
    Module module = getModuleByName(name);
    return (module != null && module.isOn());
  }
  
  public boolean isModuleEnabled(Class<Module> clazz) {
    Module module = getModuleByClass(clazz);
    return (module != null && module.isOn());
  }
  
  public Module getModuleByDisplayName(String displayName) {
    for (Module module : modules) {
      if (!module.getDisplayName().equalsIgnoreCase(displayName))
        continue; 
      return module;
    } 
    return null;
  }
  
  public ArrayList<Module> getEnabledModules() {
    ArrayList<Module> enabledModules = new ArrayList<>();
    for (Module module : modules) {
      if (!module.isEnabled())
        continue; 
      enabledModules.add(module);
    } 
    return enabledModules;
  }
  
  public ArrayList<String> getEnabledModulesName() {
    ArrayList<String> enabledModules = new ArrayList<>();
    for (Module module : modules) {
      if (!module.isEnabled() || !module.isDrawn())
        continue; 
      enabledModules.add(module.getFullArrayString());
    } 
    return enabledModules;
  }
  
  public ArrayList<Module> getModulesByCategory(Module.Category category) {
    ArrayList<Module> modulesCategory = new ArrayList<>();
    modules.forEach(module -> {
          if (module.getCategory() == category)
            modulesCategory.add(module); 
        });
    return modulesCategory;
  }
  
  public List<Module.Category> getCategories() {
    return Arrays.asList(Module.Category.values());
  }
  
  public void onLoad() {
    modules.stream().filter(Module::listening).forEach(MinecraftForge.EVENT_BUS::register);
    modules.forEach(Module::onLoad);
  }
  
  public void onUpdate() {
    modules.stream().filter(Feature::isEnabled).forEach(Module::onUpdate);
  }
  
  public void onTick() {
    modules.stream().filter(Feature::isEnabled).forEach(Module::onTick);
  }
  
  public void onRender2D(Render2DEvent event) {
    modules.stream().filter(Feature::isEnabled).forEach(module -> module.onRender2D(event));
  }
  
  public void onRender3D(Render3DEvent event) {
    modules.stream().filter(Feature::isEnabled).forEach(module -> module.onRender3D(event));
  }
  
  public <T extends Module> T getModuleT(Class<T> clazz) {
    return (T)modules.stream().filter(module -> (module.getClass() == clazz)).map(module -> module).findFirst().orElse(null);
  }
  
  public void sortModules(boolean reverse) {
    this.sortedModules = (List<Module>)getEnabledModules().stream().filter(Module::isDrawn).sorted(Comparator.comparing(module -> Integer.valueOf(this.renderer.getStringWidth(module.getFullArrayString()) * (reverse ? -1 : 1)))).collect(Collectors.toList());
  }
  
  public void sortModulesABC() {
    this.sortedModulesABC = new ArrayList<>(getEnabledModulesName());
    this.sortedModulesABC.sort(String.CASE_INSENSITIVE_ORDER);
  }
  
  public void onLogout() {
    modules.forEach(Module::onLogout);
  }
  
  public void onLogin() {
    modules.forEach(Module::onLogin);
  }
  
  public void onUnload() {
    modules.forEach(MinecraftForge.EVENT_BUS::unregister);
    modules.forEach(Module::onUnload);
  }
  
  public void onUnloadPost() {
    for (Module module : modules)
      module.enabled.setValue(Boolean.valueOf(false)); 
  }
  
  public void onKeyPressed(int eventKey) {
    if (eventKey == 0 || !Keyboard.getEventKeyState() || mc.currentScreen instanceof dev.madcat.m3dc3t.features.gui.M3dC3tGui)
      return; 
    modules.forEach(module -> {
          if (module.getBind().getKey() == eventKey)
            module.toggle(); 
        });
  }
  
  private class Animation extends Thread {
    public Module module;
    
    public float offset;
    
    public float vOffset;
    
    ScheduledExecutorService service;
    
    public Animation() {
      super("Animation");
      this.service = Executors.newSingleThreadScheduledExecutor();
    }
    
    public void run() {
      if ((HUD.getInstance()).renderingMode.getValue() == HUD.RenderingMode.Length) {
        for (Module module : ModuleManager.this.sortedModules) {
          String text = module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "");
          module.offset = ModuleManager.this.renderer.getStringWidth(text);
          module.vOffset = ModuleManager.this.renderer.getFontHeight();
          if (module.isEnabled()) {
            if (module.arrayListOffset <= module.offset || Util.mc.world == null)
              continue; 
            module.arrayListOffset -= module.offset;
            module.sliding = true;
            continue;
          } 
          if (!module.isDisabled())
            continue; 
          if (module.arrayListOffset < ModuleManager.this.renderer.getStringWidth(text) && Util.mc.world != null) {
            module.arrayListOffset += module.offset;
            module.sliding = true;
            continue;
          } 
          module.sliding = false;
        } 
      } else {
        for (String e : ModuleManager.this.sortedModulesABC) {
          Module module = ModuleManager.getModuleByName(e);
          String text = module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "");
          module.offset = ModuleManager.this.renderer.getStringWidth(text);
          module.vOffset = ModuleManager.this.renderer.getFontHeight();
          if (module.isEnabled()) {
            if (module.arrayListOffset <= module.offset || Util.mc.world == null)
              continue; 
            module.arrayListOffset -= module.offset;
            module.sliding = true;
            continue;
          } 
          if (!module.isDisabled())
            continue; 
          if (module.arrayListOffset < ModuleManager.this.renderer.getStringWidth(text) && Util.mc.world != null) {
            module.arrayListOffset += module.offset;
            module.sliding = true;
            continue;
          } 
          module.sliding = false;
        } 
      } 
    }
    
    public void start() {
      System.out.println("Starting animation thread.");
      this.service.scheduleAtFixedRate(this, 0L, 1L, TimeUnit.MILLISECONDS);
    }
  }
  
  public static ArrayList<Module> getModules() {
    return nigger;
  }
  
  public static boolean isModuleEnablednigger(String name) {
    Module modulenigger = getModules().stream().filter(mm -> mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    return modulenigger.isEnabled();
  }
  
  public static boolean isModuleEnablednigger(Module modulenigger) {
    return modulenigger.isEnabled();
  }
}
