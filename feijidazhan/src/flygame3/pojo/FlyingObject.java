package flygame3.pojo;

import java.awt.image.BufferedImage;

/**
 * 飞行物(敌机，蜜蜂，子弹，英雄机)
 */
public abstract class FlyingObject {
    //所有的飞行物都应该有宽、高，以及在屏幕中的位置
    //只能子类继承和使用
    protected double x;    //x坐标
    protected double y;    //y坐标
    protected int width;    //宽
    protected int height;   //高
    //这里获取的是飞行物显示的图片
    protected BufferedImage image;   //图片

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * 检查飞行物是否出界
     * @return true 出界与否
     */
    public abstract boolean outOfBounds();

    /**
     * 飞行物移动的方法，每种飞行物不一样
     */
    public abstract void move();

    public boolean shootBy(Bullet bullet){
        double x = bullet.getX();  //子弹横坐标
        double y = bullet.getY();  //子弹纵坐标
        return this.x<x && x<this.x+width && this.y<y && y<this.y+height;
    }

}

