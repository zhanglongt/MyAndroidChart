package com.yffsc.myandroidchart.View.View;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.yffsc.myandroidchart.Model.ChartData;
import com.yffsc.myandroidchart.R;

/**
 * Created by zlt on 2017/2/7.
 */

public class HorizontalChart extends BaseChart{

    private Context context;
    public HorizontalChart(Context context) {
        super(context);
        this.context = context;
    }

    public HorizontalChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public HorizontalChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void init() {
        super.init();
    }

    /**
     * 测量方法，主要考虑宽和高设置为wrap_content的时候，我们的view的宽高设置为多少
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        //如果宽和高都为wrap_content的时候，我们将宽设置为我们输入的max值，也就是柱状图的最大值
        //高度为每条柱状图的宽度加上间距再乘以柱状图条数再加上开始Y值后得到的值
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension((int) max, (int) (barTotal * (barWidth + deltaY) + barWidth / 2));
            //如果宽度为wrap_content  高度为match_parent或者精确数值的时候
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            //宽度设置为max，高度为父容器高度
            setMeasuredDimension((int) max, heightSpecSize);
            //如果宽度为match_parent或者精确数值的时候，高度为wrap_content
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            //宽度设置为父容器的宽度，高度为每条柱状图的宽度加上间距再乘以柱状图条数再加上开始Y值后得到的值
            setMeasuredDimension(widthSpecSize, (int) (barTotal * (barWidth + deltaY) + barWidth / 2));
        }
    }
    int abc = 0;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        abc++;
        if (barTotal != 0) {
            drawBarChart(canvas);
        }
    }
    private void drawBarChart(Canvas c) {
        for (int i = 0; i < barTotal; i++) {
            if (barChart.get(i).getCoordinate() == null) {
                ChartData.Coordinate coordinate = new ChartData.Coordinate();
                coordinate.setLeftX(startX);
                coordinate.setTopY(startY + deltaY / 2 + i * (deltaY + barWidth));
                coordinate.setBottomY(startY + deltaY / 2 + i * (deltaY + barWidth) + barWidth);
                barChart.get(i).setCoordinate(coordinate);
            }
            if (barProgress[i] < (respTarget.get(i) / max) * stopX) {
                barProgress[i] += speedNum;//每次增加12像素 720*0.014
                barChart.get(i).getCoordinate().setRightX(barProgress[i] - speedNum + min);
                postInvalidateDelayed((long) speedNum);//每12毫秒重绘一次
            }
            chartP.setColor(getResources().getColor(PNColor.get(i)));
            if (respTarget.get(i) == 0) {
                c.drawRect(startX,
                        startY + deltaY / 2 + i * (deltaY + barWidth),
                        startX,
                        startY + deltaY / 2 + i * (deltaY + barWidth) + barWidth,
                        chartP);
            } else {
                c.drawRect(startX,
                        startY + deltaY / 2 + i * (deltaY + barWidth),
                        barProgress[i] - speedNum + min,
                        startY + deltaY / 2 + i * (deltaY + barWidth) + barWidth,
                        chartP);
            }
            //c.drawText(respName.get(i), stopX - barWidth / 2, startY + i * (deltaY + barWidth) + 3 * barWidth / 4 - barWidth + 2, numP);
            if (barProgress[i] >= (respTarget.get(i) / max) * stopX) {
                if (textAlpha < 255) {
                    textAlpha += 5;
                    numP.setAlpha(textAlpha);
                    postInvalidateDelayed(51);//每100毫秒重绘一次
                }
                //float textWidth = getTextWidth(PNColor.get(i) == R.color.bar_color ? String.valueOf(respTarget.get(i)) : "-" + String.valueOf(respTarget.get(i)), numP.getTextSize());
//                c.drawText(PNColor.get(i) == R.color.bar_color ? respTargetNum.get(i) : "-" + respTargetNum.get(i),
//                        textWidth < barChart.get(i).getCoordinate().getRightX() - barChart.get(i).getCoordinate().getLeftX() ? (barProgress[i] + min - startX) / 2 - textWidth / 2 + min : (float) (barProgress[i] + min * 1.25),
//                        barChart.get(i).getCoordinate().getTopY() + (barChart.get(i).getCoordinate().getBottomY() - barChart.get(i).getCoordinate().getTopY()) / 2 - numPHeight,
//                        numP);
                String barText;
                if (minus) {
                    barText = PNColor.get(i) == R.color.bar_color ? respName.get(i) + ":" + respTargetNum.get(i) + company : respName.get(i) + ":" + "-" + respTargetNum.get(i) + company;
                } else {
                    barText = respName.get(i) + ":" + respTargetNum.get(i) + company;
                }
                float textWidth = getTextWidth(barText, numP.getTextSize()) + 5;
                float textY = barChart.get(i).getCoordinate().getTopY() + (barChart.get(i).getCoordinate().getBottomY() - barChart.get(i).getCoordinate().getTopY()) / 2 - numPHeight;
                switch (alignment) {
                    case LEFT:
                        c.drawText(barText
                                , textWidth < barChart.get(i).getCoordinate().getRightX() ? barChart.get(i).getCoordinate().getLeftX() : barChart.get(i).getCoordinate().getRightX()
                                , textY
                                , numP
                        );
                        break;
                    case CENTER:
                        c.drawText(barText
                                , (stopX-startX)/2 - textWidth / 2
                                , textY
                                , numP
                        );
                        break;
                    case RIGHT:
                        c.drawText(barText
                                , textWidth < barChart.get(i).getCoordinate().getRightX() ? barChart.get(i).getCoordinate().getRightX() - textWidth : barChart.get(i).getCoordinate().getRightX()
                                , textY
                                , numP
                        );
                        break;
                    case CHART_CENTER:
                        c.drawText(barText
                                , textWidth < barChart.get(i).getCoordinate().getRightX() ? barChart.get(i).getCoordinate().getRightX()/2 - textWidth/2 : barChart.get(i).getCoordinate().getRightX()
                                , textY
                                , numP
                        );
                        break;
                    case CHART_LEFT:
                        break;
                    case CHART_RIGHT:
                        break;
                }

            }
        }
    }

    /**
     * 定义一个接口对象listerner
     */
    private OnItemSelectListener listener;

    /**
     * 获得接口监听的方法。
     *
     * @param listener
     */
    public void setOnItemSelectListener(OnItemSelectListener listener) {
        this.listener = listener;
    }

    /**
     * 定义一个接口
     */
    public interface OnItemSelectListener {
        void onItemSelect(int index, ChartData chart);
    }
    private float y;
    private float x;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                float x1 = event.getX();
                float y1 = event.getY();
                if (x - 10 < x1 && x + 10 > x1 && y - 10 < y1 && y + 10 > y1) {
                    for (int i = 0; i < barTotal; i++) {
                        if (barChart.get(i).getCoordinate().getTopY() < y1 + deltaY / 2 && barChart.get(i).getCoordinate().getBottomY() > y1 - deltaY / 2) {
                            if (listener != null) {
                                listener.onItemSelect(i, barChart.get(i));
                            }
                        }
                    }
                    return true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
