package com.gmail.nystromjp.gui;

import com.gmail.nystromjp.CoordinateMod;
import com.gmail.nystromjp.ListItem;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WListPanel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WText;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;

public class SavesGui extends LightweightGuiDescription {

    public static List<ListItem> listItemArray = new ArrayList<ListItem>();
    public static String minecraftPath = FabricLoader.getInstance().getGameDirectory().getPath();
    public static String coordinatesPath = minecraftPath + "\\coordinates";

    public SavesGui() {
        listItemArray = new ArrayList<ListItem>();

        //Creating a File object for directory
        File directoryPath = new File(coordinatesPath);
        //List of all files and directories
        String[] contents = directoryPath.list();
        for(int i=0; i<contents.length; i++) {
            //do stuff with all of the files in the directory:
            List<String> list = readFromFile(new File(coordinatesPath+"\\"+contents[i]));
            listItemArray.add(new ListItem(contents[i].replace(".txt", ""), list.get(0), list.get(1), list.get(2)));
        }

        //create root panel
        WPlainPanel root = new WPlainPanel();
        setRootPanel(root);
        root.setSize(320, 180);

        //create and register buttons at the top to switch modes
        WButton openCalc = new WButton(new TranslatableText("coordinatemod.CoordinateScreen.button.openCalc"));
        root.add(openCalc, 160, 0, 160, 20);
        openCalc.setOnClick(this::calc);

        WButton openSaves = new WButton(new TranslatableText("coordinatemod.CoordinateScreen.button.openSaves"));
        root.add(openSaves, 0, 0, 160, 20);
        openSaves.setOnClick(this::saves);
        System.out.println(CoordinateMod.savesIsOpen);
        if(CoordinateMod.savesIsOpen){
            openSaves.setEnabled(false);
            openCalc.setEnabled(true);
        }else{
            openSaves.setEnabled(true);
            openCalc.setEnabled(false);
        }

        WButton addItem = new WButton((new TranslatableText("coordinatemod.CoordinateScreen.button.addItem")));
        root.add(addItem, 0, 20, 320, 20);
        addItem.setOnClick(this::addItem);


        //create the BiConsumer for the list items
        BiConsumer<ListItem, ListTemplate> configurator = (s, panel) -> {

            panel.name.setText(new LiteralText(s.name));
            panel.xCoords.setText(new LiteralText(s.xCoordinate));
            panel.yCoords.setText(new LiteralText(s.yCoordinate));
            panel.zCoords.setText(new LiteralText(s.zCoordinate));

            int index = listItemArray.indexOf(s);
            panel.editItemButton.setOnClick(() -> this.openEditGui(index, panel.name.getText().toString()));

            panel.deleteItemButton.setOnClick(() -> this.deleteItem(new File(coordinatesPath+"\\"+contents[index])));
            System.out.println(coordinatesPath+"\\"+contents[index]);
        };

        //create and add the list
        WListPanel<ListItem, ListTemplate> list = new WListPanel<>(listItemArray, ListTemplate::new, configurator);
        list.setListItemHeight(20);
        root.add(list, 0, 40, 320, 140);


        root.validate(this);

        }

    public class ListTemplate extends WPlainPanel {

        //all of the objects that go into one element of the list
        WText name = new WText(new LiteralText("Name"));

        WText xText = new WText(new TranslatableText("X:"));
        WText xCoords = new WText(new TranslatableText("X Coordinate"));

        WText yText = new WText(new TranslatableText("Y:"));
        WText yCoords = new WText(new TranslatableText("Y Coordinate"));

        WText zText = new WText(new TranslatableText("Z:"));
        WText zCoords = new WText(new TranslatableText("Z Coordinate"));

        WButton deleteItemButton = new WButton(new TranslatableText("Del."));
        WButton editItemButton = new WButton(new TranslatableText("Edit"));



        public ListTemplate() {
            this.add(name, 0, 5, 120, 10);
            //name.setMaxLength(22);

            this.add(xText, 120, 5, 10, 10);
            this.add(xCoords, 130, 5, 40, 10);

            this.add(yText, 170, 5, 10, 10);
            this.add(yCoords, 180, 5, 40, 10);

            this.add(zText, 220, 5, 10, 10);
            this.add(zCoords, 230, 5, 40, 10);

            this.add(deleteItemButton, 270, 0, 20, 10);
            this.add(editItemButton, 290, 0, 20, 10);

            this.setSize(320, 10);

        }
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
    public Runnable openEditGui(int i, String oldName) {
        System.out.println(oldName);
        MinecraftClient.getInstance().openScreen(new CoordinateScreen(new EditItemGui(i, oldName)));
        CoordinateMod.savesIsOpen = false;
        return null;
    }
    public Runnable deleteItem(File file){
        try{
            Files.delete(file.toPath());
        }catch (IOException e){
            e.printStackTrace();
        }
        MinecraftClient.getInstance().openScreen(new CoordinateScreen(new SavesGui()));
        return null;
    }
    public Runnable addItem(){
        MinecraftClient.getInstance().openScreen(new CoordinateScreen(new EditItemGui()));
        CoordinateMod.savesIsOpen = false;
        return null;
    }

    public static List<String> readFromFile(File file){
        List<String> list = new ArrayList<>();
        try {
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()){
                list.add(reader.nextLine());
            }
            reader.close();
        }catch(FileNotFoundException fileNotFoundException){
            fileNotFoundException.printStackTrace();
            System.out.println("An error has occured.");
        }
        return list;
    }


}