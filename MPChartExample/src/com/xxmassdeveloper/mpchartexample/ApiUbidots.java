package com.xxmassdeveloper.mpchartexample;

import android.os.AsyncTask;
import com.ubidots.ApiClient;
import com.ubidots.Value;
import com.ubidots.Variable;

/**
 * Created by irsan on 10/10/2016.
 */
public class ApiUbidots extends AsyncTask<Integer, Void, Value[]> {
    private final String API_KEY = "CnkINHEr3L632xJAeCyAjjSumQpOtU";
    private final String VARIABLE_ID = "57ed3ef97625426ae9a21758";

    @Override
    protected Value[] doInBackground(Integer... params) {
        ApiClient apiClient = new ApiClient(API_KEY);
        Variable tempVarId = apiClient.getVariable(VARIABLE_ID);
        Value[] variableValues = tempVarId.getValues();

        return variableValues;
    }

    @Override
    protected void onPostExecute(Value[] variableValues) {
        // Update your views here
    }
}