package flygame3.pojo;

import flygame3.common.GameSet;

import java.util.Random;

public class BadFly extends FlyingObject implements Enemy {
    private double speed = 3;  //移动步骤
    Random ran=new Random();
    int pinlv=0;//水平改变方向的频率


    /**
     * 初始化数据
     */
    public BadFly(int guanka) {
        //获取敌机图片
        if(guanka==1){
            this.image = GameSet.djImage;
        }else{//第二关
            this.image = GameSet.dj2Image;
        }

        //获取图片的宽和高
        width = image.getWidth();
        height = image.getHeight();
        //设置敌机初始位置的y轴
        y = -height;

        //设置敌机初始位置的x轴
        Random rand = new Random();
        x = rand.nextInt(GameSet.WIDTH - width);
    }

    /**
     * 获取分数
     */
    @Override
    public int getScore() {
        return 1;
    }

    /**
     * //越界处理
     */
    @Override
    public boolean outOfBounds() {
        return y > GameSet.HEIGHT;
    }

    public Bullet[] shoot() {
        int xStep = width / 2;      //6半
        int yStep = 20;  //步
        Bullet[] bullets = new Bullet[1];
        bullets[0] = new Bullet(x+xStep,y+yStep,2);
        return bullets;
    }

    /**
     * 移动
     */
    @Override
    public void move() {
        y += 3;
        x += speed/2;
        if(x > GameSet.WIDTH-width){//如果越界直接反向
            speed = -2;
            pinlv=0;//重新计数随机方向
            return;//不需要随机改变方向了
        }
        if(x < 0){//如果越界直接反向
            speed = 2;
            pinlv=0;
            return;
        }
        if(pinlv%50==0){//没有越界，随机改变方向
            int r=ran.nextInt(2);
            if(r==0){
                speed=-speed;
            }
        }
        pinlv++;
    }
}
