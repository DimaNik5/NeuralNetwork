package com.ai;

import com.ai.dataSet.NormalizeData;
import com.ai.neuralNetwork.NeuralNetwork;

public class Ensemble {
    // Количество перцептронов
    private static final byte COUNT = 10;
    // Массив перцептронов
    private final NeuralNetwork[] neuralNetworks;

    // Конструктор, принимающий путь к папке, хранящей веса
    public Ensemble(String way){
        neuralNetworks = new NeuralNetwork[COUNT];
        String weight;
        for(int i = 0; i < COUNT; i++){
            weight = way + "weight" + i + ".csv";
            neuralNetworks[i] = new NeuralNetwork(weight);
        }
    }

    // Подсчет результата по вводимому массиву
    public double counting(double[] in){
        double res = 0;
        for(int i = 0; i < COUNT; i++){
            res += neuralNetworks[i].counting(in)[0];
        }
        return res / COUNT;
    }

    // Подсчет результата поп вводимой строчке и количество параметров в ней (берутся последние 7)
    public double counting(String in, int count){
        double[] doubleData = new double[7];
        boolean status = false;
        String[] splitLine = in.split(",");
        int len = splitLine.length;
        if(len != count) return -1;
        for (int i = count - 7; i < len; i++){
            double normResult = NormalizeData.normalize(splitLine[i], i);
            if (normResult == -1) {
                status = true; break;
            }
            doubleData[i - (count - 7)] = normResult;
        }
        if(status) return -1;

        return counting(doubleData);
    }
}
