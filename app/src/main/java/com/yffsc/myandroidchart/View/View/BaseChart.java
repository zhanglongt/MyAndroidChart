package com.yffsc.myandroidchart.View.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import com.yffsc.myandroidchart.Model.ChartData;
import com.yffsc.myandroidchart.R;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by zlt on 2017/1/17.
 */

public abstract class BaseChart extends ViewGroup{

    private static final Double GRID_HEIGHT = 0.103125;
    private static final Double GRID_WIDTH = 0.25;
    private static final Double CHART_WIDTH = 0.05625;
    private static final Double TEXT_SIZE = 0.041667;//30
    private static final Double ANIMATION_SPEED = 0.016667;// 720*0.016667
    private static final Double BAR_WIDTH = 0.1;// 720*0.1
    private static final Double DELTA_Y = 0.083334;//
    private static final Double MIN = 0.01;// 16.56
    public static final String LEFT = "left";
    public static final String CHART_LEFT = "chartLeft";
    public static final String CENTER = "center";
    public static final String CHART_CENTER = "chartCenter";
    public static final String RIGHT = "right";
    public static final String CHART_RIGHT = "chartRight";
    public BaseChart(Context context) {
        this(context,null);
    }

    public BaseChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 放入数据
     */
    ArrayList<ChartData> barChartData;
    /**
     * 设置每条柱状图的目标值，除以max即为比例
     */
    protected ArrayList<Float> respTarget;
    /**
     * 处理后的显示值
     */
    protected ArrayList<String> respTargetValue;
    /**
     * 每条柱状图的名字
     */
    protected ArrayList<String> respName;
    /**
     * 设置每条柱状图的当前比例
     */
    protected Float[] barProgress;
    /**
     * 网格背景是否显示
     */
    boolean isGridBG;
    /**
     * 网格画笔
     */
    protected Paint gridBG;

    /**
     * 柱子内数字画笔
     */
    protected Paint numP;
    /**
     * 数字字体颜色
     */
    int numPColor;

    /**
     * 柱子名字画笔
     */
    protected Paint nameP;
    /**
     * 名字字体颜色
     */
    int namePColor;

    /**
     * 柱状图画笔
     */
    protected Paint chartP;
    /**
     * 柱子颜色
     */
    int chartPColor;
    /**
     * 柱子数字大小 720 30
     */
    float numSize;

    /**
     * 柱子名字大小 720 30
     */
    float nameSize;
    /**
     * 屏幕宽
     */
    protected int screenWidth;
    /**
     * 屏幕高
     */
    protected int screenHeight;

    /**
     * 开始X坐标
     */
    protected int startX = 0;

    /**
     * 开始Y坐标
     */
    protected int startY = 0;

    /**
     * 结束X坐标
     */
    protected int stopX;

    /**
     * 结束Y坐标
     */
    protected int stopY;

    /**
     * 字体一半高度
     */
    protected float numPHeight;

    /**
     * 背景网格高比例 默认公式 screenHeight*GRID_HEIGHT=132
     */
    protected float gridHeight;

    /**
     * 背景网格宽比例 默认公式 screenWidth*GRID_WIDTH=180
     */
    protected float gridWidth;
    /**
     * 柱子宽度 screenHeight*CHART_WIDTH=72
     */
    protected float chartWidth;
    /**
     * 动画速度
     */
    protected float speedNum;// 720*0.014

    /**
     * 每条柱状图的宽度
     */
    protected float barWidth;

    /**
     * 每条竖线之间的间距
     */
    protected int deltaX;
    /**
     * 每条柱状图之间的间距 60
     */
    protected int deltaY;
    /**
     * 设置最大值，用于计算比例
     */
    protected float max;
    /**
     * 设置最小值
     */
    protected float min;
    /**
     * 透明度
     */
    protected int textAlpha;
    /**
     * 单位 默认空
     */
    protected String company = "";
    /**
     * 距离最高百分比 20%
     */
    protected float topThan=0.2f;

    /**
     *负号是否显示 默认显示
     */
    boolean minus = true;

    protected int barTotal;

    /**
     * left center right chartLeft chartCenter chartRight
     */
    protected String alignment = CHART_CENTER;

    /**
     * 放入数据
     */
    ArrayList<ChartData> barChart;
    /**
     * 处理后的显示值
     */
    protected ArrayList<String> respTargetNum;
    /**
     * 每个柱子的颜色
     */
    protected ArrayList<Integer> PNColor;

