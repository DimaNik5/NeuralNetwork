package com.ai;

import com.ai.dataSet.DataSet;
import com.ai.neuralNetwork.train.TrainingNeuralNetwork;

import java.io.IOException;
import java.io.InputStream;

public class Training {

    public static double train(TrainingNeuralNetwork tnn, DataSet dataSet) {
        InputStream input = System.in; // Получаем входной поток
        int i = 1;
        int epochs = 1000000;
        boolean show = true;
        while(true) {

            try {
                if (input.available() > 0) { // Проверяем наличие данных
                    int availableBytes = input.available();
                    byte[] buffer = new byte[availableBytes];
                    input.read(buffer); // Читаем данные

                    String inputString = new String(buffer);
                    if(inputString.equals("s\n")) show = !show;
                    else if(inputString.equals("e\n")) break;
                }

                if(show) System.out.println("Maximum deviation per " + i + "kk epoch: " + tnn.train(dataSet.getInList(), dataSet.getOutList(), epochs));
                else tnn.train(dataSet.getInList(), dataSet.getOutList(), epochs);
                i++;


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        tnn.save();

        return 0;
    }
}
