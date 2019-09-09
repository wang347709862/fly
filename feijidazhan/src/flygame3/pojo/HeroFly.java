package flygame3.pojo;

import flygame3.common.GameSet;
import java.awt.image.BufferedImage;

/**
 * 英雄飞机类
 */
public class HeroFly extends FlyingObject{
    private BufferedImage[] images = {};  //英雄机图片
    private BufferedImage[] images2 = {};  //英雄机图片
    private int index = 0;                //英雄机图片切换索引

    private int doubleFire;   //双倍火力
    private int life;   //生命值

    /** 无参构造方法，创建飞机时，初始化基本数据 */
    public HeroFly(){
        life = 3;   //初始3条生命
        doubleFire = 0;   //初始火力为0，单倍火力
        images = new BufferedImage[]{GameSet.hero0Image,GameSet.hero1Image,GameSet.hero2Image,GameSet.hero3Image};
        images2 = new BufferedImage[]{GameSet.hero4Image,GameSet.hero5Image};
        //英雄机图片数组
        image = GameSet.hero0Image;   //初始为hero0图片

        //设置飞机的宽高为图片的宽和高
        width = image.getWidth();
        height = image.getHeight();
        //设置飞机在屏幕上的初始位置
        x = 150;
        y = 400;
    }

    public int getDoubleFire() {
        return doubleFire;
    }
    /** 设置双倍火力 */
    public void setDoubleFire(int doubleFire) {
        this.doubleFire = doubleFire;
    }

    /** 增加火力 */
    public void addDoubleFire(){
        if(doubleFire ==0)
            doubleFire = 40;
        else if(doubleFire==40){
            doubleFire = 80;
        }
    }

    /** 增命 */
    public void addLife(){  //增命
        life++;
    }
    public void subLife(){  //增命
        life--;
    }
    /** 减命 */
    public void subtractLife(){   //减命
        life--;
    }

    /** 获取命 */
    public int getLife(){
        return life;
    }

    /** 当前物体移动了一下，相对距离，x,y鼠标位置  */
    public void moveTo(int x,int y){
        this.x = x - width/2;
        this.y = y - height/2;
    }

    /** 越界处理 */
    @Override
    public boolean outOfBounds() {
        return false;
    }

    /** 发射子弹 */
    public Bullet[] shoot(){
        int xStep = width/6;      //6半
        int yStep = 20;  //步
        if(doubleFire==40){  //双倍火力
            Bullet[] bullets = new Bullet[2];
            bullets[0] = new Bullet(x+2*xStep,y-yStep,0);  //y-yStep(子弹距飞机的位置)
            bullets[1] = new Bullet(x+4*xStep,y-yStep,0);
            return bullets;
        }else if(doubleFire==0){      //单倍火力
            Bullet[] bullets = new Bullet[1];
            bullets[0] = new Bullet(x+3*xStep,y-yStep,0);
            return bullets;
        }else{//3倍火力
            Bullet[] bullets = new Bullet[3];
//            bullets[0] = new Bullet(x-4*xStep,y-yStep,0);
//            bullets[1] = new Bullet(x+4*xStep,y-yStep,0);
//            bullets[2] = new Bullet(x,y-yStep,0);
//            bullets[3] = new Bullet(x+2*xStep,y-yStep,0);
//            bullets[4] = new Bullet(x+4*xStep,y-yStep,0);
//            bullets[5] = new Bullet(x+6*xStep,y-yStep,0);
//            bullets[6] = new Bullet(x+8*xStep,y-yStep,0);
//            bullets[9] = new Bullet(x+10*xStep,y-yStep,0);
//            bullets[7] = new Bullet(x-6*xStep,y-yStep,0);
//            bullets[8] = new Bullet(x-8*xStep,y-yStep,0);
//            bullets[9] = new Bullet(x+2*xStep,y-yStep,0);
            bullets[0] = new Bullet(x+xStep,y-yStep,-1);  //y-yStep(子弹距飞机的位置)
            bullets[1] = new Bullet(x+3*xStep,y-yStep,0);
            bullets[2] = new Bullet(x+5*xStep,y-yStep,1);
            return bullets;
        }
    }

    /** 移动 */
    @Override
    public void move() {
        if(doubleFire==80){
            image=images2[index++/10%images2.length];
            return;
        }
        if(images.length>0){
            image = images[index++/10%images.length];  //切换图片hero0，hero1
        }
    }

    /** 碰撞算法 */
    public boolean hit(FlyingObject other){

        double x1 = other.x - this.width/2;                 //x坐标最小距离
        double x2 = other.x + this.width/2 + other.width;   //x坐标最大距离
        double y1 = other.y - this.height/2;                //y坐标最小距离
        double y2 = other.y + this.height/2 + other.height; //y坐标最大距离

        double herox = this.x + this.width/2;               //英雄机x坐标中心点距离
        double heroy = this.y + this.height/2;              //英雄机y坐标中心点距离

        return herox>x1 && herox<x2 && heroy>y1 && heroy<y2;   //区间范围内为撞上了
    }

}
