package flygame3.pojo;

import flygame3.common.GameSet;

public class Bullet extends FlyingObject{
    private int speed = 10;  //移动的速度
    private int direction=0;//子弹的方向


    /** 初始化数据 */
    public Bullet(double x,double y,int direction){
        this.x = x;
        this.y = y;
        this.direction=direction;
        this.image = GameSet.bullet;
        if(direction==2)
            this.image = GameSet.bullet2;
        this.width=GameSet.bullet.getWidth();
        this.height=GameSet.bullet.getHeight();
    }

    /** 移动 */
    @Override
    public void move(){
        if(direction==0)
            y-=speed;
        else if(direction==-1) {
            y=y-speed;
            x=x-0.6;
        }else if(direction==1){
            y=y-speed;
            x=x+0.6;
        }else if(direction==2){
            y+=speed;
        }
    }

    /** 越界处理 */
    @Override
    public boolean outOfBounds() {
        return y<-height;
    }

    public void not(){
        this.y=-1;
    }
}
