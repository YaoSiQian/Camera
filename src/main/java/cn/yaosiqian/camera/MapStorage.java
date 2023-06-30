package cn.yaosiqian.camera;

import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MapStorage {

    public static void store(int id, byte[][] data) {
        // String serializedDataSimple = serializeMapDataSimple(data);
        String serializedData = serializeMapDataCompressed(data);

        File file = new File(Camera.getInstance().getDataFolder(), "maps/map_" + id + ".txt");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e1) {
                Bukkit.getLogger().severe("Error creating map file for mapId: " + id);
                e1.printStackTrace();
            }
        }

        try {
            Files.write(Paths.get(file.getPath()), serializedData.getBytes());
        } catch (IOException e) {
            Bukkit.getLogger().severe("Error writing to mapId: " + id);
            e.printStackTrace();
        }
    }

    public static String serializeMapDataCompressed(byte[][] data) {
        StringBuilder outputString = new StringBuilder();
        int count = 1;
        for(int i = 0; i < (128*128); i++) {
            int row =  i / 128;
            int column = i % 128;
            int rownext = (i+1) / 128;
            int colnext = (i+1) % 128;

            count = 1;
            while(i < (128*128) - 1 && data[row][column] == data[rownext][colnext]) {
                count++;
                i++;
                row =  i / 128;
                column = i % 128;
                rownext = (i+1) / 128;
                colnext = (i+1) % 128;
            }

            outputString.append(data[row][column]).append("_").append(count).append(",");
        }
        return outputString.toString();
    }

    public static String serializeMapDataSimple(byte[][] data) {
        StringBuilder builder = new StringBuilder();
        for (byte[] datum : data) {
            for (byte color : datum) {
                String colorString = String.format("%s", color);
                builder.append(colorString).append("_").append(1).append(",");
            }
        }
        return builder.toString();
    }
}