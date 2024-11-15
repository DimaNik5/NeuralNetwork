package com.ai.Metrics;

import javax.swing.*;

public class GrafMetrics {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Метрики");
        Graf graf = new Graf();
        frame.add(graf);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);

    }
}
