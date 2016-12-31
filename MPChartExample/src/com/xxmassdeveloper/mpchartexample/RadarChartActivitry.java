
package com.xxmassdeveloper.mpchartexample;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.xxmassdeveloper.mpchartexample.custom.RadarMarkerView;
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RadarChartActivitry extends DemoBase {

    private String API_KEY = "CnkINHEr3L632xJAeCyAjjSumQpOtU";
    private String tempVarId = "57ed3ef97625426ae9a21758";
    private String humVarId = "57ed3ef97625426a5196553b";
    private String luxVarId = "57ed3ef97625426b0cea756b";
    private String nutrientVarId = "57ed3ef97625426b4e7f3794";
    private String moistureVarId = "57ed3ef97625426b4e7f3795";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private RadarChart mChart;
    ArrayList<RadarEntry> entries1 = new ArrayList<RadarEntry>();
    ArrayList<RadarEntry> entries2 = new ArrayList<RadarEntry>();
    float[] arr = new float[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_radarchart_noseekbar);

        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setTypeface(mTfLight);
        tv.setTextColor(Color.WHITE);
        tv.setBackgroundColor(Color.rgb(60, 65, 82));

        mChart = (RadarChart) findViewById(R.id.chart1);
        mChart.setBackgroundColor(Color.rgb(60, 65, 82));

        mChart.setDescription("");

        mChart.setWebLineWidth(1f);
        mChart.setWebColor(Color.LTGRAY);
        mChart.setWebLineWidthInner(1f);
        mChart.setWebColorInner(Color.LTGRAY);
        mChart.setWebAlpha(100);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MarkerView mv = new RadarMarkerView(this, R.layout.radar_markerview);

        // set the marker to the chart
        mChart.setMarkerView(mv);

        setData();
        //feedMultiple();

        mChart.animateXY(
                1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new AxisValueFormatter() {

            private String[] mActivities = new String[]{"Soil Nutrient", "Soil Moisture", "Light", "Humidity", "Temperature"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });
        xAxis.setTextColor(Color.WHITE);

        YAxis yAxis = mChart.getYAxis();
        yAxis.setTypeface(mTfLight);
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinValue(0f);
        yAxis.setAxisMaxValue(80f);
        yAxis.setDrawLabels(false);

        Legend l = mChart.getLegend();
        l.setPosition(LegendPosition.ABOVE_CHART_CENTER);
        l.setTypeface(mTfLight);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.WHITE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.radar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                for (IDataSet<?> set : mChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHighlight: {
                if (mChart.getData() != null) {
                    mChart.getData().setHighlightEnabled(!mChart.getData().isHighlightEnabled());
                    mChart.invalidate();
                }
                break;
            }
            case R.id.actionToggleRotate: {
                if (mChart.isRotationEnabled())
                    mChart.setRotationEnabled(false);
                else
                    mChart.setRotationEnabled(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleFilled: {

                ArrayList<IRadarDataSet> sets = (ArrayList<IRadarDataSet>) mChart.getData()
                        .getDataSets();

                for (IRadarDataSet set : sets) {
                    if (set.isDrawFilledEnabled())
                        set.setDrawFilled(false);
                    else
                        set.setDrawFilled(true);
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHighlightCircle: {

                ArrayList<IRadarDataSet> sets = (ArrayList<IRadarDataSet>) mChart.getData()
                        .getDataSets();

                for (IRadarDataSet set : sets) {
                    set.setDrawHighlightCircleEnabled(!set.isDrawHighlightCircleEnabled());
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionSave: {
                if (mChart.saveToPath("title" + System.currentTimeMillis(), "")) {
                    Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!",
                            Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT)
                            .show();
                break;
            }
            case R.id.actionToggleXLabels: {
                mChart.getXAxis().setEnabled(!mChart.getXAxis().isEnabled());
                mChart.notifyDataSetChanged();
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleYLabels: {

                mChart.getYAxis().setEnabled(!mChart.getYAxis().isEnabled());
                mChart.invalidate();
                break;
            }
            case R.id.animateX: {
                mChart.animateX(1400);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(1400);
                break;
            }
            case R.id.animateXY: {
                mChart.animateXY(1400, 1400);
                break;
            }
            case R.id.actionToggleSpin: {
                mChart.spin(2000, mChart.getRotationAngle(), mChart.getRotationAngle() + 360, Easing.EasingOption
                        .EaseInCubic);
                break;
            }
        }
        return true;
    }

    public void setData() {
        float mult = 80;
        float min = 20;
        int cnt = 5;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ( new UbidotsClient() ).handleUbidots(tempVarId, API_KEY, new UbidotsClient.UbiListener() {
                    @Override
                    public void onDataReady(List<UbidotsClient.Value> result) {
                        Log.d("Chart", "======== On data Ready ===========");
                        for (int i=0; i < 1; i++) {
                            //arr[0] = result.get(i).value;
                            entries2.add(new RadarEntry(result.get(i).value));

                        }
                    }
                });
            }
        },1500);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ( new UbidotsClient() ).handleUbidots(humVarId, API_KEY, new UbidotsClient.UbiListener() {
                    @Override
                    public void onDataReady(List<UbidotsClient.Value> result) {
                        Log.d("Chart", "======== On data Ready ===========");
                        for (int i=0; i < 1; i++) {
                            //arr[1] = result.get(i).value;
                            entries2.add(new RadarEntry(result.get(i).value));
                        }
                    }
                });
            }
        },1200);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ( new UbidotsClient() ).handleUbidots(luxVarId, API_KEY, new UbidotsClient.UbiListener() {
                    @Override
                    public void onDataReady(List<UbidotsClient.Value> result) {
                        Log.d("Chart", "======== On data Ready ===========");
                        for (int i=0; i < 1; i++) {
                            if (result.get(i).value > 2000) {
                                //arr[2] = 100f;
                                entries2.add(new RadarEntry(100f));
                            } else {
                                //arr[2] = result.get(i).value / 20f;
                                entries2.add(new RadarEntry(result.get(i).value / 20f));
                            }
                        }
                    }
                });
            }
        },900);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ( new UbidotsClient() ).handleUbidots(moistureVarId, API_KEY, new UbidotsClient.UbiListener() {
                    @Override
                    public void onDataReady(List<UbidotsClient.Value> result) {
                        Log.d("Chart", "======== On data Ready ===========");
                        for (int i=0; i < 1; i++) {
                            //arr[3] = result.get(i).value;
                            entries2.add(new RadarEntry(result.get(i).value));
                        }

                    }
                });
            }
        },600);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ( new UbidotsClient() ).handleUbidots(nutrientVarId, API_KEY, new UbidotsClient.UbiListener() {
                    @Override
                    public void onDataReady(List<UbidotsClient.Value> result) {
                        Log.d("Chart", "======== On data Ready ===========");
                        for (int i=0; i < 1; i++) {
                            //arr[4] = result.get(i).value;
                            entries2.add(new RadarEntry(result.get(i).value));

                        }
                    }
                });
            }
        },400);






        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < cnt; i++) {
            //float val1 = (float) (Math.random() * mult) + min;
            //entries2.add(new RadarEntry(80f));
            //entries2.add(new RadarEntry(50f));

            //float val2 = (float) (Math.random() * mult) +
            if (i == 4){
                entries1.add(new RadarEntry(30f));
            }else{
                entries1.add(new RadarEntry(45f));
            }
        }

        RadarDataSet set1 = new RadarDataSet(entries1, "Data Reference");
        set1.setColor(Color.rgb(103, 110, 129));
        set1.setFillColor(Color.rgb(103, 110, 129));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        RadarDataSet set2 = new RadarDataSet(entries2, "Real Data");
        set2.setColor(Color.rgb(121, 162, 175));
        set2.setFillColor(Color.rgb(121, 162, 175));
        set2.setDrawFilled(true);
        set2.setFillAlpha(180);
        set2.setLineWidth(2f);
        set2.setDrawHighlightCircleEnabled(true);
        set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(sets);
        data.setValueTypeface(mTfLight);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        mChart.setData(data);
        mChart.invalidate();

    }

    /*private Thread thread;

    private void feedMultiple() {

        if (thread != null)
            thread.interrupt();

        final Runnable runnable = new Runnable() {

            @Override
            public void run() {
                setData();
            }
        };

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {

                    // Don't generate garbage runnables inside the loop.
                    runOnUiThread(runnable);

                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
    } */
}
