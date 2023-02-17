//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.util;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.util.UUIDTypeAdapter;
import dev.madcat.m3dc3t.features.command.Command;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;
import javax.net.ssl.HttpsURLConnection;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.io.IOUtils;

public class PlayerUtil implements Util {
  private static final JsonParser PARSER = new JsonParser();
  
  public static double getBaseMoveSpeed() {
    double baseSpeed = 0.2873D;
    if (mc.player != null && mc.player.isPotionActive(Potion.getPotionById(1))) {
      int amplifier = mc.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
      baseSpeed *= 1.0D + 0.2D * (amplifier + 1);
    } 
    return baseSpeed;
  }
  
  public static boolean isMoving(EntityLivingBase entity) {
    return (entity.moveForward != 0.0F || entity.moveStrafing != 0.0F);
  }
  
  public static double[] forward(double speed) {
    float forward = mc.player.movementInput.moveForward;
    float side = mc.player.movementInput.moveStrafe;
    float yaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
    if (forward != 0.0F) {
      if (side > 0.0F) {
        yaw += ((forward > 0.0F) ? -45 : 45);
      } else if (side < 0.0F) {
        yaw += ((forward > 0.0F) ? 45 : -45);
      } 
      side = 0.0F;
      if (forward > 0.0F) {
        forward = 1.0F;
      } else if (forward < 0.0F) {
        forward = -1.0F;
      } 
    } 
    double sin = Math.sin(Math.toRadians((yaw + 90.0F)));
    double cos = Math.cos(Math.toRadians((yaw + 90.0F)));
    double posX = forward * speed * cos + side * speed * sin;
    double posZ = forward * speed * sin - side * speed * cos;
    return new double[] { posX, posZ };
  }
  
  public static String getNameFromUUID(UUID uuid) {
    try {
      lookUpName process = new lookUpName(uuid);
      Thread thread = new Thread(process);
      thread.start();
      thread.join();
      return process.getName();
    } catch (Exception e) {
      return null;
    } 
  }
  
  public static BlockPos getPlayerPos() {
    return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
  }
  
  public static String getNameFromUUID(String uuid) {
    try {
      lookUpName process = new lookUpName(uuid);
      Thread thread = new Thread(process);
      thread.start();
      thread.join();
      return process.getName();
    } catch (Exception e) {
      return null;
    } 
  }
  
  public static UUID getUUIDFromName(String name) {
    try {
      lookUpUUID process = new lookUpUUID(name);
      Thread thread = new Thread(process);
      thread.start();
      thread.join();
      return process.getUUID();
    } catch (Exception e) {
      return null;
    } 
  }
  
  public static String requestIDs(String data) {
    try {
      String query = "https://api.mojang.com/profiles/minecraft";
      URL url = new URL(query);
      HttpURLConnection conn = (HttpURLConnection)url.openConnection();
      conn.setConnectTimeout(5000);
      conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
      conn.setDoOutput(true);
      conn.setDoInput(true);
      conn.setRequestMethod("POST");
      OutputStream os = conn.getOutputStream();
      os.write(data.getBytes(StandardCharsets.UTF_8));
      os.close();
      InputStream in = new BufferedInputStream(conn.getInputStream());
      String res = convertStreamToString(in);
      in.close();
      conn.disconnect();
      return res;
    } catch (Exception e) {
      return null;
    } 
  }
  
  public static String convertStreamToString(InputStream is) {
    Scanner s = (new Scanner(is)).useDelimiter("\\A");
    return s.hasNext() ? s.next() : "/";
  }
  
  public static List<String> getHistoryOfNames(UUID id) {
    try {
      JsonArray array = getResources(new URL("https://api.mojang.com/user/profiles/" + getIdNoHyphens(id) + "/names"), "GET").getAsJsonArray();
      List<String> temp = Lists.newArrayList();
      for (JsonElement e : array) {
        JsonObject node = e.getAsJsonObject();
        String name = node.get("name").getAsString();
        long changedAt = node.has("changedToAt") ? node.get("changedToAt").getAsLong() : 0L;
        temp.add(name + "脗搂8" + (new Date(changedAt)).toString());
      } 
      Collections.sort(temp);
      return temp;
    } catch (Exception ignored) {
      return null;
    } 
  }
  
