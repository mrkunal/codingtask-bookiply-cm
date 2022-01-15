package com.bookiply.interview.assignment.utils;

import com.bookiply.interview.assignment.response.FirehosesLengthResponse;
import com.bookiply.interview.assignment.response.HydrantResponse;

public class HydrantUtils {

    public static  HydrantResponse[] mockHydrantResponse() {

        HydrantResponse hydrantResponse1 = new HydrantResponse("HYUNIT1", 100.50);
        HydrantResponse hydrantResponse2 = new HydrantResponse("HYUNIT4", 50.50);
        HydrantResponse hydrantResponse3 = new HydrantResponse("HYUNIT5", 49.00);
        return new HydrantResponse[]{hydrantResponse1, hydrantResponse2, hydrantResponse3};
    }


    public static FirehosesLengthResponse mockFirehosesLengthResponse() {
        return new FirehosesLengthResponse(250d, HydrantUtils.mockHydrantResponse());
    }

}
