//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.combat;

import com.mojang.authlib.GameProfile;
import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.event.events.ClientEvent;
import dev.madcat.m3dc3t.event.events.PacketEvent;
import dev.madcat.m3dc3t.event.events.Render3DEvent;
import dev.madcat.m3dc3t.event.events.UpdateWalkingPlayerEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Bind;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.BlockUtil;
import dev.madcat.m3dc3t.util.DamageUtil;
import dev.madcat.m3dc3t.util.EntityUtil;
import dev.madcat.m3dc3t.util.InventoryUtil;
import dev.madcat.m3dc3t.util.MathUtil;
import dev.madcat.m3dc3t.util.yes.RenderUtil;
import dev.madcat.m3dc3t.util.yes.Timer;
import io.netty.util.internal.ConcurrentSet;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class AutoCrystal extends Module {
  public static EntityPlayer target;
  
  public static Set<BlockPos> lowDmgPos = (Set<BlockPos>)new ConcurrentSet();
  
  public static Set<BlockPos> placedPos = new HashSet<>();
  
  public static Set<BlockPos> BrokenPos = new HashSet<>();
  
  private static AutoCrystal instance;
  
  private final Timer switchTimer = new Timer();
  
  private final Timer manualTimer = new Timer();
  
  private final Timer breakTimer = new Timer();
  
  private final Timer placeTimer = new Timer();
  
  private final Timer syncTimer = new Timer();
  
  private final Timer predictTimer = new Timer();
  
  private final Timer renderTimer = new Timer();
  
  private final AtomicBoolean shouldInterrupt = new AtomicBoolean(false);
  
  private final Timer syncroTimer = new Timer();
  
  private final Map<EntityPlayer, Timer> totemPops = new ConcurrentHashMap<>();
  
  private final Queue<CPacketUseEntity> packetUseEntities = new LinkedList<>();
  
  private final AtomicBoolean threadOngoing = new AtomicBoolean(false);
  
  private final List<RenderPos> positions = new ArrayList<>();
  
  private final Setting<Settings> setting = register(new Setting("Settings", Settings.PLACE));
  
  public Setting<Boolean> place = register(new Setting("Place", Boolean.TRUE, v -> (this.setting.getValue() == Settings.PLACE)));
  
  public Setting<Integer> placeDelay = register(new Setting("PlaceDelay", Integer.valueOf(25), Integer.valueOf(0), Integer.valueOf(500), v -> (this.setting.getValue() == Settings.PLACE && ((Boolean)this.place.getValue()).booleanValue())));
  
  public Setting<Float> placeRange = register(new Setting("PlaceRange", Float.valueOf(6.0F), Float.valueOf(0.0F), Float.valueOf(10.0F), v -> (this.setting.getValue() == Settings.PLACE && ((Boolean)this.place.getValue()).booleanValue())));
  
  public Setting<Float> minDamage = register(new Setting("MinDamage", Float.valueOf(7.0F), Float.valueOf(0.1F), Float.valueOf(20.0F), v -> (this.setting.getValue() == Settings.PLACE && ((Boolean)this.place.getValue()).booleanValue())));
  
  public Setting<Float> maxSelfPlace = register(new Setting("MaxSelfPlace", Float.valueOf(10.0F), Float.valueOf(0.1F), Float.valueOf(36.0F), v -> (this.setting.getValue() == Settings.PLACE && ((Boolean)this.place.getValue()).booleanValue())));
  
  public Setting<Integer> wasteAmount = register(new Setting("WasteAmount", Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(5), v -> (this.setting.getValue() == Settings.PLACE && ((Boolean)this.place.getValue()).booleanValue())));
  
  public Setting<Boolean> wasteMinDmgCount = register(new Setting("CountMinDmg", Boolean.TRUE, v -> (this.setting.getValue() == Settings.PLACE && ((Boolean)this.place.getValue()).booleanValue())));
  
  public Setting<Float> facePlace = register(new Setting("FacePlace", Float.valueOf(8.0F), Float.valueOf(0.1F), Float.valueOf(36.0F), v -> (this.setting.getValue() == Settings.PLACE && ((Boolean)this.place.getValue()).booleanValue())));
  
  public Setting<Float> placetrace = register(new Setting("Placetrace", Float.valueOf(4.5F), Float.valueOf(0.0F), Float.valueOf(10.0F), v -> (this.setting.getValue() == Settings.PLACE && ((Boolean)this.place.getValue()).booleanValue() && this.raytrace.getValue() != Raytrace.NONE && this.raytrace.getValue() != Raytrace.BREAK)));
  
  public Setting<Boolean> antiSurround = register(new Setting("AntiSurround", Boolean.TRUE, v -> (this.setting.getValue() == Settings.PLACE && ((Boolean)this.place.getValue()).booleanValue())));
  
  public Setting<Boolean> limitFacePlace = register(new Setting("LimitFacePlace", Boolean.TRUE, v -> (this.setting.getValue() == Settings.PLACE && ((Boolean)this.place.getValue()).booleanValue())));
  
  public Setting<Boolean> oneDot15 = register(new Setting("1.15", Boolean.FALSE, v -> (this.setting.getValue() == Settings.PLACE && ((Boolean)this.place.getValue()).booleanValue())));
  
  public Setting<Boolean> doublePop = register(new Setting("AntiTotem", Boolean.FALSE, v -> (this.setting.getValue() == Settings.PLACE && ((Boolean)this.place.getValue()).booleanValue())));
  
  public Setting<Double> popHealth = register(new Setting("PopHealth", Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(3.0D), v -> (this.setting.getValue() == Settings.PLACE && ((Boolean)this.place.getValue()).booleanValue() && ((Boolean)this.doublePop.getValue()).booleanValue())));
  
  public Setting<Float> popDamage = register(new Setting("PopDamage", Float.valueOf(4.0F), Float.valueOf(0.0F), Float.valueOf(6.0F), v -> (this.setting.getValue() == Settings.PLACE && ((Boolean)this.place.getValue()).booleanValue() && ((Boolean)this.doublePop.getValue()).booleanValue())));
  
  public Setting<Integer> popTime = register(new Setting("PopTime", Integer.valueOf(500), Integer.valueOf(0), Integer.valueOf(1000), v -> (this.setting.getValue() == Settings.PLACE && ((Boolean)this.place.getValue()).booleanValue() && ((Boolean)this.doublePop.getValue()).booleanValue())));
  
  public Setting<Boolean> doublePopOnDamage = register(new Setting("DamagePop", Boolean.FALSE, v -> (this.setting.getValue() == Settings.PLACE && ((Boolean)this.place.getValue()).booleanValue() && ((Boolean)this.doublePop.getValue()).booleanValue() && this.targetMode.getValue() == Target.DAMAGE)));
  
  public Setting<Boolean> explode = register(new Setting("Break", Boolean.TRUE, v -> (this.setting.getValue() == Settings.BREAK)));
  
  public Setting<Switch> switchMode = register(new Setting("Attack", Switch.BREAKSLOT, v -> (this.setting.getValue() == Settings.BREAK && ((Boolean)this.explode.getValue()).booleanValue())));
  
  public Setting<Integer> breakDelay = register(new Setting("BreakDelay", Integer.valueOf(50), Integer.valueOf(0), Integer.valueOf(500), v -> (this.setting.getValue() == Settings.BREAK && ((Boolean)this.explode.getValue()).booleanValue())));
  
  public Setting<Float> breakRange = register(new Setting("BreakRange", Float.valueOf(6.0F), Float.valueOf(0.0F), Float.valueOf(10.0F), v -> (this.setting.getValue() == Settings.BREAK && ((Boolean)this.explode.getValue()).booleanValue())));
  
  public Setting<Integer> packets = register(new Setting("Packets", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(6), v -> (this.setting.getValue() == Settings.BREAK && ((Boolean)this.explode.getValue()).booleanValue())));
  
  public Setting<Float> maxSelfBreak = register(new Setting("MaxSelfBreak", Float.valueOf(10.0F), Float.valueOf(0.1F), Float.valueOf(36.0F), v -> (this.setting.getValue() == Settings.BREAK && ((Boolean)this.explode.getValue()).booleanValue())));
  
  public Setting<Float> breaktrace = register(new Setting("Breaktrace", Float.valueOf(4.5F), Float.valueOf(0.0F), Float.valueOf(10.0F), v -> (this.setting.getValue() == Settings.BREAK && ((Boolean)this.explode.getValue()).booleanValue() && this.raytrace.getValue() != Raytrace.NONE && this.raytrace.getValue() != Raytrace.PLACE)));
  
  public Setting<Boolean> manual = register(new Setting("Manual", Boolean.TRUE, v -> (this.setting.getValue() == Settings.BREAK)));
  
  public Setting<Boolean> manualMinDmg = register(new Setting("ManMinDmg", Boolean.TRUE, v -> (this.setting.getValue() == Settings.BREAK && ((Boolean)this.manual.getValue()).booleanValue())));
  
  public Setting<Integer> manualBreak = register(new Setting("ManualDelay", Integer.valueOf(500), Integer.valueOf(0), Integer.valueOf(500), v -> (this.setting.getValue() == Settings.BREAK && ((Boolean)this.manual.getValue()).booleanValue())));
  
  public Setting<Boolean> sync = register(new Setting("Sync", Boolean.TRUE, v -> (this.setting.getValue() == Settings.BREAK && (((Boolean)this.explode.getValue()).booleanValue() || ((Boolean)this.manual.getValue()).booleanValue()))));
  
  public Setting<Boolean> instant = register(new Setting("Predict", Boolean.TRUE, v -> (this.setting.getValue() == Settings.BREAK && ((Boolean)this.explode.getValue()).booleanValue() && ((Boolean)this.place.getValue()).booleanValue())));
  
  public Setting<PredictTimer> instantTimer = register(new Setting("PredictTimer", PredictTimer.NONE, v -> (this.setting.getValue() == Settings.BREAK && ((Boolean)this.explode.getValue()).booleanValue() && ((Boolean)this.place.getValue()).booleanValue() && ((Boolean)this.instant.getValue()).booleanValue())));
  
  public Setting<Boolean> resetBreakTimer = register(new Setting("ResetBreakTimer", Boolean.TRUE, v -> (this.setting.getValue() == Settings.BREAK && ((Boolean)this.explode.getValue()).booleanValue() && ((Boolean)this.place.getValue()).booleanValue() && ((Boolean)this.instant.getValue()).booleanValue())));
  
  public Setting<Integer> predictDelay = register(new Setting("PredictDelay", Integer.valueOf(12), Integer.valueOf(0), Integer.valueOf(500), v -> (this.setting.getValue() == Settings.BREAK && ((Boolean)this.explode.getValue()).booleanValue() && ((Boolean)this.place.getValue()).booleanValue() && ((Boolean)this.instant.getValue()).booleanValue() && this.instantTimer.getValue() == PredictTimer.PREDICT)));
  
  public Setting<Boolean> predictCalc = register(new Setting("PredictCalc", Boolean.TRUE, v -> (this.setting.getValue() == Settings.BREAK && ((Boolean)this.explode.getValue()).booleanValue() && ((Boolean)this.place.getValue()).booleanValue() && ((Boolean)this.instant.getValue()).booleanValue())));
  
  public Setting<Boolean> superSafe = register(new Setting("SuperSafe", Boolean.TRUE, v -> (this.setting.getValue() == Settings.BREAK && ((Boolean)this.explode.getValue()).booleanValue() && ((Boolean)this.place.getValue()).booleanValue() && ((Boolean)this.instant.getValue()).booleanValue())));
  
  public Setting<Boolean> antiCommit = register(new Setting("AntiOverCommit", Boolean.TRUE, v -> (this.setting.getValue() == Settings.BREAK && ((Boolean)this.explode.getValue()).booleanValue() && ((Boolean)this.place.getValue()).booleanValue() && ((Boolean)this.instant.getValue()).booleanValue())));
  
  public Setting<Boolean> render = register(new Setting("Render", Boolean.valueOf(true), v -> (this.setting.getValue() == Settings.RENDER)));
  
  public Setting<Boolean> justRender = register(new Setting("JustRender", Boolean.valueOf(false), v -> (this.setting.getValue() == Settings.RENDER && ((Boolean)this.render.getValue()).booleanValue())));
  
  public Setting<RenderMode> renderMode = register(new Setting("Mode", RenderMode.STATIC, v -> (this.setting.getValue() == Settings.RENDER && ((Boolean)this.render.getValue()).booleanValue())));
  
  private final Setting<Boolean> fadeFactor = register(new Setting("Fade", Boolean.valueOf(true), v -> (this.setting.getValue() == Settings.RENDER && this.renderMode.getValue() == RenderMode.FADE && ((Boolean)this.render.getValue()).booleanValue())));
  
  private final Setting<Boolean> scaleFactor = register(new Setting("Shrink", Boolean.valueOf(false), v -> (this.setting.getValue() == Settings.RENDER && this.renderMode.getValue() == RenderMode.FADE && ((Boolean)this.render.getValue()).booleanValue())));
  
  private final Setting<Boolean> slabFactor = register(new Setting("Slab", Boolean.valueOf(false), v -> (this.setting.getValue() == Settings.RENDER && this.renderMode.getValue() == RenderMode.FADE && ((Boolean)this.render.getValue()).booleanValue())));
  
  private final Setting<Boolean> onlyplaced = register(new Setting("OnlyPlaced", Boolean.valueOf(false), v -> (this.setting.getValue() == Settings.RENDER && this.renderMode.getValue() == RenderMode.FADE && ((Boolean)this.render.getValue()).booleanValue())));
  
  private final Setting<Float> duration = register(new Setting("Duration", Float.valueOf(1500.0F), Float.valueOf(0.0F), Float.valueOf(5000.0F), v -> (this.setting.getValue() == Settings.RENDER && this.renderMode.getValue() == RenderMode.FADE && ((Boolean)this.render.getValue()).booleanValue())));
  
  private final Setting<Integer> max = register(new Setting("MaxPositions", Integer.valueOf(15), Integer.valueOf(1), Integer.valueOf(30), v -> (this.setting.getValue() == Settings.RENDER && this.renderMode.getValue() == RenderMode.FADE && ((Boolean)this.render.getValue()).booleanValue())));
  
  private final Setting<Float> slabHeight = register(new Setting("SlabDepth", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(1.0F), v -> (this.setting.getValue() == Settings.RENDER && (this.renderMode.getValue() == RenderMode.STATIC || this.renderMode.getValue() == RenderMode.GLIDE) && ((Boolean)this.render.getValue()).booleanValue())));
  
  private final Setting<Float> moveSpeed = register(new Setting("Speed", Float.valueOf(900.0F), Float.valueOf(0.0F), Float.valueOf(1500.0F), v -> (this.setting.getValue() == Settings.RENDER && this.renderMode.getValue() == RenderMode.GLIDE && ((Boolean)this.render.getValue()).booleanValue())));
  
  private final Setting<Float> accel = register(new Setting("Deceleration", Float.valueOf(0.8F), Float.valueOf(0.0F), Float.valueOf(1.0F), v -> (this.setting.getValue() == Settings.RENDER && this.renderMode.getValue() == RenderMode.GLIDE && ((Boolean)this.render.getValue()).booleanValue())));
  
  public Setting<Boolean> box = register(new Setting("Box", Boolean.valueOf(true), v -> (this.setting.getValue() == Settings.RENDER && ((Boolean)this.render.getValue()).booleanValue())));
  
  private final Setting<Integer> bRed = register(new Setting("BoxRed", Integer.valueOf(150), Integer.valueOf(0), Integer.valueOf(255), v -> (this.setting.getValue() == Settings.RENDER && ((Boolean)this.render.getValue()).booleanValue() && ((Boolean)this.box.getValue()).booleanValue())));
  
  private final Setting<Integer> bGreen = register(new Setting("BoxGreen", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> (this.setting.getValue() == Settings.RENDER && ((Boolean)this.render.getValue()).booleanValue() && ((Boolean)this.box.getValue()).booleanValue())));
  
  private final Setting<Integer> bBlue = register(new Setting("BoxBlue", Integer.valueOf(150), Integer.valueOf(0), Integer.valueOf(255), v -> (this.setting.getValue() == Settings.RENDER && ((Boolean)this.render.getValue()).booleanValue() && ((Boolean)this.box.getValue()).booleanValue())));
  
  private final Setting<Integer> bAlpha = register(new Setting("BoxAlpha", Integer.valueOf(40), Integer.valueOf(0), Integer.valueOf(255), v -> (this.setting.getValue() == Settings.RENDER && ((Boolean)this.render.getValue()).booleanValue() && ((Boolean)this.box.getValue()).booleanValue())));
  
  public Setting<Boolean> outline = register(new Setting("Outline", Boolean.valueOf(true), v -> (this.setting.getValue() == Settings.RENDER && ((Boolean)this.render.getValue()).booleanValue())));
  
  private final Setting<Integer> oRed = register(new Setting("OutlineRed", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> (this.setting.getValue() == Settings.RENDER && ((Boolean)this.render.getValue()).booleanValue() && ((Boolean)this.outline.getValue()).booleanValue())));
  
  private final Setting<Integer> oGreen = register(new Setting("OutlineGreen", Integer.valueOf(50), Integer.valueOf(0), Integer.valueOf(255), v -> (this.setting.getValue() == Settings.RENDER && ((Boolean)this.render.getValue()).booleanValue() && ((Boolean)this.outline.getValue()).booleanValue())));
  
  private final Setting<Integer> oBlue = register(new Setting("OutlineBlue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> (this.setting.getValue() == Settings.RENDER && ((Boolean)this.render.getValue()).booleanValue() && ((Boolean)this.outline.getValue()).booleanValue())));
  
  private final Setting<Integer> oAlpha = register(new Setting("OutlineAlpha", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> (this.setting.getValue() == Settings.RENDER && ((Boolean)this.render.getValue()).booleanValue() && ((Boolean)this.outline.getValue()).booleanValue())));
  
  private final Setting<Float> lineWidth = register(new Setting("LineWidth", Float.valueOf(1.5F), Float.valueOf(0.1F), Float.valueOf(5.0F), v -> (this.setting.getValue() == Settings.RENDER && ((Boolean)this.render.getValue()).booleanValue() && ((Boolean)this.outline.getValue()).booleanValue())));
  
  public Setting<Boolean> text = register(new Setting("Text", Boolean.valueOf(false), v -> (this.setting.getValue() == Settings.RENDER && ((Boolean)this.render.getValue()).booleanValue())));
  
  private final Setting<Integer> switchCooldown = register(new Setting("Cooldown", Integer.valueOf(500), Integer.valueOf(0), Integer.valueOf(1000), v -> (this.setting.getValue() == Settings.MISC)));
  
  public Setting<Boolean> holdFacePlace = register(new Setting("HoldFacePlace", Boolean.FALSE, v -> (this.setting.getValue() == Settings.MISC)));
  
  public Setting<Boolean> holdFaceBreak = register(new Setting("HoldSlowBreak", Boolean.FALSE, v -> (this.setting.getValue() == Settings.MISC && ((Boolean)this.holdFacePlace.getValue()).booleanValue())));
  
  public Setting<Boolean> slowFaceBreak = register(new Setting("SlowFaceBreak", Boolean.FALSE, v -> (this.setting.getValue() == Settings.MISC)));
  
  public Setting<Boolean> actualSlowBreak = register(new Setting("ActuallySlow", Boolean.FALSE, v -> (this.setting.getValue() == Settings.MISC)));
  
  public Setting<Integer> facePlaceSpeed = register(new Setting("FaceSpeed", Integer.valueOf(500), Integer.valueOf(0), Integer.valueOf(500), v -> (this.setting.getValue() == Settings.MISC)));
  
  public Setting<Boolean> antiNaked = register(new Setting("AntiNaked", Boolean.TRUE, v -> (this.setting.getValue() == Settings.MISC)));
  
  public Setting<Float> range = register(new Setting("Range", Float.valueOf(12.0F), Float.valueOf(0.1F), Float.valueOf(20.0F), v -> (this.setting.getValue() == Settings.MISC)));
  
  public Setting<Target> targetMode = register(new Setting("Target", Target.CLOSEST, v -> (this.setting.getValue() == Settings.MISC)));
  
  public Setting<Integer> minArmor = register(new Setting("MinArmor", Integer.valueOf(5), Integer.valueOf(0), Integer.valueOf(125), v -> (this.setting.getValue() == Settings.MISC)));
  
  public Setting<AutoSwitch> autoSwitch = register(new Setting("Switch", AutoSwitch.TOGGLE, v -> (this.setting.getValue() == Settings.MISC)));
  
  public Setting<Bind> switchBind = register(new Setting("SwitchBind", new Bind(-1), v -> (this.setting.getValue() == Settings.MISC && this.autoSwitch.getValue() == AutoSwitch.TOGGLE)));
  
  public Setting<Boolean> offhandSwitch = register(new Setting("Offhand", Boolean.TRUE, v -> (this.setting.getValue() == Settings.MISC && this.autoSwitch.getValue() != AutoSwitch.NONE && this.autoSwitch.getValue() != AutoSwitch.SILENT)));
  
  public Setting<Boolean> switchBack = register(new Setting("Switchback", Boolean.TRUE, v -> (this.setting.getValue() == Settings.MISC && this.autoSwitch.getValue() != AutoSwitch.NONE && ((Boolean)this.offhandSwitch.getValue()).booleanValue() && this.autoSwitch.getValue() != AutoSwitch.SILENT)));
  
  public Setting<Boolean> lethalSwitch = register(new Setting("LethalSwitch", Boolean.FALSE, v -> (this.setting.getValue() == Settings.MISC && this.autoSwitch.getValue() != AutoSwitch.NONE && this.autoSwitch.getValue() != AutoSwitch.SILENT)));
  
  public Setting<Boolean> mineSwitch = register(new Setting("MineSwitch", Boolean.TRUE, v -> (this.setting.getValue() == Settings.MISC && this.autoSwitch.getValue() != AutoSwitch.NONE && this.autoSwitch.getValue() != AutoSwitch.SILENT)));
  
  public Setting<Rotate> rotate = register(new Setting("Rotate", Rotate.OFF, v -> (this.setting.getValue() == Settings.MISC)));
  
  public Setting<Boolean> suicide = register(new Setting("Suicide", Boolean.FALSE, v -> (this.setting.getValue() == Settings.MISC)));
  
  public Setting<Boolean> webAttack = register(new Setting("WebAttack", Boolean.TRUE, v -> (this.setting.getValue() == Settings.MISC && this.targetMode.getValue() != Target.DAMAGE)));
  
  public Setting<Boolean> fullCalc = register(new Setting("ExtraCalc", Boolean.FALSE, v -> (this.setting.getValue() == Settings.MISC)));
  
  public Setting<Boolean> sound = register(new Setting("Sound", Boolean.TRUE, v -> (this.setting.getValue() == Settings.MISC)));
  
  public Setting<Float> soundRange = register(new Setting("SoundRange", Float.valueOf(12.0F), Float.valueOf(0.0F), Float.valueOf(12.0F), v -> (this.setting.getValue() == Settings.MISC)));
  
  public Setting<Float> soundPlayer = register(new Setting("SoundPlayer", Float.valueOf(6.0F), Float.valueOf(0.0F), Float.valueOf(12.0F), v -> (this.setting.getValue() == Settings.MISC)));
  
  public Setting<Boolean> soundConfirm = register(new Setting("SoundConfirm", Boolean.TRUE, v -> (this.setting.getValue() == Settings.MISC)));
  
  public Setting<Boolean> extraSelfCalc = register(new Setting("MinSelfDmg", Boolean.FALSE, v -> (this.setting.getValue() == Settings.MISC)));
  
  public Setting<AntiFriendPop> antiFriendPop = register(new Setting("FriendPop", AntiFriendPop.NONE, v -> (this.setting.getValue() == Settings.MISC)));
  
  public Setting<Boolean> noCount = register(new Setting("AntiCount", Boolean.FALSE, v -> (this.setting.getValue() == Settings.MISC && (this.antiFriendPop.getValue() == AntiFriendPop.ALL || this.antiFriendPop.getValue() == AntiFriendPop.BREAK))));
  
  public Setting<Boolean> calcEvenIfNoDamage = register(new Setting("BigFriendCalc", Boolean.FALSE, v -> (this.setting.getValue() == Settings.MISC && (this.antiFriendPop.getValue() == AntiFriendPop.ALL || this.antiFriendPop.getValue() == AntiFriendPop.BREAK) && this.targetMode.getValue() != Target.DAMAGE)));
  
  public Setting<Boolean> predictFriendDmg = register(new Setting("PredictFriend", Boolean.FALSE, v -> (this.setting.getValue() == Settings.MISC && (this.antiFriendPop.getValue() == AntiFriendPop.ALL || this.antiFriendPop.getValue() == AntiFriendPop.BREAK) && ((Boolean)this.instant.getValue()).booleanValue())));
  
  public Setting<Raytrace> raytrace = register(new Setting("Raytrace", Raytrace.NONE, v -> (this.setting.getValue() == Settings.MISC)));
  
  private final Setting<Integer> eventMode = register(new Setting("Updates", Integer.valueOf(3), Integer.valueOf(1), Integer.valueOf(3), v -> (this.setting.getValue() == Settings.DEV)));
  
  public final Setting<Boolean> attackOppositeHand = register(new Setting("OppositeHand", Boolean.FALSE, v -> (this.setting.getValue() == Settings.DEV)));
  
  public final Setting<Boolean> removeAfterAttack = register(new Setting("AttackRemove", Boolean.FALSE, v -> (this.setting.getValue() == Settings.DEV)));
  
  public final Setting<Boolean> antiBlock = register(new Setting("AntiFeetPlace", Boolean.FALSE, v -> (this.setting.getValue() == Settings.DEV)));
  
  public Setting<Float> minMinDmg = register(new Setting("MinMinDmg", Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(3.0F), v -> (this.setting.getValue() == Settings.DEV && ((Boolean)this.place.getValue()).booleanValue())));
  
  public Setting<Boolean> breakSwing = register(new Setting("BreakSwing", Boolean.TRUE, v -> (this.setting.getValue() == Settings.DEV)));
  
  public Setting<Boolean> placeSwing = register(new Setting("PlaceSwing", Boolean.FALSE, v -> (this.setting.getValue() == Settings.DEV)));
  
  public Setting<Boolean> exactHand = register(new Setting("ExactHand", Boolean.FALSE, v -> (this.setting.getValue() == Settings.DEV && ((Boolean)this.placeSwing.getValue()).booleanValue())));
  
  public Setting<Boolean> fakeSwing = register(new Setting("FakeSwing", Boolean.FALSE, v -> (this.setting.getValue() == Settings.DEV && ((Boolean)this.justRender.getValue()).booleanValue())));
  
  public Setting<Logic> logic = register(new Setting("Logic", Logic.BREAKPLACE, v -> (this.setting.getValue() == Settings.DEV)));
  
  public Setting<DamageSync> damageSync = register(new Setting("DamageSync", DamageSync.NONE, v -> (this.setting.getValue() == Settings.DEV)));
  
  public Setting<Integer> damageSyncTime = register(new Setting("SyncDelay", Integer.valueOf(500), Integer.valueOf(0), Integer.valueOf(500), v -> (this.setting.getValue() == Settings.DEV && this.damageSync.getValue() != DamageSync.NONE)));
  
  public Setting<Float> dropOff = register(new Setting("DropOff", Float.valueOf(5.0F), Float.valueOf(0.0F), Float.valueOf(10.0F), v -> (this.setting.getValue() == Settings.DEV && this.damageSync.getValue() == DamageSync.BREAK)));
  
  public Setting<Integer> confirm = register(new Setting("Confirm", Integer.valueOf(250), Integer.valueOf(0), Integer.valueOf(1000), v -> (this.setting.getValue() == Settings.DEV && this.damageSync.getValue() != DamageSync.NONE)));
  
  public Setting<Boolean> syncedFeetPlace = register(new Setting("FeetSync", Boolean.FALSE, v -> (this.setting.getValue() == Settings.DEV && this.damageSync.getValue() != DamageSync.NONE)));
  
  public Setting<Boolean> fullSync = register(new Setting("FullSync", Boolean.FALSE, v -> (this.setting.getValue() == Settings.DEV && this.damageSync.getValue() != DamageSync.NONE && ((Boolean)this.syncedFeetPlace.getValue()).booleanValue())));
  
  public Setting<Boolean> syncCount = register(new Setting("SyncCount", Boolean.TRUE, v -> (this.setting.getValue() == Settings.DEV && this.damageSync.getValue() != DamageSync.NONE && ((Boolean)this.syncedFeetPlace.getValue()).booleanValue())));
  
  public Setting<Boolean> hyperSync = register(new Setting("HyperSync", Boolean.FALSE, v -> (this.setting.getValue() == Settings.DEV && this.damageSync.getValue() != DamageSync.NONE && ((Boolean)this.syncedFeetPlace.getValue()).booleanValue())));
  
  public Setting<Boolean> gigaSync = register(new Setting("GigaSync", Boolean.FALSE, v -> (this.setting.getValue() == Settings.DEV && this.damageSync.getValue() != DamageSync.NONE && ((Boolean)this.syncedFeetPlace.getValue()).booleanValue())));
  
  public Setting<Boolean> syncySync = register(new Setting("SyncySync", Boolean.FALSE, v -> (this.setting.getValue() == Settings.DEV && this.damageSync.getValue() != DamageSync.NONE && ((Boolean)this.syncedFeetPlace.getValue()).booleanValue())));
  
  public Setting<Boolean> enormousSync = register(new Setting("EnormousSync", Boolean.FALSE, v -> (this.setting.getValue() == Settings.DEV && this.damageSync.getValue() != DamageSync.NONE && ((Boolean)this.syncedFeetPlace.getValue()).booleanValue())));
  
  public Setting<Boolean> holySync = register(new Setting("UnbelievableSync", Boolean.FALSE, v -> (this.setting.getValue() == Settings.DEV && this.damageSync.getValue() != DamageSync.NONE && ((Boolean)this.syncedFeetPlace.getValue()).booleanValue())));
  
  public Setting<Boolean> rotateFirst = register(new Setting("FirstRotation", Boolean.FALSE, v -> (this.setting.getValue() == Settings.DEV && this.rotate.getValue() != Rotate.OFF && ((Integer)this.eventMode.getValue()).intValue() == 2)));
  
  public Setting<ThreadMode> threadMode = register(new Setting("Thread", ThreadMode.NONE, v -> (this.setting.getValue() == Settings.DEV)));
  
  public Setting<Integer> threadDelay = register(new Setting("ThreadDelay", Integer.valueOf(50), Integer.valueOf(1), Integer.valueOf(1000), v -> (this.setting.getValue() == Settings.DEV && this.threadMode.getValue() != ThreadMode.NONE)));
  
  public Setting<Boolean> syncThreadBool = register(new Setting("ThreadSync", Boolean.TRUE, v -> (this.setting.getValue() == Settings.DEV && this.threadMode.getValue() != ThreadMode.NONE)));
  
  public Setting<Integer> syncThreads = register(new Setting("SyncThreads", Integer.valueOf(1000), Integer.valueOf(1), Integer.valueOf(10000), v -> (this.setting.getValue() == Settings.DEV && this.threadMode.getValue() != ThreadMode.NONE && ((Boolean)this.syncThreadBool.getValue()).booleanValue())));
  
  public Setting<Boolean> predictPos = register(new Setting("PredictPos", Boolean.FALSE, v -> (this.setting.getValue() == Settings.DEV)));
  
  public Setting<Integer> predictTicks = register(new Setting("ExtrapolationTicks", Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(20), v -> (this.setting.getValue() == Settings.DEV && ((Boolean)this.predictPos.getValue()).booleanValue())));
  
  public Setting<Integer> rotations = register(new Setting("Spoofs", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(20), v -> (this.setting.getValue() == Settings.DEV)));
  
  public Setting<Boolean> predictRotate = register(new Setting("PredictRotate", Boolean.FALSE, v -> (this.setting.getValue() == Settings.DEV)));
  
  public Setting<Float> predictOffset = register(new Setting("PredictOffset", Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(4.0F), v -> (this.setting.getValue() == Settings.DEV)));
  
  public boolean rotating;
  
  private Queue<Entity> attackList = new ConcurrentLinkedQueue<>();
  
  private Map<Entity, Float> crystalMap = new HashMap<>();
  
  private Entity efficientTarget;
  
  private double currentDamage;
  
  private double renderDamage;
  
  private double lastDamage;
  
  private boolean didRotation;
  
  private boolean switching;
  
  private BlockPos placePos;
  
  private BlockPos renderPos;
  
  private boolean mainHand;
  
  private boolean offHand;
  
  private int crystalCount;
  
  private int minDmgCount;
  
  private int lastSlot = -1;
  
  private float yaw;
  
  private float pitch;
  
  private BlockPos webPos;
  
  private BlockPos lastPos;
  
  private boolean posConfirmed;
  
  private boolean foundDoublePop;
  
  private int rotationPacketsSpoofed;
  
  private ScheduledExecutorService executor;
  
  private Thread thread;
  
  private EntityPlayer currentSyncTarget;
  
  private BlockPos syncedPlayerPos;
  
  private BlockPos syncedCrystalPos;
  
  private PlaceInfo placeInfo;
  
  private boolean addTolowDmg;
  
  private boolean shouldSilent;
  
  private BlockPos lastRenderPos;
  
  private AxisAlignedBB renderBB;
  
  private float timePassed;
  
  public AutoCrystal() {
    super("AutoCrystal", "0d4 is ver strong:).", Module.Category.COMBAT, true, false, false);
    instance = this;
  }
  
  public static AutoCrystal getInstance() {
    if (instance == null)
      instance = new AutoCrystal(); 
    return instance;
  }
  
  public void onTick() {
    if (this.threadMode.getValue() == ThreadMode.NONE && ((Integer)this.eventMode.getValue()).intValue() == 3)
      doAutoCrystal(); 
  }
  
  @SubscribeEvent
  public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent event) {
    if (event.getStage() == 1)
      postProcessing(); 
    if (event.getStage() != 0)
      return; 
    if (((Integer)this.eventMode.getValue()).intValue() == 2)
      doAutoCrystal(); 
  }
  
  public void postTick() {
    if (this.threadMode.getValue() != ThreadMode.NONE)
      processMultiThreading(); 
  }
  
  public void onUpdate() {
    if (this.threadMode.getValue() == ThreadMode.NONE && ((Integer)this.eventMode.getValue()).intValue() == 1)
      doAutoCrystal(); 
  }
  
  public void onToggle() {
    BrokenPos.clear();
    placedPos.clear();
    this.totemPops.clear();
    this.rotating = false;
  }
  
  public void onDisable() {
    this.positions.clear();
    this.lastRenderPos = null;
    if (this.thread != null)
      this.shouldInterrupt.set(true); 
    if (this.executor != null)
      this.executor.shutdown(); 
  }
  
  public void onEnable() {
    if (this.threadMode.getValue() != ThreadMode.NONE)
      processMultiThreading(); 
  }
  
  public String getDisplayInfo() {
    if (this.switching)
      return "§aSwitch"; 
    if (target != null)
      return target.getName(); 
    return null;
  }
  
  @SubscribeEvent
  public void onPacketSend(PacketEvent.Send event) {
    if (event.getStage() == 0 && this.rotate.getValue() != Rotate.OFF && this.rotating && ((Integer)this.eventMode.getValue()).intValue() != 2 && event.getPacket() instanceof CPacketPlayer) {
      CPacketPlayer packet2 = (CPacketPlayer)event.getPacket();
      packet2.yaw = this.yaw;
      packet2.pitch = this.pitch;
      this.rotationPacketsSpoofed++;
      if (this.rotationPacketsSpoofed >= ((Integer)this.rotations.getValue()).intValue()) {
        this.rotating = false;
        this.rotationPacketsSpoofed = 0;
      } 
    } 
    BlockPos pos = null;
    CPacketUseEntity packet;
    if (event.getStage() == 0 && event.getPacket() instanceof CPacketUseEntity && (packet = (CPacketUseEntity)event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && packet.getEntityFromWorld((World)mc.world) instanceof EntityEnderCrystal) {
      pos = ((Entity)Objects.<Entity>requireNonNull(packet.getEntityFromWorld((World)mc.world))).getPosition();
      if (((Boolean)this.removeAfterAttack.getValue()).booleanValue()) {
        ((Entity)Objects.<Entity>requireNonNull(packet.getEntityFromWorld((World)mc.world))).setDead();
        mc.world.removeEntityFromWorld(packet.entityId);
      } 
    } 
    if (event.getStage() == 0 && event.getPacket() instanceof CPacketUseEntity && (packet = (CPacketUseEntity)event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && packet.getEntityFromWorld((World)mc.world) instanceof EntityEnderCrystal) {
      EntityEnderCrystal crystal = (EntityEnderCrystal)packet.getEntityFromWorld((World)mc.world);
      if (((Boolean)this.antiBlock.getValue()).booleanValue() && EntityUtil.isCrystalAtFeet(crystal, ((Float)this.range.getValue()).floatValue()) && pos != null) {
        rotateToPos(pos);
        BlockUtil.placeCrystalOnBlock(this.placePos, this.offHand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, ((Boolean)this.placeSwing.getValue()).booleanValue(), ((Boolean)this.exactHand.getValue()).booleanValue(), this.shouldSilent);
      } 
    } 
  }
  
  @SubscribeEvent(priority = EventPriority.HIGH, receiveCanceled = true)
  public void onPacketReceive(PacketEvent.Receive event) {
    if (fullNullCheck())
      return; 
    if (!((Boolean)this.justRender.getValue()).booleanValue() && this.switchTimer.passedMs(((Integer)this.switchCooldown.getValue()).intValue()) && ((Boolean)this.explode.getValue()).booleanValue() && ((Boolean)this.instant.getValue()).booleanValue() && event.getPacket() instanceof SPacketSpawnObject && (this.syncedCrystalPos == null || !((Boolean)this.syncedFeetPlace.getValue()).booleanValue() || this.damageSync.getValue() == DamageSync.NONE)) {
      SPacketSpawnObject packet2 = (SPacketSpawnObject)event.getPacket();
      BlockPos pos;
      if (packet2.getType() == 51 && mc.player.getDistanceSq(pos = new BlockPos(packet2.getX(), packet2.getY(), packet2.getZ())) + ((Float)this.predictOffset.getValue()).floatValue() <= MathUtil.square(((Float)this.breakRange.getValue()).floatValue()) && (this.instantTimer.getValue() == PredictTimer.NONE || (this.instantTimer.getValue() == PredictTimer.BREAK && this.breakTimer.passedMs(((Integer)this.breakDelay.getValue()).intValue())) || (this.instantTimer.getValue() == PredictTimer.PREDICT && this.predictTimer.passedMs(((Integer)this.predictDelay.getValue()).intValue())))) {
        if (predictSlowBreak(pos.down()))
          return; 
        if (((Boolean)this.predictFriendDmg.getValue()).booleanValue() && (this.antiFriendPop.getValue() == AntiFriendPop.BREAK || this.antiFriendPop.getValue() == AntiFriendPop.ALL) && isRightThread())
          for (EntityPlayer friend : mc.world.playerEntities) {
            if (friend == null || mc.player.equals(friend) || friend.getDistanceSq(pos) > MathUtil.square((((Float)this.range.getValue()).floatValue() + ((Float)this.placeRange.getValue()).floatValue())) || !M3dC3t.friendManager.isFriend(friend) || DamageUtil.calculateDamage(pos, (Entity)friend) <= EntityUtil.getHealth((Entity)friend) + 0.5D)
              continue; 
            return;
          }  
        if (placedPos.contains(pos.down())) {
          float selfDamage;
          if (isRightThread() ? (((Boolean)this.superSafe.getValue()).booleanValue() ? (DamageUtil.canTakeDamage(((Boolean)this.suicide.getValue()).booleanValue()) && ((selfDamage = DamageUtil.calculateDamage(pos, (Entity)mc.player)) - 0.5D > EntityUtil.getHealth((Entity)mc.player) || selfDamage > ((Float)this.maxSelfBreak.getValue()).floatValue())) : ((Boolean)this.superSafe.getValue()).booleanValue()) : ((Boolean)this.superSafe.getValue()).booleanValue())
            return; 
          attackCrystalPredict(packet2.getEntityID(), pos);
        } else if (((Boolean)this.predictCalc.getValue()).booleanValue() && isRightThread()) {
          float selfDamage = -1.0F;
          if (DamageUtil.canTakeDamage(((Boolean)this.suicide.getValue()).booleanValue()))
            selfDamage = DamageUtil.calculateDamage(pos, (Entity)mc.player); 
          if (selfDamage + 0.5D < EntityUtil.getHealth((Entity)mc.player) && selfDamage <= ((Float)this.maxSelfBreak.getValue()).floatValue())
            for (EntityPlayer player : mc.world.playerEntities) {
              float damage;
              if (player.getDistanceSq(pos) > MathUtil.square(((Float)this.range.getValue()).floatValue()) || !EntityUtil.isValid((Entity)player, (((Float)this.range.getValue()).floatValue() + ((Float)this.breakRange.getValue()).floatValue())) || (((Boolean)this.antiNaked.getValue()).booleanValue() && DamageUtil.isNaked(player)) || ((damage = DamageUtil.calculateDamage(pos, (Entity)player)) <= selfDamage && (damage <= ((Float)this.minDamage.getValue()).floatValue() || DamageUtil.canTakeDamage(((Boolean)this.suicide.getValue()).booleanValue())) && damage <= EntityUtil.getHealth((Entity)player)))
                continue; 
              if (((Boolean)this.predictRotate.getValue()).booleanValue() && ((Integer)this.eventMode.getValue()).intValue() != 2 && (this.rotate.getValue() == Rotate.BREAK || this.rotate.getValue() == Rotate.ALL))
                rotateToPos(pos); 
              attackCrystalPredict(packet2.getEntityID(), pos);
            }  
        } 
      } 
    } else if (!((Boolean)this.soundConfirm.getValue()).booleanValue() && event.getPacket() instanceof SPacketExplosion) {
      SPacketExplosion packet3 = (SPacketExplosion)event.getPacket();
      BlockPos pos = (new BlockPos(packet3.getX(), packet3.getY(), packet3.getZ())).down();
      removePos(pos);
    } else if (event.getPacket() instanceof SPacketDestroyEntities) {
      SPacketDestroyEntities packet4 = (SPacketDestroyEntities)event.getPacket();
      for (int id : packet4.getEntityIDs()) {
        Entity entity = mc.world.getEntityByID(id);
        if (entity instanceof EntityEnderCrystal) {
          BrokenPos.remove((new BlockPos(entity.getPositionVector())).down());
          placedPos.remove((new BlockPos(entity.getPositionVector())).down());
        } 
      } 
    } else if (event.getPacket() instanceof SPacketEntityStatus) {
      SPacketEntityStatus packet5 = (SPacketEntityStatus)event.getPacket();
      if (packet5.getOpCode() == 35 && packet5.getEntity((World)mc.world) instanceof EntityPlayer)
        this.totemPops.put((EntityPlayer)packet5.getEntity((World)mc.world), (new Timer()).reset()); 
    } else {
      SPacketSoundEffect packet;
      if (event.getPacket() instanceof SPacketSoundEffect && (packet = (SPacketSoundEffect)event.getPacket()).getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
        BlockPos pos = new BlockPos(packet.getX(), packet.getY(), packet.getZ());
        if ((((Boolean)this.sound.getValue()).booleanValue() || this.threadMode.getValue() == ThreadMode.SOUND) && 
          fullNullCheck())
          return; 
        if (((Boolean)this.soundConfirm.getValue()).booleanValue())
          removePos(pos); 
        if (this.threadMode.getValue() == ThreadMode.SOUND && isRightThread() && mc.player != null && mc.player.getDistanceSq(pos) < MathUtil.square(((Float)this.soundPlayer.getValue()).floatValue()))
          handlePool(true); 
      } 
    } 
  }
  
  private boolean predictSlowBreak(BlockPos pos) {
    if (((Boolean)this.antiCommit.getValue()).booleanValue() && lowDmgPos.remove(pos))
      return shouldSlowBreak(false); 
    return false;
  }
  
  private boolean isRightThread() {
    return (mc.isCallingFromMinecraftThread() || (!M3dC3t.eventManager.ticksOngoing() && !this.threadOngoing.get()));
  }
  
  private void attackCrystalPredict(int entityID, BlockPos pos) {
    if (((Boolean)this.predictRotate.getValue()).booleanValue() && (((Integer)this.eventMode.getValue()).intValue() != 2 || this.threadMode.getValue() != ThreadMode.NONE) && (this.rotate.getValue() == Rotate.BREAK || this.rotate.getValue() == Rotate.ALL))
      rotateToPos(pos); 
    CPacketUseEntity attackPacket = new CPacketUseEntity();
    attackPacket.entityId = entityID;
    attackPacket.action = CPacketUseEntity.Action.ATTACK;
    mc.player.connection.sendPacket((Packet)attackPacket);
    if (((Boolean)this.breakSwing.getValue()).booleanValue())
      mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND)); 
    if (((Boolean)this.resetBreakTimer.getValue()).booleanValue())
      this.breakTimer.reset(); 
    this.predictTimer.reset();
  }
  
  private void removePos(BlockPos pos) {
    if (this.damageSync.getValue() == DamageSync.PLACE) {
      if (placedPos.remove(pos))
        this.posConfirmed = true; 
    } else if (this.damageSync.getValue() == DamageSync.BREAK && BrokenPos.remove(pos)) {
      this.posConfirmed = true;
    } 
  }
  
  public void onRender3D(Render3DEvent event) {
    if (!((Boolean)this.render.getValue()).booleanValue())
      return; 
    Color boxC = new Color(((Integer)this.bRed.getValue()).intValue(), ((Integer)this.bGreen.getValue()).intValue(), ((Integer)this.bBlue.getValue()).intValue(), ((Integer)this.bAlpha.getValue()).intValue());
    Color outlineC = new Color(((Integer)this.oRed.getValue()).intValue(), ((Integer)this.oGreen.getValue()).intValue(), ((Integer)this.oBlue.getValue()).intValue(), ((Integer)this.oAlpha.getValue()).intValue());
    if ((this.offHand || this.mainHand || this.switchMode.getValue() == Switch.CALC) && this.renderPos != null && (((Boolean)this.box.getValue()).booleanValue() || ((Boolean)this.outline.getValue()).booleanValue())) {
      if (this.renderMode.getValue() == RenderMode.FADE) {
        this.positions.removeIf(pos -> pos.getPos().equals(this.renderPos));
        this.positions.add(new RenderPos(this.renderPos, 0.0F));
      } 
      if (this.renderMode.getValue() == RenderMode.STATIC)
        RenderUtil.drawSexyBoxPhobosIsRetardedFuckYouESP(new AxisAlignedBB(this.renderPos), boxC, outlineC, ((Float)this.lineWidth
            
            .getValue()).floatValue(), ((Boolean)this.outline
            .getValue()).booleanValue(), ((Boolean)this.box
            .getValue()).booleanValue(), 1.0F, 1.0F, ((Float)this.slabHeight
            
            .getValue()).floatValue()); 
      if (this.renderMode.getValue() == RenderMode.GLIDE) {
        if (this.lastRenderPos == null || mc.player.getDistance(this.renderBB.minX, this.renderBB.minY, this.renderBB.minZ) > ((Float)this.range.getValue()).floatValue()) {
          this.lastRenderPos = this.renderPos;
          this.renderBB = new AxisAlignedBB(this.renderPos);
          this.timePassed = 0.0F;
        } 
        if (!this.lastRenderPos.equals(this.renderPos)) {
          this.lastRenderPos = this.renderPos;
          this.timePassed = 0.0F;
        } 
        double xDiff = this.renderPos.getX() - this.renderBB.minX;
        double yDiff = this.renderPos.getY() - this.renderBB.minY;
        double zDiff = this.renderPos.getZ() - this.renderBB.minZ;
        float multiplier = this.timePassed / ((Float)this.moveSpeed.getValue()).floatValue() * ((Float)this.accel.getValue()).floatValue();
        if (multiplier > 1.0F)
          multiplier = 1.0F; 
        this.renderBB = this.renderBB.offset(xDiff * multiplier, yDiff * multiplier, zDiff * multiplier);
        RenderUtil.drawSexyBoxPhobosIsRetardedFuckYouESP(this.renderBB, boxC, outlineC, ((Float)this.lineWidth
            
            .getValue()).floatValue(), ((Boolean)this.outline
            .getValue()).booleanValue(), ((Boolean)this.box
            .getValue()).booleanValue(), 1.0F, 1.0F, ((Float)this.slabHeight
            
            .getValue()).floatValue());
        if (((Boolean)this.text.getValue()).booleanValue())
          RenderUtil.drawText(this.renderBB
              .offset(0.0D, (1.0F - ((Float)this.slabHeight.getValue()).floatValue() / 2.0F) - 0.4D, 0.0D), (
              (Math.floor(this.renderDamage) == this.renderDamage) ? (String)Integer.valueOf((int)this.renderDamage) : String.format("%.1f", new Object[] { Double.valueOf(this.renderDamage) })) + ""); 
        if (this.renderBB.equals(new AxisAlignedBB(this.renderPos))) {
          this.timePassed = 0.0F;
        } else {
          this.timePassed += 50.0F;
        } 
      } 
    } 
    if (this.renderMode.getValue() == RenderMode.FADE) {
      this.positions.forEach(pos -> {
            float factor = (((Float)this.duration.getValue()).floatValue() - pos.getRenderTime()) / ((Float)this.duration.getValue()).floatValue();
            RenderUtil.drawSexyBoxPhobosIsRetardedFuckYouESP(new AxisAlignedBB(pos.getPos()), boxC, outlineC, ((Float)this.lineWidth.getValue()).floatValue(), ((Boolean)this.outline.getValue()).booleanValue(), ((Boolean)this.box.getValue()).booleanValue(), ((Boolean)this.fadeFactor.getValue()).booleanValue() ? factor : 1.0F, ((Boolean)this.scaleFactor.getValue()).booleanValue() ? factor : 1.0F, ((Boolean)this.slabFactor.getValue()).booleanValue() ? factor : 1.0F);
            pos.setRenderTime(pos.getRenderTime() + 50.0F);
          });
      this.positions.removeIf(pos -> 
          (pos.getRenderTime() >= ((Float)this.duration.getValue()).floatValue() || mc.world.isAirBlock(pos.getPos()) || !mc.world.isAirBlock(pos.getPos().offset(EnumFacing.UP))));
      if (this.positions.size() > ((Integer)this.max.getValue()).intValue())
        this.positions.remove(0); 
    } 
    if ((this.offHand || this.mainHand || this.switchMode.getValue() == Switch.CALC) && this.renderPos != null && ((Boolean)this.text.getValue()).booleanValue() && this.renderMode.getValue() != RenderMode.GLIDE)
      RenderUtil.drawText((new AxisAlignedBB(this.renderPos))
          .offset(0.0D, (this.renderMode.getValue() != RenderMode.FADE) ? ((1.0F - ((Float)this.slabHeight.getValue()).floatValue() / 2.0F) - 0.4D) : 0.1D, 0.0D), (
          (Math.floor(this.renderDamage) == this.renderDamage) ? (String)Integer.valueOf((int)this.renderDamage) : String.format("%.1f", new Object[] { Double.valueOf(this.renderDamage) })) + ""); 
  }
  
  @SubscribeEvent
  public void onKeyInput(InputEvent.KeyInputEvent event) {
    if (Keyboard.getEventKeyState() && !(mc.currentScreen instanceof dev.madcat.m3dc3t.features.gui.M3dC3tGui) && ((Bind)this.switchBind.getValue()).getKey() == Keyboard.getEventKey()) {
      if (((Boolean)this.switchBack.getValue()).booleanValue() && ((Boolean)this.offhandSwitch.getValue()).booleanValue() && this.offHand)
        return; 
      this.switching = !this.switching;
    } 
  }
  
  @SubscribeEvent
  public void onSettingChange(ClientEvent event) {
    if (event.getStage() == 2 && event.getSetting() != null && event.getSetting().getFeature() != null && event.getSetting().getFeature().equals(this) && isEnabled() && (event.getSetting().equals(this.threadDelay) || event.getSetting().equals(this.threadMode))) {
      if (this.executor != null)
        this.executor.shutdown(); 
      if (this.thread != null)
        this.shouldInterrupt.set(true); 
    } 
  }
  
  private void postProcessing() {
    if (this.threadMode.getValue() != ThreadMode.NONE || ((Integer)this.eventMode.getValue()).intValue() != 2 || this.rotate.getValue() == Rotate.OFF || !((Boolean)this.rotateFirst.getValue()).booleanValue())
      return; 
    switch ((Logic)this.logic.getValue()) {
      case OFF:
        postProcessBreak();
        postProcessPlace();
        break;
      case PLACE:
        postProcessPlace();
        postProcessBreak();
        break;
    } 
  }
  
  private void postProcessBreak() {
    while (!this.packetUseEntities.isEmpty()) {
      CPacketUseEntity packet = this.packetUseEntities.poll();
      mc.player.connection.sendPacket((Packet)packet);
      if (((Boolean)this.breakSwing.getValue()).booleanValue())
        mc.player.swingArm(EnumHand.MAIN_HAND); 
      this.breakTimer.reset();
    } 
  }
  
  private void postProcessPlace() {
    if (this.placeInfo != null) {
      this.placeInfo.runPlace();
      this.placeTimer.reset();
      this.placeInfo = null;
    } 
  }
  
  private void processMultiThreading() {
    if (isOff())
      return; 
    if (this.threadMode.getValue() == ThreadMode.WHILE) {
      handleWhile();
    } else if (this.threadMode.getValue() != ThreadMode.NONE) {
      handlePool(false);
    } 
  }
  
  private void handlePool(boolean justDoIt) {
    if (justDoIt || this.executor == null || this.executor.isTerminated() || this.executor.isShutdown() || (this.syncroTimer.passedMs(((Integer)this.syncThreads.getValue()).intValue()) && ((Boolean)this.syncThreadBool.getValue()).booleanValue())) {
      if (this.executor != null)
        this.executor.shutdown(); 
      this.executor = getExecutor();
      this.syncroTimer.reset();
    } 
  }
  
  private void handleWhile() {
    if (this.thread == null || this.thread.isInterrupted() || !this.thread.isAlive() || (this.syncroTimer.passedMs(((Integer)this.syncThreads.getValue()).intValue()) && ((Boolean)this.syncThreadBool.getValue()).booleanValue())) {
      if (this.thread == null) {
        this.thread = new Thread(RAutoCrystal.getInstance(this));
      } else if (this.syncroTimer.passedMs(((Integer)this.syncThreads.getValue()).intValue()) && !this.shouldInterrupt.get() && ((Boolean)this.syncThreadBool.getValue()).booleanValue()) {
        this.shouldInterrupt.set(true);
        this.syncroTimer.reset();
        return;
      } 
      if (this.thread != null && (this.thread.isInterrupted() || !this.thread.isAlive()))
        this.thread = new Thread(RAutoCrystal.getInstance(this)); 
      if (this.thread != null && this.thread.getState() == Thread.State.NEW) {
        try {
          this.thread.start();
        } catch (Exception e) {
          e.printStackTrace();
        } 
        this.syncroTimer.reset();
      } 
    } 
  }
  
  private ScheduledExecutorService getExecutor() {
    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    service.scheduleAtFixedRate(RAutoCrystal.getInstance(this), 0L, ((Integer)this.threadDelay.getValue()).intValue(), TimeUnit.MILLISECONDS);
    return service;
  }
  
  public void doAutoCrystal() {
    if (check()) {
      switch ((Logic)this.logic.getValue()) {
        case PLACE:
          placeCrystal();
          breakCrystal();
          break;
        case OFF:
          breakCrystal();
          placeCrystal();
          break;
      } 
      manualBreaker();
    } 
  }
  
  private boolean check() {
    if (fullNullCheck())
      return false; 
    if (this.syncTimer.passedMs(((Integer)this.damageSyncTime.getValue()).intValue())) {
      this.currentSyncTarget = null;
      this.syncedCrystalPos = null;
      this.syncedPlayerPos = null;
    } else if (((Boolean)this.syncySync.getValue()).booleanValue() && this.syncedCrystalPos != null) {
      this.posConfirmed = true;
    } 
    this.foundDoublePop = false;
    if (this.renderTimer.passedMs(500L)) {
      this.renderPos = null;
      this.renderTimer.reset();
    } 
    this.mainHand = (mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL);
    if (this.autoSwitch.getValue() == AutoSwitch.SILENT && InventoryUtil.getItemHotbar(Items.END_CRYSTAL) != -1) {
      this.mainHand = true;
      this.shouldSilent = true;
    } else {
      this.shouldSilent = false;
    } 
    this.offHand = (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL);
    this.currentDamage = 0.0D;
    this.placePos = null;
    if (this.lastSlot != mc.player.inventory.currentItem || Surround.isPlacing) {
      this.lastSlot = mc.player.inventory.currentItem;
      this.switchTimer.reset();
    } 
    if (!this.offHand && !this.mainHand) {
      this.placeInfo = null;
      this.packetUseEntities.clear();
    } 
    if (this.offHand || this.mainHand)
      this.switching = false; 
    if ((!this.offHand && !this.mainHand && this.switchMode.getValue() == Switch.BREAKSLOT && !this.switching) || !DamageUtil.canBreakWeakness((EntityPlayer)mc.player) || !this.switchTimer.passedMs(((Integer)this.switchCooldown.getValue()).intValue())) {
      this.renderPos = null;
      target = null;
      this.rotating = false;
      return false;
    } 
    if (((Boolean)this.mineSwitch.getValue()).booleanValue() && Mouse.isButtonDown(0) && (this.switching || this.autoSwitch.getValue() == AutoSwitch.ALWAYS) && Mouse.isButtonDown(1) && mc.player.getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemPickaxe)
      switchItem(); 
    mapCrystals();
    if (!this.posConfirmed && this.damageSync.getValue() != DamageSync.NONE && this.syncTimer.passedMs(((Integer)this.confirm.getValue()).intValue()))
      this.syncTimer.setMs((((Integer)this.damageSyncTime.getValue()).intValue() + 1)); 
    return true;
  }
  
  private void mapCrystals() {
    this.efficientTarget = null;
    if (((Integer)this.packets.getValue()).intValue() != 1) {
      this.attackList = new ConcurrentLinkedQueue<>();
      this.crystalMap = new HashMap<>();
    } 
    this.crystalCount = 0;
    this.minDmgCount = 0;
    Entity maxCrystal = null;
    float maxDamage = 0.5F;
    for (Entity entity : mc.world.loadedEntityList) {
      if (entity.isDead || !(entity instanceof EntityEnderCrystal) || !isValid(entity))
        continue; 
      if (((Boolean)this.syncedFeetPlace.getValue()).booleanValue() && entity.getPosition().down().equals(this.syncedCrystalPos) && this.damageSync.getValue() != DamageSync.NONE) {
        this.minDmgCount++;
        this.crystalCount++;
        if (((Boolean)this.syncCount.getValue()).booleanValue()) {
          this.minDmgCount = ((Integer)this.wasteAmount.getValue()).intValue() + 1;
          this.crystalCount = ((Integer)this.wasteAmount.getValue()).intValue() + 1;
        } 
        if (!((Boolean)this.hyperSync.getValue()).booleanValue())
          continue; 
        maxCrystal = null;
        break;
      } 
      boolean count = false;
      boolean countMin = false;
      float selfDamage = -1.0F;
      if (DamageUtil.canTakeDamage(((Boolean)this.suicide.getValue()).booleanValue()))
        selfDamage = DamageUtil.calculateDamage(entity, (Entity)mc.player); 
      if (selfDamage + 0.5D < EntityUtil.getHealth((Entity)mc.player) && selfDamage <= ((Float)this.maxSelfBreak.getValue()).floatValue()) {
        Entity beforeCrystal = maxCrystal;
        float beforeDamage = maxDamage;
        for (EntityPlayer player : mc.world.playerEntities) {
          if (player.getDistanceSq(entity) > MathUtil.square(((Float)this.range.getValue()).floatValue()))
            continue; 
          if (EntityUtil.isValid((Entity)player, (((Float)this.range.getValue()).floatValue() + ((Float)this.breakRange.getValue()).floatValue()))) {
            float damage;
            if ((((Boolean)this.antiNaked.getValue()).booleanValue() && DamageUtil.isNaked(player)) || ((damage = DamageUtil.calculateDamage(entity, (Entity)player)) <= selfDamage && (damage <= ((Float)this.minDamage.getValue()).floatValue() || DamageUtil.canTakeDamage(((Boolean)this.suicide.getValue()).booleanValue())) && damage <= EntityUtil.getHealth((Entity)player)))
              continue; 
            if (damage > maxDamage) {
              maxDamage = damage;
              maxCrystal = entity;
            } 
            if (((Integer)this.packets.getValue()).intValue() == 1) {
              if (damage >= ((Float)this.minDamage.getValue()).floatValue() || !((Boolean)this.wasteMinDmgCount.getValue()).booleanValue())
                count = true; 
              countMin = true;
              continue;
            } 
            if (this.crystalMap.get(entity) != null && ((Float)this.crystalMap.get(entity)).floatValue() >= damage)
              continue; 
            this.crystalMap.put(entity, Float.valueOf(damage));
            continue;
          } 
          if ((this.antiFriendPop.getValue() != AntiFriendPop.BREAK && this.antiFriendPop.getValue() != AntiFriendPop.ALL) || !M3dC3t.friendManager.isFriend(player.getName()) || DamageUtil.calculateDamage(entity, (Entity)player) <= EntityUtil.getHealth((Entity)player) + 0.5D)
            continue; 
          maxCrystal = beforeCrystal;
          maxDamage = beforeDamage;
          this.crystalMap.remove(entity);
          if (!((Boolean)this.noCount.getValue()).booleanValue())
            break; 
          count = false;
          countMin = false;
        } 
      } 
      if (!countMin)
        continue; 
      this.minDmgCount++;
      if (!count)
        continue; 
      this.crystalCount++;
    } 
    if (this.damageSync.getValue() == DamageSync.BREAK && (maxDamage > this.lastDamage || this.syncTimer.passedMs(((Integer)this.damageSyncTime.getValue()).intValue()) || this.damageSync.getValue() == DamageSync.NONE))
      this.lastDamage = maxDamage; 
    if (((Boolean)this.enormousSync.getValue()).booleanValue() && ((Boolean)this.syncedFeetPlace.getValue()).booleanValue() && this.damageSync.getValue() != DamageSync.NONE && this.syncedCrystalPos != null) {
      if (((Boolean)this.syncCount.getValue()).booleanValue()) {
        this.minDmgCount = ((Integer)this.wasteAmount.getValue()).intValue() + 1;
        this.crystalCount = ((Integer)this.wasteAmount.getValue()).intValue() + 1;
      } 
      return;
    } 
    if (((Boolean)this.webAttack.getValue()).booleanValue() && this.webPos != null)
      if (mc.player.getDistanceSq(this.webPos.up()) > MathUtil.square(((Float)this.breakRange.getValue()).floatValue())) {
        this.webPos = null;
      } else {
        for (Entity entity : mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(this.webPos.up()))) {
          if (!(entity instanceof EntityEnderCrystal))
            continue; 
          this.attackList.add(entity);
          this.efficientTarget = entity;
          this.webPos = null;
          this.lastDamage = 0.5D;
          return;
        } 
      }  
    if (shouldSlowBreak(true) && maxDamage < ((Float)this.minDamage.getValue()).floatValue() && (target == null || EntityUtil.getHealth((Entity)target) > ((Float)this.facePlace.getValue()).floatValue() || (!this.breakTimer.passedMs(((Integer)this.facePlaceSpeed.getValue()).intValue()) && ((Boolean)this.slowFaceBreak.getValue()).booleanValue() && Mouse.isButtonDown(0) && ((Boolean)this.holdFacePlace.getValue()).booleanValue() && ((Boolean)this.holdFaceBreak.getValue()).booleanValue()))) {
      this.efficientTarget = null;
      return;
    } 
    if (((Integer)this.packets.getValue()).intValue() == 1) {
      this.efficientTarget = maxCrystal;
    } else {
      this.crystalMap = MathUtil.sortByValue(this.crystalMap, true);
      for (Map.Entry<Entity, Float> entry : this.crystalMap.entrySet()) {
        Entity crystal = entry.getKey();
        float damage = ((Float)entry.getValue()).floatValue();
        if (damage >= ((Float)this.minDamage.getValue()).floatValue() || !((Boolean)this.wasteMinDmgCount.getValue()).booleanValue())
          this.crystalCount++; 
        this.attackList.add(crystal);
        this.minDmgCount++;
      } 
    } 
  }
  
  private boolean shouldSlowBreak(boolean withManual) {
    return ((withManual && ((Boolean)this.manual.getValue()).booleanValue() && ((Boolean)this.manualMinDmg.getValue()).booleanValue() && Mouse.isButtonDown(1) && (!Mouse.isButtonDown(0) || !((Boolean)this.holdFacePlace.getValue()).booleanValue())) || (((Boolean)this.holdFacePlace.getValue()).booleanValue() && ((Boolean)this.holdFaceBreak.getValue()).booleanValue() && Mouse.isButtonDown(0) && !this.breakTimer.passedMs(((Integer)this.facePlaceSpeed.getValue()).intValue())) || (((Boolean)this.slowFaceBreak.getValue()).booleanValue() && !this.breakTimer.passedMs(((Integer)this.facePlaceSpeed.getValue()).intValue())));
  }
  
  private void placeCrystal() {
    int crystalLimit = ((Integer)this.wasteAmount.getValue()).intValue();
    if (this.placeTimer.passedMs(((Integer)this.placeDelay.getValue()).intValue()) && ((Boolean)this.place.getValue()).booleanValue() && (this.offHand || this.mainHand || this.switchMode.getValue() == Switch.CALC || (this.switchMode.getValue() == Switch.BREAKSLOT && this.switching))) {
      if ((this.offHand || this.mainHand || (this.switchMode.getValue() != Switch.ALWAYS && !this.switching)) && this.crystalCount >= crystalLimit && (!((Boolean)this.antiSurround.getValue()).booleanValue() || this.lastPos == null || !this.lastPos.equals(this.placePos)))
        return; 
      calculateDamage(getTarget((this.targetMode.getValue() == Target.UNSAFE)));
      if (target != null && this.placePos != null) {
        if (!this.offHand && !this.mainHand && this.autoSwitch.getValue() != AutoSwitch.NONE && (this.currentDamage > ((Float)this.minDamage.getValue()).floatValue() || (((Boolean)this.lethalSwitch.getValue()).booleanValue() && EntityUtil.getHealth((Entity)target) <= ((Float)this.facePlace.getValue()).floatValue())) && !switchItem())
          return; 
        if (this.currentDamage < ((Float)this.minDamage.getValue()).floatValue() && ((Boolean)this.limitFacePlace.getValue()).booleanValue())
          crystalLimit = 1; 
        if (this.currentDamage >= ((Float)this.minMinDmg.getValue()).floatValue() && (this.offHand || this.mainHand || this.autoSwitch.getValue() != AutoSwitch.NONE) && (this.crystalCount < crystalLimit || (((Boolean)this.antiSurround.getValue()).booleanValue() && this.lastPos != null && this.lastPos.equals(this.placePos))) && (this.currentDamage > ((Float)this.minDamage.getValue()).floatValue() || this.minDmgCount < crystalLimit) && this.currentDamage >= 1.0D && (DamageUtil.isArmorLow(target, ((Integer)this.minArmor.getValue()).intValue()) || EntityUtil.getHealth((Entity)target) <= ((Float)this.facePlace.getValue()).floatValue() || this.currentDamage > ((Float)this.minDamage.getValue()).floatValue() || shouldHoldFacePlace())) {
          float damageOffset = (this.damageSync.getValue() == DamageSync.BREAK) ? (((Float)this.dropOff.getValue()).floatValue() - 5.0F) : 0.0F;
          boolean syncflag = false;
          if (((Boolean)this.syncedFeetPlace.getValue()).booleanValue() && this.placePos.equals(this.lastPos) && isEligableForFeetSync(target, this.placePos) && !this.syncTimer.passedMs(((Integer)this.damageSyncTime.getValue()).intValue()) && target.equals(this.currentSyncTarget) && target.getPosition().equals(this.syncedPlayerPos) && this.damageSync.getValue() != DamageSync.NONE) {
            this.syncedCrystalPos = this.placePos;
            this.lastDamage = this.currentDamage;
            if (((Boolean)this.fullSync.getValue()).booleanValue())
              this.lastDamage = 100.0D; 
            syncflag = true;
          } 
          if (syncflag || this.currentDamage - damageOffset > this.lastDamage || this.syncTimer.passedMs(((Integer)this.damageSyncTime.getValue()).intValue()) || this.damageSync.getValue() == DamageSync.NONE) {
            if (!syncflag && this.damageSync.getValue() != DamageSync.BREAK)
              this.lastDamage = this.currentDamage; 
            if (!((Boolean)this.onlyplaced.getValue()).booleanValue())
              this.renderPos = this.placePos; 
            this.renderDamage = this.currentDamage;
            if (switchItem()) {
              this.currentSyncTarget = target;
              this.syncedPlayerPos = target.getPosition();
              if (this.foundDoublePop)
                this.totemPops.put(target, (new Timer()).reset()); 
              rotateToPos(this.placePos);
              if (this.addTolowDmg || (((Boolean)this.actualSlowBreak.getValue()).booleanValue() && this.currentDamage < ((Float)this.minDamage.getValue()).floatValue()))
                lowDmgPos.add(this.placePos); 
              placedPos.add(this.placePos);
              if (!((Boolean)this.justRender.getValue()).booleanValue())
                if (((Integer)this.eventMode.getValue()).intValue() == 2 && this.threadMode.getValue() == ThreadMode.NONE && ((Boolean)this.rotateFirst.getValue()).booleanValue() && this.rotate.getValue() != Rotate.OFF) {
                  this.placeInfo = new PlaceInfo(this.placePos, this.offHand, ((Boolean)this.placeSwing.getValue()).booleanValue(), ((Boolean)this.exactHand.getValue()).booleanValue(), this.shouldSilent);
                } else {
                  BlockUtil.placeCrystalOnBlock(this.placePos, this.offHand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, ((Boolean)this.placeSwing.getValue()).booleanValue(), ((Boolean)this.exactHand.getValue()).booleanValue(), this.shouldSilent);
                }  
              this.lastPos = this.placePos;
              this.placeTimer.reset();
              this.posConfirmed = false;
              if (this.syncTimer.passedMs(((Integer)this.damageSyncTime.getValue()).intValue())) {
                this.syncedCrystalPos = null;
                this.syncTimer.reset();
              } 
            } 
          } 
        } 
      } else {
        this.renderPos = null;
      } 
    } 
  }
  
  private boolean shouldHoldFacePlace() {
    this.addTolowDmg = false;
    if (((Boolean)this.holdFacePlace.getValue()).booleanValue() && Mouse.isButtonDown(0)) {
      this.addTolowDmg = true;
      return true;
    } 
    return false;
  }
  
  private boolean switchItem() {
    if (this.offHand || this.mainHand)
      return true; 
    switch ((AutoSwitch)this.autoSwitch.getValue()) {
      case OFF:
        return false;
      case PLACE:
        if (!this.switching)
          return false; 
      case BREAK:
        if (!doSwitch())
          break; 
        return true;
    } 
    return false;
  }
  
  private boolean doSwitch() {
    if (((Boolean)this.offhandSwitch.getValue()).booleanValue()) {
      this.switching = false;
      return true;
    } 
    if (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
      this.mainHand = false;
    } else {
      InventoryUtil.switchToHotbarSlot(ItemEndCrystal.class, false);
      this.mainHand = true;
    } 
    this.switching = false;
    return true;
  }
  
  private void calculateDamage(EntityPlayer targettedPlayer) {
    if (targettedPlayer == null && this.targetMode.getValue() != Target.DAMAGE && !((Boolean)this.fullCalc.getValue()).booleanValue())
      return; 
    float maxDamage = 0.5F;
    EntityPlayer currentTarget = null;
    BlockPos currentPos = null;
    float maxSelfDamage = 0.0F;
    this.foundDoublePop = false;
    BlockPos setToAir = null;
    IBlockState state = null;
    BlockPos playerPos;
    if (((Boolean)this.webAttack.getValue()).booleanValue() && targettedPlayer != null && mc.world.getBlockState(playerPos = new BlockPos(targettedPlayer.getPositionVector())).getBlock() == Blocks.WEB) {
      setToAir = playerPos;
      state = mc.world.getBlockState(playerPos);
      mc.world.setBlockToAir(playerPos);
    } 
    for (BlockPos pos : BlockUtil.possiblePlacePositions(((Float)this.placeRange.getValue()).floatValue(), ((Boolean)this.antiSurround.getValue()).booleanValue(), ((Boolean)this.oneDot15.getValue()).booleanValue())) {
      if (!BlockUtil.rayTracePlaceCheck(pos, ((this.raytrace.getValue() == Raytrace.PLACE || this.raytrace.getValue() == Raytrace.FULL) && mc.player.getDistanceSq(pos) > MathUtil.square(((Float)this.placetrace.getValue()).floatValue())), 1.0F))
        continue; 
      float selfDamage = -1.0F;
      if (DamageUtil.canTakeDamage(((Boolean)this.suicide.getValue()).booleanValue()))
        selfDamage = DamageUtil.calculateDamage(pos, (Entity)mc.player); 
      if (selfDamage + 0.5D >= EntityUtil.getHealth((Entity)mc.player) || selfDamage > ((Float)this.maxSelfPlace.getValue()).floatValue())
        continue; 
      if (targettedPlayer != null) {
        float playerDamage = DamageUtil.calculateDamage(pos, (Entity)targettedPlayer);
        if (((Boolean)this.calcEvenIfNoDamage.getValue()).booleanValue() && (this.antiFriendPop.getValue() == AntiFriendPop.ALL || this.antiFriendPop.getValue() == AntiFriendPop.PLACE)) {
          boolean friendPop = false;
          for (EntityPlayer friend : mc.world.playerEntities) {
            if (friend == null || mc.player.equals(friend) || friend.getDistanceSq(pos) > MathUtil.square((((Float)this.range.getValue()).floatValue() + ((Float)this.placeRange.getValue()).floatValue())) || !M3dC3t.friendManager.isFriend(friend) || DamageUtil.calculateDamage(pos, (Entity)friend) <= EntityUtil.getHealth((Entity)friend) + 0.5D)
              continue; 
            friendPop = true;
          } 
          if (friendPop)
            continue; 
        } 
        if (isDoublePoppable(targettedPlayer, playerDamage) && (currentPos == null || targettedPlayer.getDistanceSq(pos) < targettedPlayer.getDistanceSq(currentPos))) {
          currentTarget = targettedPlayer;
          maxDamage = playerDamage;
          currentPos = pos;
          this.foundDoublePop = true;
          continue;
        } 
        if (this.foundDoublePop || (playerDamage <= maxDamage && (!((Boolean)this.extraSelfCalc.getValue()).booleanValue() || playerDamage < maxDamage || selfDamage >= maxSelfDamage)) || (playerDamage <= selfDamage && (playerDamage <= ((Float)this.minDamage.getValue()).floatValue() || DamageUtil.canTakeDamage(((Boolean)this.suicide.getValue()).booleanValue())) && playerDamage <= EntityUtil.getHealth((Entity)targettedPlayer)))
          continue; 
        maxDamage = playerDamage;
        currentTarget = targettedPlayer;
        currentPos = pos;
        maxSelfDamage = selfDamage;
        continue;
      } 
      float maxDamageBefore = maxDamage;
      EntityPlayer currentTargetBefore = currentTarget;
      BlockPos currentPosBefore = currentPos;
      float maxSelfDamageBefore = maxSelfDamage;
      for (EntityPlayer player : mc.world.playerEntities) {
        if (EntityUtil.isValid((Entity)player, (((Float)this.placeRange.getValue()).floatValue() + ((Float)this.range.getValue()).floatValue()))) {
          if (((Boolean)this.antiNaked.getValue()).booleanValue() && DamageUtil.isNaked(player))
            continue; 
          float playerDamage = DamageUtil.calculateDamage(pos, (Entity)player);
          if (((Boolean)this.doublePopOnDamage.getValue()).booleanValue() && isDoublePoppable(player, playerDamage) && (currentPos == null || player.getDistanceSq(pos) < player.getDistanceSq(currentPos))) {
            currentTarget = player;
            maxDamage = playerDamage;
            currentPos = pos;
            maxSelfDamage = selfDamage;
            this.foundDoublePop = true;
            if (this.antiFriendPop.getValue() != AntiFriendPop.BREAK && this.antiFriendPop.getValue() != AntiFriendPop.PLACE)
              continue; 
            break;
          } 
          if (this.foundDoublePop || (playerDamage <= maxDamage && (!((Boolean)this.extraSelfCalc.getValue()).booleanValue() || playerDamage < maxDamage || selfDamage >= maxSelfDamage)) || (playerDamage <= selfDamage && (playerDamage <= ((Float)this.minDamage.getValue()).floatValue() || DamageUtil.canTakeDamage(((Boolean)this.suicide.getValue()).booleanValue())) && playerDamage <= EntityUtil.getHealth((Entity)player)))
            continue; 
          maxDamage = playerDamage;
          currentTarget = player;
          currentPos = pos;
          maxSelfDamage = selfDamage;
          continue;
        } 
        if ((this.antiFriendPop.getValue() != AntiFriendPop.ALL && this.antiFriendPop.getValue() != AntiFriendPop.PLACE) || player == null || player.getDistanceSq(pos) > MathUtil.square((((Float)this.range.getValue()).floatValue() + ((Float)this.placeRange.getValue()).floatValue())) || !M3dC3t.friendManager.isFriend(player) || DamageUtil.calculateDamage(pos, (Entity)player) <= EntityUtil.getHealth((Entity)player) + 0.5D)
          continue; 
        maxDamage = maxDamageBefore;
        currentTarget = currentTargetBefore;
        currentPos = currentPosBefore;
        maxSelfDamage = maxSelfDamageBefore;
      } 
    } 
    if (setToAir != null) {
      mc.world.setBlockState(setToAir, state);
      this.webPos = currentPos;
    } 
    target = currentTarget;
    this.currentDamage = maxDamage;
    this.placePos = currentPos;
  }
  
  private EntityPlayer getTarget(boolean unsafe) {
    EntityOtherPlayerMP entityOtherPlayerMP;
    if (this.targetMode.getValue() == Target.DAMAGE)
      return null; 
    EntityPlayer currentTarget = null;
    for (EntityPlayer player : mc.world.playerEntities) {
      if (EntityUtil.isntValid((Entity)player, (((Float)this.placeRange.getValue()).floatValue() + ((Float)this.range.getValue()).floatValue())) || (((Boolean)this.antiNaked.getValue()).booleanValue() && DamageUtil.isNaked(player)) || (unsafe && EntityUtil.isSafe((Entity)player)))
        continue; 
      if (((Integer)this.minArmor.getValue()).intValue() > 0 && DamageUtil.isArmorLow(player, ((Integer)this.minArmor.getValue()).intValue())) {
        currentTarget = player;
        break;
      } 
      if (currentTarget == null) {
        currentTarget = player;
        continue;
      } 
      if (mc.player.getDistanceSq((Entity)player) >= mc.player.getDistanceSq((Entity)currentTarget))
        continue; 
      currentTarget = player;
    } 
    if (unsafe && currentTarget == null)
      return getTarget(false); 
    if (((Boolean)this.predictPos.getValue()).booleanValue() && currentTarget != null) {
      currentTarget.getUniqueID();
      GameProfile profile = new GameProfile(currentTarget.getUniqueID(), currentTarget.getName());
      EntityOtherPlayerMP newTarget = new EntityOtherPlayerMP((World)mc.world, profile);
      Vec3d extrapolatePosition = MathUtil.extrapolatePlayerPosition(currentTarget, ((Integer)this.predictTicks.getValue()).intValue());
      newTarget.copyLocationAndAnglesFrom((Entity)currentTarget);
      newTarget.posX = extrapolatePosition.x;
      newTarget.posY = extrapolatePosition.y;
      newTarget.posZ = extrapolatePosition.z;
      newTarget.setHealth(EntityUtil.getHealth((Entity)currentTarget));
      newTarget.inventory.copyInventory(currentTarget.inventory);
      entityOtherPlayerMP = newTarget;
    } 
    return (EntityPlayer)entityOtherPlayerMP;
  }
  
  private void breakCrystal() {
    if (((Boolean)this.explode.getValue()).booleanValue() && this.breakTimer.passedMs(((Integer)this.breakDelay.getValue()).intValue()) && (this.switchMode.getValue() == Switch.ALWAYS || this.mainHand || this.offHand))
      if (((Integer)this.packets.getValue()).intValue() == 1 && this.efficientTarget != null) {
        if (((Boolean)this.justRender.getValue()).booleanValue()) {
          doFakeSwing();
          return;
        } 
        if (((Boolean)this.syncedFeetPlace.getValue()).booleanValue() && ((Boolean)this.gigaSync.getValue()).booleanValue() && this.syncedCrystalPos != null && this.damageSync.getValue() != DamageSync.NONE)
          return; 
        rotateTo(this.efficientTarget);
        attackEntity(this.efficientTarget);
        this.breakTimer.reset();
      } else if (!this.attackList.isEmpty()) {
        if (((Boolean)this.justRender.getValue()).booleanValue()) {
          doFakeSwing();
          return;
        } 
        if (((Boolean)this.syncedFeetPlace.getValue()).booleanValue() && ((Boolean)this.gigaSync.getValue()).booleanValue() && this.syncedCrystalPos != null && this.damageSync.getValue() != DamageSync.NONE)
          return; 
        for (int i = 0; i < ((Integer)this.packets.getValue()).intValue(); i++) {
          Entity entity = this.attackList.poll();
          if (entity != null) {
            rotateTo(entity);
            attackEntity(entity);
          } 
        } 
        this.breakTimer.reset();
      }  
  }
  
  private void attackEntity(Entity entity) {
    if (entity != null)
      if (((Integer)this.eventMode.getValue()).intValue() == 2 && this.threadMode.getValue() == ThreadMode.NONE && ((Boolean)this.rotateFirst.getValue()).booleanValue() && this.rotate.getValue() != Rotate.OFF) {
        this.packetUseEntities.add(new CPacketUseEntity(entity));
      } else {
        EntityUtil.attackEntity(entity, ((Boolean)this.sync.getValue()).booleanValue(), ((Boolean)this.breakSwing.getValue()).booleanValue());
        EntityUtil.OffhandAttack(entity, ((Boolean)this.attackOppositeHand.getValue()).booleanValue(), ((Boolean)this.attackOppositeHand.getValue()).booleanValue());
        BrokenPos.add((new BlockPos(entity.getPositionVector())).down());
      }  
  }
  
  private void doFakeSwing() {
    if (((Boolean)this.fakeSwing.getValue()).booleanValue())
      EntityUtil.swingArmNoPacket(EnumHand.MAIN_HAND, (EntityLivingBase)mc.player); 
  }
  
  private void manualBreaker() {
    if (this.rotate.getValue() != Rotate.OFF && ((Integer)this.eventMode.getValue()).intValue() != 2 && this.rotating)
      if (this.didRotation) {
        mc.player.rotationPitch = (float)(mc.player.rotationPitch + 4.0E-4D);
        this.didRotation = false;
      } else {
        mc.player.rotationPitch = (float)(mc.player.rotationPitch - 4.0E-4D);
        this.didRotation = true;
      }  
    RayTraceResult result;
    if ((this.offHand || this.mainHand) && ((Boolean)this.manual.getValue()).booleanValue() && this.manualTimer.passedMs(((Integer)this.manualBreak.getValue()).intValue()) && Mouse.isButtonDown(1) && mc.player.getHeldItemOffhand().getItem() != Items.GOLDEN_APPLE && mc.player.inventory.getCurrentItem().getItem() != Items.GOLDEN_APPLE && mc.player.inventory.getCurrentItem().getItem() != Items.BOW && mc.player.inventory.getCurrentItem().getItem() != Items.EXPERIENCE_BOTTLE && (result = mc.objectMouseOver) != null) {
      Entity entity;
      BlockPos mousePos;
      switch (result.typeOfHit) {
        case OFF:
          entity = result.entityHit;
          if (!(entity instanceof EntityEnderCrystal))
            break; 
          EntityUtil.attackEntity(entity, ((Boolean)this.sync.getValue()).booleanValue(), ((Boolean)this.breakSwing.getValue()).booleanValue());
          EntityUtil.OffhandAttack(entity, ((Boolean)this.attackOppositeHand.getValue()).booleanValue(), ((Boolean)this.attackOppositeHand.getValue()).booleanValue());
          this.manualTimer.reset();
          break;
        case PLACE:
          mousePos = mc.objectMouseOver.getBlockPos().up();
          for (Entity target : mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(mousePos))) {
            if (!(target instanceof EntityEnderCrystal))
              continue; 
            EntityUtil.attackEntity(target, ((Boolean)this.sync.getValue()).booleanValue(), ((Boolean)this.breakSwing.getValue()).booleanValue());
            EntityUtil.OffhandAttack(target, ((Boolean)this.attackOppositeHand.getValue()).booleanValue(), ((Boolean)this.attackOppositeHand.getValue()).booleanValue());
            this.manualTimer.reset();
          } 
          break;
      } 
    } 
  }
  
  private void rotateTo(Entity entity) {
    float[] angle;
    switch ((Rotate)this.rotate.getValue()) {
      case OFF:
        this.rotating = false;
        break;
      case BREAK:
      case ALL:
        angle = MathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), entity.getPositionVector());
        if (((Integer)this.eventMode.getValue()).intValue() == 2 && this.threadMode.getValue() == ThreadMode.NONE) {
          M3dC3t.rotationManager.setPlayerRotations(angle[0], angle[1]);
          break;
        } 
        this.yaw = angle[0];
        this.pitch = angle[1];
        this.rotating = true;
        break;
    } 
  }
  
  private void rotateToPos(BlockPos pos) {
    float[] angle;
    switch ((Rotate)this.rotate.getValue()) {
      case OFF:
        this.rotating = false;
        break;
      case PLACE:
      case ALL:
        angle = MathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d((pos.getX() + 0.5F), (pos.getY() - 0.5F), (pos.getZ() + 0.5F)));
        if (((Integer)this.eventMode.getValue()).intValue() == 2 && this.threadMode.getValue() == ThreadMode.NONE) {
          M3dC3t.rotationManager.setPlayerRotations(angle[0], angle[1]);
          break;
        } 
        this.yaw = angle[0];
        this.pitch = angle[1];
        this.rotating = true;
        break;
    } 
  }
  
  private boolean isDoublePoppable(EntityPlayer player, float damage) {
    float health;
    if (((Boolean)this.doublePop.getValue()).booleanValue() && (health = EntityUtil.getHealth((Entity)player)) <= ((Double)this.popHealth.getValue()).doubleValue() && damage > health + 0.5D && damage <= ((Float)this.popDamage.getValue()).floatValue()) {
      Timer timer = this.totemPops.get(player);
      return (timer == null || timer.passedMs(((Integer)this.popTime.getValue()).intValue()));
    } 
    return false;
  }
  
  private boolean isValid(Entity entity) {
    return (entity != null && mc.player.getDistanceSq(entity) <= MathUtil.square(((Float)this.breakRange.getValue()).floatValue()) && (this.raytrace.getValue() == Raytrace.NONE || this.raytrace.getValue() == Raytrace.PLACE || mc.player.canEntityBeSeen(entity) || (!mc.player.canEntityBeSeen(entity) && mc.player.getDistanceSq(entity) <= MathUtil.square(((Float)this.breaktrace.getValue()).floatValue()))));
  }
  
  private boolean isEligableForFeetSync(EntityPlayer player, BlockPos pos) {
    if (((Boolean)this.holySync.getValue()).booleanValue()) {
      BlockPos playerPos = new BlockPos(player.getPositionVector());
      EnumFacing[] arrayOfEnumFacing;
      int i;
      byte b;
      for (arrayOfEnumFacing = EnumFacing.values(), i = arrayOfEnumFacing.length, b = 0; b < i; ) {
        EnumFacing facing = arrayOfEnumFacing[b];
        if (facing == EnumFacing.DOWN || facing == EnumFacing.UP || !pos.equals(playerPos.down().offset(facing))) {
          b++;
          continue;
        } 
        return true;
      } 
      return false;
    } 
    return true;
  }
  
  public enum PredictTimer {
    NONE, BREAK, PREDICT;
  }
  
  public enum AntiFriendPop {
    NONE, PLACE, BREAK, ALL;
  }
  
  public enum ThreadMode {
    NONE, POOL, SOUND, WHILE;
  }
  
  public enum AutoSwitch {
    NONE, TOGGLE, ALWAYS, SILENT;
  }
  
  public enum Raytrace {
    NONE, PLACE, BREAK, FULL;
  }
  
  public enum Switch {
    ALWAYS, BREAKSLOT, CALC;
  }
  
  public enum Logic {
    BREAKPLACE, PLACEBREAK;
  }
  
  public enum Target {
    CLOSEST, UNSAFE, DAMAGE;
  }
  
  public enum Rotate {
    OFF, PLACE, BREAK, ALL;
  }
  
  public enum DamageSync {
    NONE, PLACE, BREAK;
  }
  
  public enum Settings {
    PLACE, BREAK, RENDER, MISC, DEV;
  }
  
  public enum RenderMode {
    STATIC, FADE, GLIDE;
  }
  
  public static class PlaceInfo {
    private final BlockPos pos;
    
    private final boolean offhand;
    
    private final boolean placeSwing;
    
    private final boolean exactHand;
    
    private final boolean silent;
    
    public PlaceInfo(BlockPos pos, boolean offhand, boolean placeSwing, boolean exactHand, boolean silent) {
      this.pos = pos;
      this.offhand = offhand;
      this.placeSwing = placeSwing;
      this.exactHand = exactHand;
      this.silent = silent;
    }
    
    public void runPlace() {
      BlockUtil.placeCrystalOnBlock(this.pos, this.offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, this.placeSwing, this.exactHand, this.silent);
    }
  }
  
  private static class RAutoCrystal implements Runnable {
    private static RAutoCrystal instance;
    
    private AutoCrystal autoCrystal;
    
    public static RAutoCrystal getInstance(AutoCrystal autoCrystal) {
      if (instance == null) {
        instance = new RAutoCrystal();
        instance.autoCrystal = autoCrystal;
      } 
      return instance;
    }
    
    public void run() {
      if (this.autoCrystal.threadMode.getValue() == AutoCrystal.ThreadMode.WHILE) {
        while (this.autoCrystal.isOn() && this.autoCrystal.threadMode.getValue() == AutoCrystal.ThreadMode.WHILE) {
          while (M3dC3t.eventManager.ticksOngoing());
          if (this.autoCrystal.shouldInterrupt.get()) {
            this.autoCrystal.shouldInterrupt.set(false);
            this.autoCrystal.syncroTimer.reset();
            this.autoCrystal.thread.interrupt();
            break;
          } 
          this.autoCrystal.threadOngoing.set(true);
          this.autoCrystal.doAutoCrystal();
          this.autoCrystal.threadOngoing.set(false);
          try {
            Thread.sleep(((Integer)this.autoCrystal.threadDelay.getValue()).intValue());
          } catch (InterruptedException e) {
            this.autoCrystal.thread.interrupt();
            e.printStackTrace();
          } 
        } 
      } else if (this.autoCrystal.threadMode.getValue() != AutoCrystal.ThreadMode.NONE && this.autoCrystal.isOn()) {
        while (M3dC3t.eventManager.ticksOngoing());
        this.autoCrystal.threadOngoing.set(true);
        this.autoCrystal.doAutoCrystal();
        this.autoCrystal.threadOngoing.set(false);
      } 
    }
  }
  
  private class RenderPos {
    private BlockPos renderPos;
    
    private float renderTime;
    
    public RenderPos(BlockPos pos, float time) {
      this.renderPos = pos;
      this.renderTime = time;
    }
    
    public BlockPos getPos() {
      return this.renderPos;
    }
    
    public float getRenderTime() {
      return this.renderTime;
    }
    
    public void setPos(BlockPos pos) {
      this.renderPos = pos;
    }
    
    public void setRenderTime(float time) {
      this.renderTime = time;
    }
  }
}
