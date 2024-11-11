package simple.test.NN;

import simple.test.NN.Components.Layer;

public class NeuralNetwork {
    private final Layer[] layers;

    public NeuralNetwork(TrainingNeuralNetwork tnn, String fileName){
        tnn.save(fileName);
        layers = tnn.getLayers();
    }

    public NeuralNetwork(Layer[] layers){
        this.layers = layers;
    }

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

    public double[][] countingList(double[][] input){
        double[][] res = new double[input.length][];
        for(int i = 0; i < input.length; i++){
            res[i] = counting(input[i]);
        }
        return res;
    }

    public Layer[] getLayers() {
        return layers;
    }
}
