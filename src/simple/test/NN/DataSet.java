package simple.test.NN;

public class DataSet {
    private int count;
    private double[][][] tests;

    public int getCount() {
        return count;
    }

    public double[][][] getTests() {
        return tests;
    }

    public void setTests(double[][][] tests) {
        this.tests = tests;
        count = tests.length;
    }
}
