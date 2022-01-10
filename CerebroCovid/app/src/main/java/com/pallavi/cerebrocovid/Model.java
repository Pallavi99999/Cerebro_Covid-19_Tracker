package com.pallavi.cerebrocovid;

public class Model {

    String loc;
    String totalConfirmed;

    public Model(String loc, String totalConfirmed) {
        this.loc = loc;
        this.totalConfirmed = totalConfirmed;
    }

    public Model() {
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(String totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }
}
