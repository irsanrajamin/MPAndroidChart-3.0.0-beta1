
package com.xxmassdeveloper.mpchartexample.notimportant;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.github.mikephil.charting.utils.Utils;
import com.xxmassdeveloper.mpchartexample.AnotherBarActivity;
import com.xxmassdeveloper.mpchartexample.BarChartActivity;
import com.xxmassdeveloper.mpchartexample.BarChartActivityMultiDataset;
import com.xxmassdeveloper.mpchartexample.BarChartActivitySinus;
import com.xxmassdeveloper.mpchartexample.BarChartPositiveNegative;
import com.xxmassdeveloper.mpchartexample.BubbleChartActivity;
import com.xxmassdeveloper.mpchartexample.CandleStickChartActivity;
import com.xxmassdeveloper.mpchartexample.CombinedChartActivity;
import com.xxmassdeveloper.mpchartexample.CubicLineChartActivity;
import com.xxmassdeveloper.mpchartexample.DynamicalAddingActivity;
import com.xxmassdeveloper.mpchartexample.FilledLineActivity;
import com.xxmassdeveloper.mpchartexample.HalfPieChartActivity;
import com.xxmassdeveloper.mpchartexample.HorizontalBarChartActivity;
import com.xxmassdeveloper.mpchartexample.InvertedLineChartActivity;
import com.xxmassdeveloper.mpchartexample.LineChartActivity1;
import com.xxmassdeveloper.mpchartexample.LineChartActivity2;
import com.xxmassdeveloper.mpchartexample.LineChartActivityColored;
import com.xxmassdeveloper.mpchartexample.LineChartTime;
import com.xxmassdeveloper.mpchartexample.ListViewBarChartActivity;
import com.xxmassdeveloper.mpchartexample.ListViewMultiChartActivity;
import com.xxmassdeveloper.mpchartexample.MultiLineChartActivity;
import com.xxmassdeveloper.mpchartexample.PerformanceLineChart;
import com.xxmassdeveloper.mpchartexample.PieChartActivity;
import com.xxmassdeveloper.mpchartexample.PiePolylineChartActivity;
import com.xxmassdeveloper.mpchartexample.R;
import com.xxmassdeveloper.mpchartexample.RadarChartActivitry;
import com.xxmassdeveloper.mpchartexample.RealtimeLineChartActivity;
import com.xxmassdeveloper.mpchartexample.ScatterChartActivity;
import com.xxmassdeveloper.mpchartexample.ScrollViewActivity;
import com.xxmassdeveloper.mpchartexample.StackedBarActivity;
import com.xxmassdeveloper.mpchartexample.StackedBarActivityNegative;
import com.xxmassdeveloper.mpchartexample.fragments.SimpleChartDemo;
import com.xxmassdeveloper.mpchartexample.realm.RealmMainActivity;

import java.util.ArrayList;

public class MainActivity extends Activity implements OnItemClickListener {
    public float hajar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        setTitle("MPAndroidChart Example");

        // initialize the utilities
        Utils.init(this);

        ArrayList<ContentItem> objects = new ArrayList<ContentItem>();

        objects.add(new ContentItem(
                "Live Chart",
                "Data from Temprature, Humidity, Light Intensity, Soil Moisture, Soil Nutrient"));
        objects.add(new ContentItem(
                "Summary Chart",
                "Compare your data with data references"));

        MyAdapter adapter = new MyAdapter(this, objects);

        ListView lv = (ListView) findViewById(R.id.listView1);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> av, View v, int pos, long arg3) {

        Intent i;

        switch (pos) {
            case 0:
                /*i = new Intent(this, ListViewMultiChartActivity.class);
                startActivity(i);
                break;*/
                i = new Intent(this, RealtimeLineChartActivity.class);
                //i = new Intent(this, DynamicalAddingActivity.class);
                startActivity(i);
                break;

            case 1:
                i = new Intent(this, RadarChartActivitry.class);
                startActivity(i);
                break;

        }

        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent i = null;

        switch (item.getItemId()) {
            case R.id.viewGithub:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://github.com/PhilJay/MPAndroidChart"));
                startActivity(i);
                break;
            case R.id.report:
                i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "philjay.librarysup@gmail.com", null));
                i.putExtra(Intent.EXTRA_SUBJECT, "MPAndroidChart Issue");
                i.putExtra(Intent.EXTRA_TEXT, "Your error report here...");
                startActivity(Intent.createChooser(i, "Report Problem"));
                break;
            case R.id.blog:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://www.xxmassdeveloper.com"));
                startActivity(i);
                break;
            case R.id.website:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://at.linkedin.com/in/philippjahoda"));
                startActivity(i);
                break;
        }

        return true;
    } */
}