    /**
     * 初始化 nitialize all paints and stuff
     */
    protected void init(){
        setWillNotDraw(false);//不设成，ondraw就不会被执行了。
        barChartData=new ArrayList<>();
        respTarget=new ArrayList<>();
        respTargetValue=new ArrayList<>();
        respName=new ArrayList<>();
        barProgress = new Float[0];
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        isGridBG = true;
        gridBG = new Paint(Paint.ANTI_ALIAS_FLAG);
        numP = new Paint(Paint.ANTI_ALIAS_FLAG);
        nameP = new Paint(Paint.ANTI_ALIAS_FLAG);
        chartP = new Paint(Paint.ANTI_ALIAS_FLAG);
        gridBG.setColor(getResources().getColor(R.color.line_color));//网格线颜色
        numP.setColor(getResources().getColor(R.color.bar_text));//柱子类字颜色
        nameP.setColor(getResources().getColor(R.color.bar_text));//柱子名字颜色
        chartP.setColor(getResources().getColor(R.color.bar_color));//柱子颜色
        chartP.setStyle(Paint.Style.FILL);
        gridBG.setStyle(Paint.Style.FILL);
        chartP.setStrokeWidth(2);
        gridBG.setStrokeWidth(1);
        numP.setStrokeWidth(1);
        nameP.setStrokeWidth(1);
        numSize = (float) (screenWidth * TEXT_SIZE);
        nameSize = (float) (screenWidth * TEXT_SIZE);
        startX = 0;
        startY = 0;
        barTotal = 0;
        textAlpha = 0;
        min = (float) (screenWidth * MIN);
        stopX = screenWidth;
        stopY = screenHeight;
        barWidth = (float) (BAR_WIDTH * screenWidth);
        speedNum = (float) (screenWidth * ANIMATION_SPEED);
        deltaY = (int) (DELTA_Y * screenWidth);
        deltaX = (int) ((stopX - barWidth / 2 - startX) / 3 - deltaY / 5);
        //numPHeight = 0;
        numP.setTextSize(numSize);
        nameP.setTextSize(nameSize);
        gridHeight = (float) (screenHeight * GRID_HEIGHT);
        gridWidth = (float) (screenWidth * GRID_WIDTH);
        chartWidth = (float) (screenHeight * CHART_WIDTH);
        getTextHeight();
    }
    /**
     * 获得文字宽度
     */
    public float getTextWidth(String text, float size) { //第一个参数是要计算的字符串，第二个参数是字提大小
        TextPaint FontPaint = new TextPaint();
        FontPaint.setTextSize(size);
        if (numPHeight == 0) {
            getTextHeight();
        }
        return FontPaint.measureText(text);
    }

    /**
     * 获取字体一半高度
     */
    public void getTextHeight() {
        Paint.FontMetrics fontMetrics = numP.getFontMetrics();
        numPHeight = (fontMetrics.ascent + fontMetrics.descent) / 2;
    }

    DecimalFormat mFormatOne = new DecimalFormat("###,###,###,##0");
    DecimalFormat mFormatTwo = new DecimalFormat("###,###,###,##0.##");
    public String getFormatted(String num) {
        float f = Float.parseFloat(num);
        String v = String.valueOf(f);
        if (v.split("\\.")[1].equals("00") || v.split("\\.")[1].equals("0")) {
            return mFormatOne.format(Float.valueOf(v));
        } else {
            return mFormatTwo.format(Float.valueOf(v));
        }
    }
    /**
     * @param num
     * @return 返回无负号
     */
    public float getFormat(String num) {
        float f = Float.parseFloat(num);
        String v = String.valueOf(f).replace("-", "");
        if (v.split("\\.")[1].equals("00") || v.split("\\.")[1].equals("0")) {
            return Float.parseFloat(v);
        } else {
            return Float.parseFloat(v);
        }
    }

    public void setBarChart(ArrayList<ChartData> barChart) {
        max = 0;
        this.barChart = barChart;
        this.barTotal = barChart.size();
        barProgress = new Float[barChart.size()];
        respTarget = new ArrayList<>();
        respTargetNum = new ArrayList<>();
        respName = new ArrayList<>();
        PNColor = new ArrayList<>();
        for (int i = 0; i < barChart.size(); i++) {
            barProgress[i] = 0f;
        }
        for (ChartData chart : barChart) {
            respName.add(chart.getName());
            respTarget.add(getFormat(String.valueOf(chart.getNum())));
            respTargetNum.add(getFormatted(String.valueOf(respTarget.get(respTarget.size() - 1))));
            PNColor.add(chart.getPNColor());
            company = chart.getUnit();
            max = chart.getNum() < 0 ? -chart.getNum() > max ? -chart.getNum() : max : chart.getNum() > max ? chart.getNum() : max;
        }
        max = max * (1 + topThan);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isGridBG) {
            if (barTotal != 0) {
                drawVerticalLine(canvas);
                drawHorizontalLine(canvas);
            }
        }
    }

    @Override
    protected void onLayout(boolean b, int left, int top, int right, int bottom) {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).layout(left, top, right, bottom);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(screenWidth, (int) ((barWidth + deltaY) * barTotal + min / 3));
    }

    /**
     * left center right chartLeft chartCenter chartRight
     * @param alignment
     */
    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    /**
     * 设置是否显示负号 默认true
     *
     * @param minus
     */
    public void setMinus(boolean minus) {
        this.minus = minus;
    }

    /**
     * 竖线
     *
     * @param c
     */
    private void drawVerticalLine(Canvas c) {
        for (int i = 0; i < 4; i++) {
            //c.drawLine(startX + i * gridWidth, startX + i * gridWidth, startY, startY + barTotal * gridHeight, gridBG);
            c.drawLine(startX + i * gridWidth, startY, startX + i * gridWidth, startY + barTotal * gridHeight, gridBG);
        }
    }

    /**
     * 横线
     *
     * @param c
     */
    private void drawHorizontalLine(Canvas c) {
        for (int i = 0; i < barTotal + 1; i++) {
            //c.drawLine(startX, stopX, startY + i * gridHeight, startY + i * gridHeight, gridBG);
            c.drawLine(startX, startY + i * gridHeight, stopX, startY + i * gridHeight, gridBG);
        }
    }

}
