package simple.test;

import simple.test.NN.DataSet;
import simple.test.NN.TrainingNeuralNetwork;

public class Main {
    public static void main(String[] args) {
        DataSet dataSet = new DataSet();
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

        TrainingNeuralNetwork nn = new TrainingNeuralNetwork(3, new int[]{2, 3, 1}, 0.3, 0.2, 3);
        nn.train(dataSet, 10000);
    }
}
