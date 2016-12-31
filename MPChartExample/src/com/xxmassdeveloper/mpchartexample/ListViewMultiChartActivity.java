
package com.xxmassdeveloper.mpchartexample;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.xxmassdeveloper.mpchartexample.listviewitems.BarChartItem;
import com.xxmassdeveloper.mpchartexample.listviewitems.ChartItem;
import com.xxmassdeveloper.mpchartexample.listviewitems.LineChartItem;
import com.xxmassdeveloper.mpchartexample.listviewitems.PieChartItem;
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Demonstrates the use of charts inside a ListView. IMPORTANT: provide a
 * specific height attribute for the chart inside your listview-item
 * 
 * @author Philipp Jahoda
 */
public class ListViewMultiChartActivity extends DemoBase {
    ArrayList<Entry> e1 = new ArrayList<Entry>();
    ArrayList<Entry> e2 = new ArrayList<Entry>();
    ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
    private String API_KEY = "CnkINHEr3L632xJAeCyAjjSumQpOtU";
    private String tempVarId = "57ed3ef97625426ae9a21758";
    private String humVarId = "57ed3ef97625426a5196553b";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_listview_chart);
        
        ListView lv = (ListView) findViewById(R.id.listView1);

        ArrayList<ChartItem> list = new ArrayList<ChartItem>();



        // 30 items
        for (int i = 0; i < 2; i++) {
            
            if(i % 2 == 0) {
                list.add(new LineChartItem(generateDataLine(i + 1), getApplicationContext()));
            } else if(i % 2 == 1) {
                list.add(new BarChartItem(generateDataBar(i + 1), getApplicationContext()));
            }
        }

        ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), list);
        lv.setAdapter(cda);
    }

    /** adapter that supports 3 different item types */
    private class ChartDataAdapter extends ArrayAdapter<ChartItem> {
        
        public ChartDataAdapter(Context context, List<ChartItem> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getItem(position).getView(position, convertView, getContext());
        }
        
        @Override
        public int getItemViewType(int position) {           
            // return the views type
            return getItem(position).getItemType();
        }
        
        @Override
        public int getViewTypeCount() {
            return 2; // we have 3 different item-types
        }
    }
    
    /**
     * generates a random ChartData object with just one DataSet
     * 
     * @return
     */
    private LineData generateDataLine(int cnt) {

        for (int i = 0; i < 18; i++) {
            e1.add(new Entry(i, (int) (Math.random() * 2) + 28));
        }

        for (int i = 0; i < 18; i++) {
            e2.add(new Entry(i, (int) (Math.random() * 5) + 40));
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ( new UbidotsClient() ).handleUbidots(humVarId, API_KEY, new UbidotsClient.UbiListener() {
                    @Override
                    public void onDataReady(List<UbidotsClient.Value> result) {
                        Log.d("Chart", "======== On data Ready ===========");
                        List<Entry> entries = new ArrayList();
                        List<String> labels = new ArrayList<String>();
                        for (int i=0; i < result.size(); i++) {
                            e2.add(new Entry(i, result.get(i).value));
                        }
                    }
                });
            }
        },1500);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ( new UbidotsClient() ).handleUbidots(tempVarId, API_KEY, new UbidotsClient.UbiListener() {
                    @Override
                    public void onDataReady(List<UbidotsClient.Value> result) {
                        Log.d("Chart", "======== On data Ready ===========");
                        List<Entry> entries = new ArrayList();
                        List<String> labels = new ArrayList<String>();
                        for (int i=0; i < result.size(); i++) {
                            e1.add(new Entry(i,result.get(i).value));
                        }
                    }
                });
            }
        },1000);




        LineDataSet d1 = new LineDataSet(e1, "New DataSet " + cnt + ", (1)");
        d1.setLineWidth(2.5f);
        d1.setCircleRadius(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);

        LineDataSet d2 = new LineDataSet(e2, "New DataSet " + cnt + ", (2)");
        d2.setLineWidth(2.5f);
        d2.setCircleRadius(4.5f);
        d2.setHighLightColor(Color.rgb(244, 117, 117));
        d2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setDrawValues(false);
        
        ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();
        sets.add(d1);
        sets.add(d2);
        
        LineData cd = new LineData(sets);
        return cd;
    }
    
    /**
     * generates a random ChartData object with just one DataSet
     * 
     * @return
     */
    private BarData generateDataBar(int cnt) {



        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry(i, (int) (Math.random() * 40) + 250));
        }

        BarDataSet d = new BarDataSet(entries, "New DataSet " + cnt);
        //d.setColors(ColorTemplate.PASTEL_COLORS);
        d.setHighLightAlpha(255);
        
        BarData cd = new BarData(d);
        cd.setBarWidth(0.9f);
        return cd;
    }
    
    /**
     * generates a random ChartData object with just one DataSet
     * 
     * @return
     */
    /*private PieData generateDataPie(int cnt) {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        for (int i = 0; i < 4; i++) {
            entries.add(new PieEntry((float) ((Math.random() * 70) + 30), "Quarter " + (i+1)));
        }

        PieDataSet d = new PieDataSet(entries, "");
        
        // space between slices
        d.setSliceSpace(2f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        
        PieData cd = new PieData(d);
        return cd;
    } */
}
