package com.gmail.nystromjp;

import com.gmail.nystromjp.gui.CoordinateGui;
import com.gmail.nystromjp.gui.CoordinateScreen;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.io.File;

public class CoordinateMod implements ModInitializer {

    public static boolean savesIsOpen;
    public String minecraftPath = FabricLoader.getInstance().getGameDirectory().getPath();

    @Override
    public void onInitialize() {

        //create the .minecraft/coordinates directory
        File file = new File(minecraftPath + "\\coordinates");
        file.mkdir();

        //create a keybinding
        KeyBindingRegistry.INSTANCE.addCategory("Coordinate Mod");
        FabricKeyBinding keyBinding;
        keyBinding = FabricKeyBinding.Builder.create(
                new Identifier("coordinate", "gui"),
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "Coordinate Mod"
        ).build();
        KeyBindingRegistry.INSTANCE.register(keyBinding); //peepee poopoo

        //checks if the initialization works
        System.out.println("Hi peeps!");

        //things that will happen every tick
        ClientTickCallback.EVENT.register(e ->
        {
            if(MinecraftClient.getInstance().player!=null){

                //open screen when key is pressed
                if(keyBinding.isPressed()) {
                    System.out.println("was pressed!");
                    MinecraftClient.getInstance().openScreen(new CoordinateScreen(new CoordinateGui()));
                }

                Screen currentScreen = MinecraftClient.getInstance().currentScreen;
                if(currentScreen instanceof CottonClientScreen){

                }
            }
        });
    }
}
