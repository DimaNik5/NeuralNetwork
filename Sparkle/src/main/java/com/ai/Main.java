package com.ai;

import com.ai.dataSet.DataSet;
import com.ai.neuralNetwork.NeuralNetwork;
import com.ai.neuralNetwork.train.TrainingNeuralNetwork;

import java.io.FileWriter;
import java.io.IOException;

import static com.ai.Training.train;

public class Main {

    static boolean testIn = true;
    static boolean newNetwork = true;
    // true - при открытии через IDE, false - при открытии через исполняемый файл
    static boolean InIDE = true;
    public static void main(String[] args) {
        // true - при открытии через IDE, false - при открытии через исполняемый файл
        String way = System.getProperty("user.dir") + ((InIDE) ? "/src/main/java/com/ai" : "");

        if(!testIn) {
            String dataset = way + "/train.csv";
            DataSet ds = new DataSet(dataset);
            ds.loadFromCsv();

            String weight = way + "/weight/weight9.csv";

            TrainingNeuralNetwork tnn;
            if (newNetwork) {
                tnn = new TrainingNeuralNetwork(new int[]{7, 3, 1}, 0.0005, 0.08, 10);
                tnn.save(weight);
            } else tnn = new TrainingNeuralNetwork(weight, 0.0005, 0.08, 10);

            train(tnn, ds);
        }
        else{
            Ensemble ensemble = new Ensemble(way);

            String dataset = way + "/train.csv";
            DataSet ds = new DataSet(dataset);
            ds.loadFromCsv();

            double max = 0;
            double sr = 0;
            System.out.println(ds.getOutList().length);
            try {
                FileWriter fw = new FileWriter(way + "/res.csv");

                for(int i = 0; i < ds.getOutList().length; i++){
                    double t = Math.abs(ensemble.counting(ds.getInList()[i]) - ds.getOutList()[i][0]);
                    max = Math.max(max, t);
                    sr += t;
                    fw.write(ds.getOutList()[i][0] + " " + ensemble.counting(ds.getInList()[i]) + "\n");
                }
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println(max);
            System.out.println(sr / ds.getOutList().length);

           /* String s = "fffe33003100330031003100,2008-01-24,Female,Service,No,4.0,8.0,7.8";
            System.out.println(ensemble.counting(s) + " Ideal: 0.74");
            s = "fffe32003600340039003400,2008-02-28,Female,Service,No,4.0,8.0,9.0";
            System.out.println(ensemble.counting(s) + " Ideal: 0.74");*/

        }

    }
}