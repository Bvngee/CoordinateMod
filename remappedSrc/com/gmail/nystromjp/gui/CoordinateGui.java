package com.gmail.nystromjp.gui;

import com.gmail.nystromjp.CoordinateMod;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

public class CoordinateGui extends LightweightGuiDescription {

    public String x, y, z;
    public String netherX, netherY, netherZ;
    public WText playerCoordinates = new WText(new TranslatableText("coordinatemod.CoordinateScreen.text.currentPlayer"));
    public WText xCoordinate;
    public WText yCoordinate;
    public WText zCoordinate;
    public WText netherCoordinates = new WText(new TranslatableText("coordinatemod.CoordinateScreen.text.correspondingNether"));
    public WText xNetherCoordinate;
    public WText yNetherCoordinate;
    public WText zNetherCoordinate;

    public CoordinateGui() {
        //create root panel
        WPlainPanel root = new WPlainPanel();
        setRootPanel(root);
        root.setSize(320, 180);

        //create and register buttons at the top to switch modes
        WButton openCalc = new WButton(new TranslatableText("coordinatemod.CoordinateScreen.button.openCalc"));
        root.add(openCalc, 160, 0, 160,10);
        openCalc.setOnClick(this::calc);

        WButton openSaves = new WButton(new TranslatableText("coordinatemod.CoordinateScreen.button.openSaves"));
        root.add(openSaves, 0, 0, 160,10);
        openSaves.setOnClick(this::saves);
        System.out.println(CoordinateMod.savesIsOpen);
        if(CoordinateMod.savesIsOpen){
            openSaves.setEnabled(false);
            openCalc.setEnabled(true);
        }else{
            openSaves.setEnabled(true);
            openCalc.setEnabled(false);
        }

        WLabel dimension = new WLabel(new TranslatableText("coordinatemod.CoordinateScreen.label.dimension"));
        //dimension.setAlignment(Alignment.CENTER);
        dimension.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(dimension, 0, 30, 320, 10);

        //check the dimension the player is in
        if(MinecraftClient.getInstance().player.world.getRegistryKey() == World.OVERWORLD) {
            dimension.setText(new TranslatableText("coordinatemod.CoordinateScreen.label.dimension", "Overworld"));
            playerCoordinates.setText(new TranslatableText("coordinatemod.CoordinateScreen.text.currentPlayer"));
            netherCoordinates.setText(new TranslatableText("coordinatemod.CoordinateScreen.text.correspondingNether"));
            //set overworld coordinates
            x = String.valueOf((int) MinecraftClient.getInstance().player.getX());
            y = String.valueOf((int) MinecraftClient.getInstance().player.getY());
            z = String.valueOf((int) MinecraftClient.getInstance().player.getZ());
            //set nether coordinates
            netherX = String.valueOf((int) MinecraftClient.getInstance().player.getX() / 8);
            netherY = String.valueOf((int) MinecraftClient.getInstance().player.getY() / 8);
            netherZ = String.valueOf((int) MinecraftClient.getInstance().player.getZ() / 8);
        }else if(MinecraftClient.getInstance().player.world.getRegistryKey() == World.NETHER){
            dimension.setText(new TranslatableText("coordinatemod.CoordinateScreen.label.dimension", "Nether"));
            playerCoordinates.setText(new TranslatableText("coordinatemod.CoordinateScreen.text.currentNether"));
            netherCoordinates.setText(new TranslatableText("coordinatemod.CoordinateScreen.text.correspondingOverworld"));
            //set overworld coordinates
            x = String.valueOf((int) MinecraftClient.getInstance().player.getX());
            y = String.valueOf((int) MinecraftClient.getInstance().player.getY());
            z = String.valueOf((int) MinecraftClient.getInstance().player.getZ());
            //set nether coordinates
            netherX = String.valueOf((int) MinecraftClient.getInstance().player.getX() * 8);
            netherY = String.valueOf((int) MinecraftClient.getInstance().player.getY() * 8);
            netherZ = String.valueOf((int) MinecraftClient.getInstance().player.getZ() * 8);
        }else if(MinecraftClient.getInstance().player.world.getRegistryKey() == World.END){
            dimension.setText(new TranslatableText("coordinatemod.CoordinateScreen.label.dimension", "The End"));
            playerCoordinates.setText(new TranslatableText("coordinatemod.CoordinateScreen.text.currentEnd"));
            netherCoordinates.setText(new TranslatableText("coordinatemod.CoordinateScreen.text.noCorresponding"));
            //set overworld coordinates
            x = String.valueOf((int) MinecraftClient.getInstance().player.getX());
            y = String.valueOf((int) MinecraftClient.getInstance().player.getY());
            z = String.valueOf((int) MinecraftClient.getInstance().player.getZ());
            //set nether coordinates
            netherX = "---";
            netherY = "---";
            netherZ = "---";
        }

        //draw label and coordinates for overworld
        root.add(playerCoordinates, 0, 40, 320, 10);

        xCoordinate = new WText(new TranslatableText("X: "+x));
        root.add(xCoordinate, 10, 50, 310, 10);

        yCoordinate = new WText(new TranslatableText("Y: "+y));
        root.add(yCoordinate, 10, 60, 310, 10);

        zCoordinate = new WText(new TranslatableText("Z: "+z));
        root.add(zCoordinate, 10, 70, 310, 10);

        WButton overworldSave = new WButton(new TranslatableText("Save Coordinates"));
        overworldSave.setOnClick(() -> this.saveItem(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z)));
        root.add(overworldSave, 0, 80, 100, 10);


        //draw label and coordinates for nether side
        root.add(netherCoordinates, 0, 120, 320, 10);

        xNetherCoordinate = new WText(new TranslatableText("X: "+netherX));
        root.add(xNetherCoordinate, 10, 130, 310, 10);

        yNetherCoordinate = new WText(new TranslatableText("Y: "+netherY));
        root.add(yNetherCoordinate, 10, 140, 310, 10);

        zNetherCoordinate = new WText(new TranslatableText("Z: "+netherZ));
        root.add(zNetherCoordinate, 10, 150, 310, 10);

        WButton netherSave = new WButton(new TranslatableText("Save Coordinates"));
        root.add(netherSave, 0, 160,100, 10);
        netherSave.setOnClick(() -> this.saveItem(Integer.parseInt(netherX), Integer.parseInt(netherY), Integer.parseInt(netherZ)));


        root.validate(this);

    }

    public Runnable saves() {
        CoordinateMod.savesIsOpen = true;
        MinecraftClient.getInstance().openScreen(new CoordinateScreen(new SavesGui()));
        return null;
    }
    public Runnable calc() {
        CoordinateMod.savesIsOpen = false;
        MinecraftClient.getInstance().openScreen(new CoordinateScreen(new CoordinateGui()));
        return null;
    }
    public Runnable saveItem(int x, int y, int z){
        CoordinateMod.savesIsOpen = false;
        MinecraftClient.getInstance().openScreen(new CoordinateScreen(new EditItemGui(x, y, z)));
        return null;
    }

}