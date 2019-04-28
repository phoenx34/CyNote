package com.example.cs309_cynote;

import com.android.volley.VolleyError;

/**
 * Interface used to define callbacks for APICalls.
 * When using this interface, a composite design pattern is recommended.
 *
 * @param <P> Parameter type of callback methods
 */
public interface APICallbacks<P> {

    /**
     * Callback method for a response in APICalls
     * @param response
     */
    void onResponse(P response);

    /**
     * Callback method for an error in APICalls
     * @param error
     */
    void onVolleyError(VolleyError error);
}
