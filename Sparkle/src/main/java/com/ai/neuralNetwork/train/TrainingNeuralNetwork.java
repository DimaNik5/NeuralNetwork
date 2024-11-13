package com.ai.neuralNetwork.train;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TrainingNeuralNetwork {

    private LayerTrain[] layerTrains;
    private double speed;
    private double alpha;
    private double maxWeight;
    private String fileName;

    public TrainingNeuralNetwork(int[] lenLayer, double speed, double alpha, double maxWeight){
        int len = lenLayer.length;
        if(len < 2) return;
        this.speed = speed;
        this.maxWeight = maxWeight;
        this.alpha = alpha;
        layerTrains = new LayerTrain[len];
        for(int i = 0; i < len; i++){
            if(lenLayer[i] < 0) return;
            layerTrains[i] = new LayerTrain(lenLayer[i], (i < len - 1 ? lenLayer[i + 1] : 0));
        }
    }

    public TrainingNeuralNetwork(String fileName, double speed, double alpha, double maxWeight){
        this.speed = speed;
        this.maxWeight = maxWeight;
        this.alpha = alpha;
        this.fileName = fileName;

        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();
            if (line == null) return;
            String[] str = line.split(" ");
            try {
                layerTrains = new LayerTrain[Integer.parseInt(str[0])];
                for (int i = 0; i < layerTrains.length; i++) {
                    if (Integer.parseInt(str[i + 1]) < 0) return;
                    layerTrains[i] = new LayerTrain(Integer.parseInt(str[i + 1]), (i < layerTrains.length - 1 ? Integer.parseInt(str[i + 2]) : 0));
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }

            for (LayerTrain layerTrain : layerTrains) {
                line = br.readLine();
                if (line == null) return;
                String[] w = line.split(";");
                for (int j = 0; j < layerTrain.getLength(); j++) {
                    if (!layerTrain.setWeight(w[j].split(" "), j)) return;
                }
                if (!layerTrain.setWeightB(w[layerTrain.getLength()].split(" "))) return;
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public double[] train(double[][] in, double[][] out, int epochs){
        double maxDelta = 0, middle = 0;
        // Тренировка по эпохам
        for (int k = 0; k < epochs; k++){
            // Проход по тестам
            for (int i = 0; i < in.length; i++) {
                // Подсчет результата
                double t = Math.abs(out[i][0] - counting(in[i])[0]);
                /*if(t > 0.3) {
                    correction(out[i]);
                    counting(in[i]);
                }*/
                if (k == epochs - 1) {

                    middle += t;
                    maxDelta = Math.max(t, maxDelta);
                }
                else counting(in[i]);
                // Корректировка весов
                correction(out[i]);
            }
        }
        middle /= in.length;
        return new double[]{maxDelta, middle};

    }

    // Вывод в консоль результатов нейронов и весов
    private void print(){
        for(int i = 0; i < 4; i++){
            for (int j = 0; j < layerTrains.length; j++) {
                layerTrains[j].print(i, j == layerTrains.length - 1);
                System.out.print("  ");
            }
            System.out.println();
        }
    }

    private double[] counting(double[] input){
        // Установка входных значений
        layerTrains[0].setInput(input);
        for(int i = 0; i < layerTrains.length - 1; i++){
            // Установка 0 для следующего слоя
            layerTrains[i+1].setZero();
            // нормализация(активация)
            if(i > 0) layerTrains[i].normalize();
            // Проход по нейронам текущего слоя
            for(int j = 0; j < layerTrains[i].getLength(); j++){
                // Получение нормального значения(0-1) для нейрона
                double curValue = layerTrains[i].getNormResult(j);
                // Проход по нейронам следующего слоя
                for(int k = 0; k < layerTrains[i+1].getLength(); k++){
                    // Добавление к результату произведение значения и веса текущего нейрона + вес нейрона смещения
                    layerTrains[i+1].addResult(k, curValue * layerTrains[i].getWeight(j, k) + layerTrains[i].getWeightB(k));
                }
            }
        }
        // Нормализация выходного слоя
        layerTrains[layerTrains.length - 1].normalize();
        return layerTrains[layerTrains.length - 1].getNormResult();
    }

    private void correction(double[] ideal){
        double[] curIdeal = ideal;
        // Получение дельт(разницы от идеала) для каждого слоя с передачей результатов к предыдущему
        for(int i = layerTrains.length - 1; i > 0; i--){
            curIdeal = layerTrains[i].setDelta(curIdeal, i == layerTrains.length - 1);
        }
        boolean f = false; // Превышение максимального веса
        // Установка новых весов
        for(int i = 0; i < layerTrains.length - 1; i++){
            f = f || (Math.abs(layerTrains[i].setDeltaWeight(layerTrains[i + 1].getDeltas(), speed, alpha)) > maxWeight);
        }
        if(f){
            // Пропорциональное деление весов
            for(int i = 0; i < layerTrains.length - 1; i++){
                layerTrains[i].divWeight(maxWeight / 2);
            }
        }

    }

    public LayerTrain[] getLayers() {
        return layerTrains;
    }

    public void save(String file){
        fileName = file;
        try {
            FileWriter fw = new FileWriter(file);

            // Запись количества слоев
            fw.write(layerTrains.length + " ");
            // Запись количества нейронов в каждом слое
            for(LayerTrain layerTrain : layerTrains){
                fw.write(layerTrain.getLength() + " ");
            }
            fw.write('\n');

            // Проход по всем слоям, кроме выходного(нет весов)
            for(int k = 0; k < layerTrains.length - 1; k++){
                // Проход повсем нейронам слоя
                for(int i = 0; i < layerTrains[k].getLength(); i++){
                    // Проход повсем нейронам следующего слоя
                    for(int j = 0; j < layerTrains[k + 1].getLength(); j++){
                        // Запись весов от i к j нейрону
                        fw.write(layerTrains[k].getWeight(i, j) + " ");
                    }
                    // Знак препинания
                    // TODO сделать константы
                    fw.write(';');
                }
                // Запись весов нейрона смещения
                for(int j = 0; j < layerTrains[k + 1].getLength(); j++){
                    fw.write(layerTrains[k].getWeightB(j) + " ");
                }
                fw.write('\n');
            }

            fw.close();
        }
        catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void save(){
        if(fileName == null || fileName.isEmpty()) return;
        save(fileName);
    }
}