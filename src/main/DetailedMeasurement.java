package main;

public class DetailedMeasurement {
    private Measurement currentMeasurements;
    private PastMeasurement[] history;


    public Measurement getCurrentMeasurement() {
        return currentMeasurements;
    }

    public PastMeasurement[] getHistory() { return history; }


    public void setCurrentMeasurement(Measurement currentMeasurement) {
        this.currentMeasurements = currentMeasurement;
    }

    public void setHistory(PastMeasurement[] history) {
        this.history = history;
    }
}
