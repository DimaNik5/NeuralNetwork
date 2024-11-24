package com.ai.neuralNetwork;

import com.ai.neuralNetwork.train.TrainingNeuralNetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NeuralNetwork {
    private Layer[] layers;

    // Копирующий конструктор из обучающей нейросети, сохраняющий веса в указанном файле
    public NeuralNetwork(TrainingNeuralNetwork tnn, String fileNameForSave){
        tnn.save(fileNameForSave);
        layers = tnn.getLayers();
    }

    // Конструктор, получающий слои
    public NeuralNetwork(Layer[] layers){
        this.layers = layers;
    }

    // Конструктор, загружающий из переданного файла веса
    public NeuralNetwork(String fileName){
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line = br.readLine();
            if (line == null) return;
            String[] str = line.split(" ");
            try {
                layers = new Layer[Integer.parseInt(str[0])];
                for (int i = 0; i < layers.length; i++) {
                    if (Integer.parseInt(str[i + 1]) < 0) return;
                    layers[i] = new Layer(Integer.parseInt(str[i + 1]), (i < layers.length - 1 ? Integer.parseInt(str[i + 2]) : 0));
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }

            for (Layer layer : layers) {
                line = br.readLine();
                if (line == null) return;
                String[] w = line.split(";");
                for (int j = 0; j < layer.getLength(); j++) {
                    if (!layer.setWeight(w[j].split(" "), j)) return;
                }
                if (!layer.setWeightB(w[layer.getLength()].split(" "))) return;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Подсчет результата по переданным входным парамметрам
    public double[] counting(double[] input){
        // Установка входных значений
        layers[0].setInput(input);
        for(int i = 0; i < layers.length - 1; i++){
            // Установка 0 для следующего слоя
            layers[i+1].setZero();
            // нормализация(активация)
            if(i > 0) layers[i].normalize();
            // Проход по нейронам текущего слоя
            for(int j = 0; j < layers[i].getLength(); j++){
                // Получение нормального значения(0-1) для нейрона
                double curValue = layers[i].getNormResult(j);
                // Проход по нейронам следующего слоя
                for(int k = 0; k < layers[i+1].getLength(); k++){
                    // Добавление к результату произведение значения и веса текущего нейрона + вес нейрона смещения
                    layers[i+1].addResult(k, curValue * layers[i].getWeight(j, k) + layers[i].getWeightB(k));
                }
            }
        }
        // Нормализация выходного слоя
        layers[layers.length - 1].normalize();
        return layers[layers.length - 1].getNormResult();
    }

    // Подсчет результатов по списку переданных входных парамметров
    public double[][] counting(double[][] input){
        double[][] res = new double[input.length][];
        for(int i = 0; i < input.length; i++){
            res[i] = counting(input[i]);
        }
        return res;
    }

    // Возврат слоев
    public Layer[] getLayers() {
        return layers;
    }
}
