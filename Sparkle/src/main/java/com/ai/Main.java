package com.ai;

import com.ai.dataSet.DataSet;
import com.ai.neuralNetwork.train.TrainingNeuralNetwork;

import static com.ai.Training.train;

public class Main {
    public static void main(String[] args) {

        DataSet ds = new DataSet("C:/Users/Президент/Desktop/Sparkle/src/main/java/com/ai/dataSet/my.csv");
        ds.loadFromCsv();

        // new int[]{7, 16, 16, 16, 1} - структура
        TrainingNeuralNetwork tnn = new TrainingNeuralNetwork("C:\\Users\\Президент\\Desktop\\Sparkle\\src\\main\\java\\com\\ai\\neuralNetwork/weight.csv",0.005, 0.8, 10);
        //tnn.train(ds.getInList(), ds.getOutList(), 1000);
        //tnn.save("C:\\Users\\Президент\\Desktop\\Sparkle\\src\\main\\java\\com\\ai\\neuralNetwork/weight.csv");


        train(tnn, ds);
    }
}