package com.ai;

import com.ai.dataSet.NormalizeData;
import com.ai.neuralNetwork.NeuralNetwork;

public class Ensemble {
    private static final byte COUNT = 10;
    private final NeuralNetwork[] neuralNetworks;

    public Ensemble(String way){
        neuralNetworks = new NeuralNetwork[COUNT];
        String weight;
        for(int i = 0; i < COUNT; i++){
            weight = way + "/weight" + i + ".csv";
            neuralNetworks[i] = new NeuralNetwork(weight);
        }
    }

    public double counting(double[] in){
        double res = 0;
        for(int i = 0; i < COUNT; i++){
            res += neuralNetworks[i].counting(in)[0];
        }
        return res / COUNT;
    }

    public double counting(String in){
        double[] doubleData = new double[7];
        boolean status = false;
        String[] splitLine = in.split(",");
        for (int i = 1; i < splitLine.length; i++){
            double normResult = NormalizeData.normalize(splitLine[i], i);
            if (normResult == -1) {
                status = true; break;
            }
            doubleData[i - 1] = normResult;
        }
        if(status) return -1;

        return counting(doubleData);
    }
}
