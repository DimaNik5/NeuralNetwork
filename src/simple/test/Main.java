package simple.test;

import simple.test.NN.DataSet;
import simple.test.NN.TrainingNeuralNetwork;

public class Main {
    public static void main(String[] args) {
        DataSet dataSet = new DataSet();
        // Создание датасета
        // исключающее или
        dataSet.setTests(new double[][][]{
                {
                        {0, 0}, {0}
                },
                {
                        {0, 1}, {1}
                },
                {
                        {1, 0}, {1}
                },
                {
                        {1, 1}, {0}
                },
        });
        // Создание нейронной сети с заданными параметрами: количество слоев, количество нейронов в слоях, скорость, альфа(момент), модуль максимального веса
        TrainingNeuralNetwork nn = new TrainingNeuralNetwork(4, new int[]{2, 3, 3, 1}, 0.6, 0.2, 3);
        // Тренировка
        nn.train(dataSet, 10000);
    }
}
