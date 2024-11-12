package com.ai;

import com.ai.dataSet.DataSet;
import com.ai.neuralNetwork.train.TrainingNeuralNetwork;

import static com.ai.Training.train;

public class Main {
    public static void main(String[] args) {
        // true - при открытии через IDE, false - при открытии через исполняемый файл
        String way = System.getProperty("user.dir") + ((true) ? "/src/main/java/com/ai" : "");

        String dataset = way + "/dataset.csv";
        DataSet ds = new DataSet(dataset);
        ds.loadFromCsv();

        String weight = way + "/weight.csv";
        // new int[]{7, 16, 16, 16, 1} - структура
        TrainingNeuralNetwork tnn = new TrainingNeuralNetwork(weight,0.005, 0.8, 10);
        //tnn.train(ds.getInList(), ds.getOutList(), 1000);
        //tnn.save(weight);

        train(tnn, ds);
    }
}