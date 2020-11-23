package entity;

public class APSignal {
    private String macAddr;
    private Double signalStrength;

    public APSignal() {}

    public APSignal(String macAddr, Double signalStrength) {
        this.macAddr = macAddr;
        this.signalStrength = signalStrength;
    }

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    public Double getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(Double signalStrength) {
        this.signalStrength = signalStrength;
    }

    @Override
    public String toString() {
        return "APSignal{" +
                "macAddr='" + macAddr + '\'' +
                ", signalStrength=" + signalStrength +
                '}';
    }
}
