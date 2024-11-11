package simple.test;

import simple.test.NN.DataSet;
import simple.test.NN.TrainingNeuralNetwork;

public class Main {
    public static void main(String[] args) {
        DataSet dataSet1 = new DataSet();
        // Создание датасета
        // исключающее или
        dataSet1.setTests(new double[][][]{
                {
                        {0, 0}, {0}
                },
                {
                        {1, 1}, {0}
                }
        });
        DataSet dataSet2 = new DataSet();
        // Создание датасета
        // исключающее или
        dataSet2.setTests(new double[][][]{
                {
                        {0, 1}, {1}
                },
                {
                        {1, 0}, {1}
                }
        });

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
                }
        });
        // Создание нейронной сети с заданными параметрами: количество слоев, количество нейронов в слоях, скорость, альфа(момент), модуль максимального веса
        TrainingNeuralNetwork nn = new TrainingNeuralNetwork(4, new int[]{2, 4, 4, 1}, 0.005, 0.8, 10);
        // Тренировка
        //nn.train(dataSet1, 1000000);
        //nn.train(dataSet2, 1000000);
        nn.train(dataSet, 1000000);
    }
}
