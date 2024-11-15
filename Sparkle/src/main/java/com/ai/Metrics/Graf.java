package com.ai.Metrics;


import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Graf extends JPanel {

    private ArrayList<int[]> resultList;

    public Graf(){

        setPreferredSize(new Dimension(540, 540));
        // Установка цвета фона
        setBackground(Color.decode("#ffffff"));
        setFocusable(true);

        String way = System.getProperty("user.dir") + "/src/main/java/com/ai/res.csv";

        resultList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(way));
            String line;
            while((line = br.readLine()) != null){
                String[] s = line.split(" ");
                resultList.add(new int[]{(int)(Double.parseDouble(s[0]) * 500) + 20, (500 - (int)(Double.parseDouble(s[1]) * 500)) + 20});
            }

            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void paint(Graphics g){
        Graphics2D drp = (Graphics2D)g;
        drp.drawLine(20, 520, 20, 20);
        drp.drawLine(20, 520, 520, 520);
        drp.drawLine(20, 520, 520, 20);
        for(int[] p : resultList){
            drp.drawLine(p[0],p[1],p[0],p[1]);
        }
    }
}
