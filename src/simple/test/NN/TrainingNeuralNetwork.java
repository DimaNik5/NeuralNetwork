package simple.test.NN;

import simple.test.NN.Components.Layer;


public class TrainingNeuralNetwork {
    private Layer[] layers;
    private double speed;
    private double alpha;
    private double maxWeight;

    public TrainingNeuralNetwork(int length, int[] lenLayer, double speed, double alpha, double maxWeight){
        if(length < 2) return;
        this.speed = speed;
        this.maxWeight = maxWeight;
        this.alpha = alpha;
        layers = new Layer[length];
        for(int i = 0; i < length; i++){
            if(lenLayer[i] < 0) return;
            layers[i] = new Layer(lenLayer[i], (i < length - 1 ? lenLayer[i + 1] : 0));
        }
    }

    public void train(DataSet dataSet, int epochs){
        double[][][] tests = dataSet.getTests();
        // Тренировка по эпохам
        for (int k = 0; k < epochs; k++){
            // Вывод результатов 1 и последней эпохи
            if(k == 0 || k == epochs - 1)
                System.out.println("\tEpoch " + k);
            // Проход по тестам
            for (double[][] test : tests) {
                if (k == 0 || k == epochs - 1)
                    System.out.println("Result: " + String.format(" %.2f", counting(test[0])[0]) + " Ideal : " + test[1][0]);
                else counting(test[0]); // Подсчет результата
                // Корректировка весов
                correction(test[1]);
                //print();
            }
        }
    }

    private void print(){
        for(int i = 0; i < 4; i++){
            for (int j = 0; j < layers.length; j++) {
                layers[j].print(i, j == layers.length - 1);
                System.out.print("  ");
            }
            System.out.println();
        }
    }

    private double[] counting(double[] input){
        // Установка входных значений
        layers[0].setInput(input);
        for(int i = 0; i < layers.length - 1; i++){
            // Установка 0 для следующего слоя
            layers[i+1].setZero();
            // нормализация(активация)
            if(i > 0)layers[i].normalize();
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

    private void correction(double[] ideal){
        double[] curIdeal = ideal;
        // Получение дельт(разницы от идеала) для каждого слоя с передачей результатов к предыдущему
        for(int i = layers.length - 1; i > 0; i--){
            curIdeal = layers[i].setDelta(curIdeal, i == layers.length - 1);
        }
        boolean f = false; // Превышение максимального веса
        // Установка новых весов
        for(int i = 0; i < layers.length - 1; i++){
            f = f || (Math.abs(layers[i].setDeltaWeight(layers[i + 1].getDeltas(), speed, alpha)) > maxWeight);
        }
        if(f){
            // Пропорциональное деление весов
            for(int i = 0; i < layers.length - 1; i++){
                layers[i].divWeight(maxWeight / 2);
            }
        }

    }

}
