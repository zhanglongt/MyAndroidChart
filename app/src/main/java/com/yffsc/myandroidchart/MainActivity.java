package com.yffsc.myandroidchart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yffsc.myandroidchart.Model.ChartData;
import com.yffsc.myandroidchart.View.View.HorizontalChart;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ChartData> barCharts;
    private HorizontalChart horizontalbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        horizontalbar= (HorizontalChart) findViewById(R.id.horizontalbar);
        barCharts = new ArrayList<>();
        for(int i=0;i<4;i++) {
            ChartData bar = new ChartData();
            //bar.setData_id(i);
            if(i==0){
                bar.setNum(10);
            }else {
                bar.setNum(100 + i * 20);
            }
            bar.setPNColor(R.color.bar_color);
            bar.setUnit("元");
            bar.setName("测试");
            barCharts.add(bar);
        }
        horizontalbar.setBarChart(barCharts);
        horizontalbar.setMinus(false);
        horizontalbar.setAlignment(HorizontalChart.CHART_CENTER);
    }
}
