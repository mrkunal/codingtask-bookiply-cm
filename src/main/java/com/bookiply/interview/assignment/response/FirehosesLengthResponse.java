package com.bookiply.interview.assignment.response;

import java.util.List;

public class FirehosesLengthResponse {

    private Double totalFirehosesLength;
    private HydrantResponse[] hydrants;

    public FirehosesLengthResponse(Double totalFirehosesLength, HydrantResponse[] hydrants) {
        this.totalFirehosesLength = totalFirehosesLength;
        this.hydrants = hydrants;

        List<String> stringList = null;

        String[] arr= (String[]) stringList.toArray();
    }

    public FirehosesLengthResponse() {
    }

    public Double getTotalFirehosesLength() {
        return totalFirehosesLength;
    }

    public void setTotalFirehosesLength(Double totalFirehosesLength) {
        this.totalFirehosesLength = totalFirehosesLength;
    }

    public HydrantResponse[] getHydrants() {
        return hydrants;
    }

    public void setHydrants(HydrantResponse[] hydrants) {
        this.hydrants = hydrants;
    }
}
