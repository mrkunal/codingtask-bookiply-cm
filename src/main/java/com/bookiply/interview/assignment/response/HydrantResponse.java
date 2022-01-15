package com.bookiply.interview.assignment.response;

public class HydrantResponse {

    private String unitid;
    private Double distanceToFire;

    public HydrantResponse() {
    }

    public HydrantResponse(String unitid, Double distanceToFire) {
        this.unitid = unitid;
        this.distanceToFire = distanceToFire;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public Double getDistanceToFire() {
        return distanceToFire;
    }

    public void setDistanceToFire(Double distanceToFire) {
        this.distanceToFire = distanceToFire;
    }
}
