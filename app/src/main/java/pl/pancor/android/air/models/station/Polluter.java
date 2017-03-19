package pl.pancor.android.air.models.station;


public class Polluter {

    private final int polluter;
    private final Double value;
    private final int maxValue;

    public Polluter(int polluter, Double value, int maxValue){

        this.polluter = polluter;
        this.value = value;
        this.maxValue = maxValue;
    }

    public int getPolluter() {
        return polluter;
    }

    public Double getValue() {
        return value;
    }

    public int getMaxValue() {
        return maxValue;
    }
}
