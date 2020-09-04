package com.gmail.nystromjp.gui;

import com.gmail.nystromjp.CoordinateMod;
import com.gmail.nystromjp.ListItem;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class EditItemGui extends LightweightGuiDescription {

    public static WTextField name;

    public static WTextField xCoords;
    public static WTextField yCoords;
    public static WTextField zCoords;

    private List<ListItem> listItemArray;
    public int index;


    //For editing a specific item in the list
    public EditItemGui(int i, String oldName) {
        index = i;
        listItemArray = SavesGui.listItemArray;

        System.out.println(new File(SavesGui.coordinatesPath + "\\" + oldName + ".txt").getPath());
        System.out.println(oldName);

        WPlainPanel root = new WPlainPanel();
        setRootPanel(root);
        root.setSize(300, 60);

        WLabel label = new WLabel(new TranslatableText("Edit Save:"));
        label.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(label, 0, 0, 300, 10);

        name = new WTextField(new LiteralText("New Name"));
        root.add(name, 0, 20, 120, 10);
        name.setText(listItemArray.get(i).name);

        WText xText = new WText(new TranslatableText("X:"));
        root.add(xText, 120, 25, 10, 10);
        xCoords = new WTextField(new TranslatableText("New X"));
        xCoords.setMaxLength(7);
        root.add(xCoords, 130, 20, 50, 10);
        xCoords.setText(String.valueOf(listItemArray.get(i).xCoordinate));

        WText yText = new WText(new TranslatableText("Y:"));
        root.add(yText, 180, 25, 10, 10);
        yCoords = new WTextField(new TranslatableText("New Y"));
        yCoords.setMaxLength(7);
        root.add(yCoords, 190, 20, 50, 10);
        yCoords.setText(String.valueOf(listItemArray.get(i).yCoordinate));

        WText zText = new WText(new TranslatableText("Z:"));
        root.add(zText, 240, 25, 10, 10);
        zCoords = new WTextField(new TranslatableText("New Z"));
        zCoords.setMaxLength(7);
        root.add(zCoords, 250, 20, 50, 10);
        zCoords.setText(String.valueOf(listItemArray.get(i).zCoordinate));


        WButton cancel = new WButton(new TranslatableText("Cancel"));
        root.add(cancel, 0, 50, 150, 10);
        cancel.setOnClick(this::cancel);
        WButton save = new WButton(new TranslatableText("Save"));
        root.add(save, 150, 50, 150, 10);
        save.setOnClick(() -> this.save(oldName));

        root.validate(this);
    }

    //For adding an item to the list
    public EditItemGui(){
        listItemArray = SavesGui.listItemArray;

        WPlainPanel root = new WPlainPanel();
        setRootPanel(root);
        root.setSize(300, 60);

        WLabel label = new WLabel(new TranslatableText("Add Coordinates:"));
        label.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(label, 0, 0, 300, 10);

        name = new WTextField(new LiteralText("New Name"));
        root.add(name, 0, 20, 120, 10);

        WText xText = new WText(new TranslatableText("X:"));
        root.add(xText, 120, 25, 10, 10);
        xCoords = new WTextField(new TranslatableText("New X"));
        xCoords.setMaxLength(7);
        root.add(xCoords, 130, 20, 50, 10);

        WText yText = new WText(new TranslatableText("Y:"));
        root.add(yText, 180, 25, 10, 10);
        yCoords = new WTextField(new TranslatableText("New Y"));
        yCoords.setMaxLength(7);
        root.add(yCoords, 190, 20, 50, 10);

        WText zText = new WText(new TranslatableText("Z:"));
        root.add(zText, 240, 25, 10, 10);
        zCoords = new WTextField(new TranslatableText("New Z"));
        zCoords.setMaxLength(7);
        root.add(zCoords, 250, 20, 50, 10);


        WButton cancel = new WButton(new TranslatableText("Cancel"));
        root.add(cancel, 0, 50, 150, 10);
        cancel.setOnClick(this::cancel);
        WButton save = new WButton(new TranslatableText("Add"));
        root.add(save, 150, 50, 150, 10);
        save.setOnClick(this::add);

        root.validate(this);
    }

    //For saving a set of coordinates from the calculator to the list
    public EditItemGui(int x, int y, int z){
        listItemArray = SavesGui.listItemArray;

        WPlainPanel root = new WPlainPanel();
        setRootPanel(root);
        root.setSize(300, 60);

        WLabel label = new WLabel(new TranslatableText("Add Coordinates:"));
        label.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(label, 0, 0, 300, 10);

        name = new WTextField(new LiteralText("New Name"));
        root.add(name, 0, 20, 120, 10);

        WText xText = new WText(new TranslatableText("X:"));
        root.add(xText, 120, 25, 10, 10);
        xCoords = new WTextField(new TranslatableText("New X"));
        xCoords.setMaxLength(7);
        root.add(xCoords, 130, 20, 50, 10);
        xCoords.setText(String.valueOf(x));

        WText yText = new WText(new TranslatableText("Y:"));
        root.add(yText, 180, 25, 10, 10);
        yCoords = new WTextField(new TranslatableText("New Y"));
        yCoords.setMaxLength(7);
        root.add(yCoords, 190, 20, 50, 10);
        yCoords.setText(String.valueOf(y));

        WText zText = new WText(new TranslatableText("Z:"));
        root.add(zText, 240, 25, 10, 10);
        zCoords = new WTextField(new TranslatableText("New Z"));
        zCoords.setMaxLength(7);
        root.add(zCoords, 250, 20, 50, 10);
        zCoords.setText(String.valueOf(z));


        WButton cancel = new WButton(new TranslatableText("Cancel"));
        root.add(cancel, 0, 50, 150, 10);
        cancel.setOnClick(this::cancel);
        WButton save = new WButton(new TranslatableText("Save"));
        root.add(save, 150, 50, 150, 10);
        save.setOnClick(this::add);

        root.validate(this);
    }

    public Runnable cancel(){
        CoordinateMod.savesIsOpen = true;
        MinecraftClient.getInstance().openScreen(new CoordinateScreen(new SavesGui()));
        return null;
    }
    public Runnable save(String oldName){
        renameFile(SavesGui.coordinatesPath + "\\" + oldName + ".txt", name.getText());
        writeToFile(new File(SavesGui.coordinatesPath + "\\" + name.getText() + ".txt"), new String[]{xCoords.getText(), yCoords.getText(), zCoords.getText()});
        CoordinateMod.savesIsOpen = true;
        MinecraftClient.getInstance().openScreen(new CoordinateScreen(new SavesGui()));
        return null;
    }
    public Runnable add(){
        createFile(name.getText());
        writeToFile(new File(SavesGui.coordinatesPath + "\\" + name.getText() + ".txt"), new String[]{xCoords.getText(), yCoords.getText(), zCoords.getText()});
        CoordinateMod.savesIsOpen = true;
        MinecraftClient.getInstance().openScreen(new CoordinateScreen(new SavesGui()));
        return null;
    }

    public void createFile(String name){
        try {
            File file = new File(SavesGui.coordinatesPath + "\\" + name + ".txt");
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(File file, String[] text) {
        try{
            FileWriter fileWriter = new FileWriter(file);
            for(int i=0;i<text.length;i++){
                fileWriter.write(text[i]);
                fileWriter.write("\n");
            }
            fileWriter.flush();
            fileWriter.close();
        }catch (IOException e){
            System.out.println("An error occured while writing.");
            e.printStackTrace();
        }
    }

    public void renameFile(String originalFilePath, String newName) {
        File oldFile = new File(originalFilePath);
        File newFile = new File(SavesGui.coordinatesPath + "\\" + newName + ".txt");
        oldFile.renameTo(newFile);
    }

}
