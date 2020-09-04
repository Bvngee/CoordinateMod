package com.gmail.nystromjp;

import com.gmail.nystromjp.gui.CoordinateGui;
import com.gmail.nystromjp.gui.CoordinateScreen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import org.lwjgl.glfw.GLFW;

import java.io.File;

public class CoordinateMod implements ModInitializer {

    public static boolean savesIsOpen;
    public String minecraftPath = FabricLoader.getInstance().getGameDir().toString();

    @Override
    public void onInitialize() {
        System.out.println("Initializing Coordinate Mod...");

        //create the .minecraft/coordinates directory
        File file = new File(minecraftPath + "\\coordinates");
        file.mkdir();

        //create keybinding
        KeyBinding rKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.coordinate.gui", GLFW.GLFW_KEY_R, "Coordinate Mod"));

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if(MinecraftClient.getInstance().player!=null){
                while (rKey.wasPressed()) {
                    MinecraftClient.getInstance().openScreen(new CoordinateScreen(new CoordinateGui()));
                }
            }
        });
    }
}
