package com.ai;

import com.ai.dataSet.Data;
import com.ai.dataSet.DataSet;
import com.ai.dataSet.NormalizeData;
import com.ai.neuralNetwork.NeuralNetwork;
import com.ai.neuralNetwork.train.TrainingNeuralNetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import static com.ai.Training.train;

public class Main {

    static boolean res = true;
    static boolean testIn = true;
    static boolean newNetwork = true;
    // true - при открытии через IDE, false - при открытии через исполняемый файл
    static boolean InIDE = true;
    public static void main(String[] args) {
        // true - при открытии через IDE, false - при открытии через исполняемый файл
        String way = System.getProperty("user.dir") + ((InIDE) ? "/src/main/java/com/ai" : "");

        if(res){ // Вывод подсчитанных результатов тествого датасета
            try {
                Ensemble ensemble = new Ensemble(way);
                BufferedReader br = new BufferedReader(new FileReader(way  + "/test.csv"));
                String dataLine = br.readLine();
                while ((dataLine = br.readLine()) != null){
                    double res = ensemble.counting(dataLine, 8);
                    if(res != -1) System.out.println(dataLine + " " + res);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else if(!testIn) { // Обучение/создание перцептрона
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
        else{ // Проверка ансамбля на обучающем датасете
            Ensemble ensemble = new Ensemble(way);

            String dataset = way + "/train.csv";
            DataSet ds = new DataSet(dataset);
            ds.loadFromCsv();

            double max = 0;
            double sr = 0;
            System.out.println(ds.getOutList().length);
            try(FileWriter fw = new FileWriter(way + "/res.csv")) {

                for(int i = 0; i < ds.getOutList().length; i++){
                    double t = Math.abs(ensemble.counting(ds.getInList()[i]) - ds.getOutList()[i][0]);
                    max = Math.max(max, t);
                    sr += t;
                    fw.write(ds.getOutList()[i][0] + " " + ensemble.counting(ds.getInList()[i]) + "\n");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println(max); // Максимальная разница
            System.out.println(sr / ds.getOutList().length); // Средняя разница
        }

    }
}