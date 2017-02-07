package com.yffsc.myandroidchart.Model;

/**
 * Created by zlt on 2017/1/17.
 */

public class ChartData {
    int data_id;
    String name;
    int PNColor;//0=正 1=负
    float num;
    String unit;//单位
    Coordinate coordinate;

    public int getData_id() {
        return data_id;
    }

    public void setData_id(int data_id) {
        this.data_id = data_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPNColor() {
        return PNColor;
    }

    public void setPNColor(int PNColor) {
        this.PNColor = PNColor;
    }

    public float getNum() {
        return num;
    }

    public void setNum(float num) {
        this.num = num;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public static class Coordinate {
        float leftX;
        float topY;
        float rightX;
        float bottomY;

        public float getLeftX() {
            return leftX;
        }

        public void setLeftX(float leftX) {
            this.leftX = leftX;
        }

        public float getTopY() {
            return topY;
        }

        public void setTopY(float topY) {
            this.topY = topY;
        }

        public float getRightX() {
            return rightX;
        }

        public void setRightX(float rightX) {
            this.rightX = rightX;
        }

        public float getBottomY() {
            return bottomY;
        }

        public void setBottomY(float bottomY) {
            this.bottomY = bottomY;
        }
    }
}
