package entity;

import java.util.ArrayList;
import java.util.List;

public class TargetPoint {
    private List<APSignal> apSignals = new ArrayList<APSignal>();

    public void addApSignals(APSignal apSignal) {
        apSignals.add(apSignal);
    }

    public List<APSignal> getApSignals() {
        return apSignals;
    }

    @Override
    public String toString() {
        return "TargetSignal{" +
                "apSignals=" + apSignals +
                '}';
    }
}
