package entity;

public class ApSignalStrengthPair {
    private String macAddr;
    private Double rpPower;
    private Double tpPower;

    public ApSignalStrengthPair(String macAddr, Double rpPower, Double tpPower) {
        this.macAddr = macAddr;
        this.rpPower = rpPower;
        this.tpPower = tpPower;
    }

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    public Double getRpPower() {
        return rpPower;
    }

    public void setRpPower(Double rpPower) {
        this.rpPower = rpPower;
    }

    public Double getTpPower() {
        return tpPower;
    }

    public void setTpPower(Double tpPower) {
        this.tpPower = tpPower;
    }

    @Override
    public String toString() {
        return "ApPowerPair{" +
                "macAddr='" + macAddr + '\'' +
                ", rpPower=" + rpPower +
                ", tpPower=" + tpPower +
                '}';
    }
}
