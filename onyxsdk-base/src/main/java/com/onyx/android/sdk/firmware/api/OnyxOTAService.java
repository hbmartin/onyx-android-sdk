package com.onyx.android.sdk.firmware.api;

import com.onyx.android.sdk.firmware.data.Firmware;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OnyxOTAService {
   String WHERE_TAG = "where";

   @GET("firmware/update")
   Call<Firmware> firmwareUpdate(@Query("where") String var1);
}
