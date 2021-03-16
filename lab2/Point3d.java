package com.company;
/**
* Трехмерный класс точки
**/
public class Point3d {
    /*Координата X*/
    private double xCoord;
    /*Координата Y*/
    private double yCoord;
    /*Координата Z*/
    private double zCoord;
    /*Конструктор иницализации*/
    public Point3d(double x, double y, double z){
        xCoord = x;
        yCoord = y;
        zCoord = z;
    }
    /*Конструктор по умолчанию*/
    public Point3d(){
        //Вызов конструктора с тремя параметрами
        this(0.0, 0.0, 0.0);
    }
    /*Возвращение значения X*/
    public double getX() {
        return xCoord;
    }
    /*Возвращение значения Y*/
    public double getY(){
        return yCoord;
    }
    /*Возвращение значения Z*/
    public double getZ() {
        return zCoord;
    }
    /*Установка координаты X*/
    public void setX(double val){
        this.xCoord = val;
    }
    /*Установка координаты Y*/
    public void setY(double val){
        this.yCoord = val;
    }
    /*Установка координаты Z*/
    public void setZ(double val){
        this.zCoord = val;
    }
    /*Сравнение двух точек по координатам*/
    public boolean isEqual(Point3d other){
        return (this.xCoord == other.xCoord & this.yCoord == other.yCoord & this.zCoord == other.zCoord);
    }
    /*Нахождение расстояния между двумя точками*/
    public double distanceTo(Point3d other){
        return Math.round((Math.sqrt(Math.pow(this.xCoord - other.xCoord, 2)
                + Math.pow(this.yCoord - other.yCoord, 2) +
                Math.pow(this.zCoord - other.zCoord, 2))) * 100 / 100);
    }
}
