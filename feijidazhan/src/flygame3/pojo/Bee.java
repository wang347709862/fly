package flygame3.pojo;

import flygame3.common.GameSet;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Bee extends FlyingObject implements Award{
    private int xSpeed = 1;   //x坐标移动速度
    private int ySpeed = 2;   //y坐标移动速度

    private int index = 0;
    private int awardType;    //奖励类型
    private BufferedImage[] images=new BufferedImage[]{GameSet.beeImage,GameSet.bee2Image};

    /** 初始化数据 */
    public Bee(){
        //设置图片
        this.image = GameSet.beeImage;
        //设置宽高
        width = image.getWidth();
        height = image.getHeight();
        //设置位置
        y = -height;
        Random rand = new Random();
        x = rand.nextInt(GameSet.WIDTH - width);
        awardType = rand.nextInt(2);   //初始化时就设置好奖励的内容
    }

    /** 获得奖励类型 */
    public int getType(){
        return awardType;
    }

    /** 越界处理 */
    @Override
    public boolean outOfBounds() {
        return y>GameSet.HEIGHT;
    }

    /** 移动，可斜着飞，不要超过边界 */
    @Override
    public void move() {
        x += xSpeed;
        y += ySpeed;
        image=images[index++/10%images.length];
        if(x > GameSet.WIDTH-width){
            xSpeed = -1;
        }
        if(x < 0){
            xSpeed = 1;
        }
    }

}
