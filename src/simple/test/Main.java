package simple.test;

import simple.test.NN.DataSet;
import simple.test.NN.NeuralNetwork;
import simple.test.NN.TrainingNeuralNetwork;

import java.util.Arrays;

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
        // Создание нейронной сети с заданными параметрами: количество нейронов в слоях, скорость, альфа(момент), модуль максимального веса
        /*TrainingNeuralNetwork nn = new TrainingNeuralNetwork(new int[]{2, 4, 4, 1}, 0.005, 0.8, 10);
        // Тренировка
        //nn.train(dataSet1, 1000000);
        //nn.train(dataSet2, 1000000);
        nn.train(dataSet, 1000000);
        nn.save("C:\\Users\\Президент\\Desktop\\NeuralNetwork\\src\\simple\\test/test.csv");
        TrainingNeuralNetwork n = new TrainingNeuralNetwork("C:\\Users\\Президент\\Desktop\\NeuralNetwork\\src\\simple\\test/test.csv", 0.005, 0.8, 10);
        n.train(dataSet, 1);*/

        double[][] d = new double[][]{
                {0, 0}, {1, 0}, {0, 0}, {1, 1}, {0, 1}
        };

        NeuralNetwork t = new NeuralNetwork("C:\\Users\\Президент\\Desktop\\NeuralNetwork\\src\\simple\\test/test.csv");
        for(double[] a : d){
            System.out.println(Arrays.toString(t.counting(a)));
        }
    }
}
