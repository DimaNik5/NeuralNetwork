package com.ai;

import com.ai.dataSet.DataSet;
import com.ai.neuralNetwork.NeuralNetwork;
import com.ai.neuralNetwork.train.TrainingNeuralNetwork;

import java.util.Arrays;

import static com.ai.Training.train;

public class Main {

    static boolean newNetwork = false;
    // true - при открытии через IDE, false - при открытии через исполняемый файл
    static boolean InIDE = true;
    public static void main(String[] args) {
        // true - при открытии через IDE, false - при открытии через исполняемый файл
        String way = System.getProperty("user.dir") + ((InIDE) ? "/src/main/java/com/ai" : "");

        String dataset = way + "/train.csv";
        DataSet ds = new DataSet(dataset);
        ds.loadFromCsv();

        String weight = way + "/weight.csv";

        /* TrainingNeuralNetwork tnn;
        if(newNetwork){
            tnn = new TrainingNeuralNetwork(new int[]{7, 16, 15, 14, 13, 12, 11, 10, 1},0.005, 0.8, 10);
            tnn.save(weight);
        }
        else tnn = new TrainingNeuralNetwork(weight,0.005,  0.8, 10);

        train(tnn, ds);*/

        for(int i = 0; i < 20; i++)
            System.out.println(Arrays.toString((new NeuralNetwork(weight)).counting(ds.getInList()[i])) + " ideal: " + ds.getOutList()[i][0]);
    }
}