  public static String getIdNoHyphens(UUID uuid) {
    return uuid.toString().replaceAll("-", "");
  }
  
  private static JsonElement getResources(URL url, String request) throws Exception {
    return getResources(url, request, null);
  }
  
  private static JsonElement getResources(URL url, String request, JsonElement element) throws Exception {
    HttpsURLConnection connection = null;
    try {
      connection = (HttpsURLConnection)url.openConnection();
      connection.setDoOutput(true);
      connection.setRequestMethod(request);
      connection.setRequestProperty("Content-Type", "application/json");
      if (element != null) {
        DataOutputStream output = new DataOutputStream(connection.getOutputStream());
        output.writeBytes(AdvancementManager.GSON.toJson(element));
        output.close();
      } 
      Scanner scanner = new Scanner(connection.getInputStream());
      StringBuilder builder = new StringBuilder();
      while (scanner.hasNextLine()) {
        builder.append(scanner.nextLine());
        builder.append('\n');
      } 
      scanner.close();
      String json = builder.toString();
      JsonElement data = PARSER.parse(json);
      return data;
    } finally {
      if (connection != null)
        connection.disconnect(); 
    } 
  }
  
  public static class lookUpUUID implements Runnable {
    private final String name;
    
    private volatile UUID uuid;
    
    public lookUpUUID(String name) {
      this.name = name;
    }
    
    public void run() {
      NetworkPlayerInfo profile;
      try {
        ArrayList<NetworkPlayerInfo> infoMap = new ArrayList<>(((NetHandlerPlayClient)Objects.<NetHandlerPlayClient>requireNonNull(Util.mc.getConnection())).getPlayerInfoMap());
        profile = infoMap.stream().filter(networkPlayerInfo -> networkPlayerInfo.getGameProfile().getName().equalsIgnoreCase(this.name)).findFirst().orElse(null);
        assert profile != null;
        this.uuid = profile.getGameProfile().getId();
      } catch (Exception e) {
        profile = null;
      } 
      if (profile == null) {
        Command.sendMessage("Player isn't online. Looking up UUID..");
        String s = PlayerUtil.requestIDs("[\"" + this.name + "\"]");
        if (s == null || s.isEmpty()) {
          Command.sendMessage("Couldn't find player ID. Are you connected to the internet? (0)");
        } else {
          JsonElement element = (new JsonParser()).parse(s);
          if (element.getAsJsonArray().size() == 0) {
            Command.sendMessage("Couldn't find player ID. (1)");
          } else {
            try {
              String id = element.getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString();
              this.uuid = UUIDTypeAdapter.fromString(id);
            } catch (Exception e) {
              e.printStackTrace();
              Command.sendMessage("Couldn't find player ID. (2)");
            } 
          } 
        } 
      } 
    }
    
    public UUID getUUID() {
      return this.uuid;
    }
    
    public String getName() {
      return this.name;
    }
  }
  
  public static class lookUpName implements Runnable {
    private final String uuid;
    
    private final UUID uuidID;
    
    private volatile String name;
    
    public lookUpName(String input) {
      this.uuid = input;
      this.uuidID = UUID.fromString(input);
    }
    
    public lookUpName(UUID input) {
      this.uuidID = input;
      this.uuid = input.toString();
    }
    
    public void run() {
      this.name = lookUpName();
    }
    
    public String lookUpName() {
      EntityPlayer player = null;
      if (Util.mc.world != null)
        player = Util.mc.world.getPlayerEntityByUUID(this.uuidID); 
      if (player == null) {
        String url = "https://api.mojang.com/user/profiles/" + this.uuid.replace("-", "") + "/names";
        try {
          String nameJson = IOUtils.toString(new URL(url));
          if (nameJson.contains(",")) {
            List<String> names = Arrays.asList(nameJson.split(","));
            Collections.reverse(names);
            return ((String)names.get(1)).replace("{\"name\":\"", "").replace("\"", "");
          } 
          return nameJson.replace("[{\"name\":\"", "").replace("\"}]", "");
        } catch (IOException exception) {
          exception.printStackTrace();
          return null;
        } 
      } 
      return player.getName();
    }
    
    public String getName() {
      return this.name;
    }
  }
}
