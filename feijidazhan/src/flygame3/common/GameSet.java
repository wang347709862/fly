package flygame3.common;

import flygame3.pojo.*;
import flygame3.utils.GetPropertiesUtil;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class GameSet extends JPanel {
    //设置面板宽和高
    public static int WIDTH; // 面板宽
    public static int HEIGHT; // 面板高

    /**
     * 游戏的当前状态: START RUNNING PAUSE GAME_OVER
     */
    private int state;
    private static final int START = 0;
    private static final int RUNNING = 1;
    private static final int PAUSE = 2;
    private static final int GAME_OVER = 3;
    private static final int XIAYIGUAN = 4;
    private int guanka=1;//第一关


    //系统设置
    public static int score = 0; // 得分
    private Timer timer; // 定时器
    private int intervel = 10; // 时间间隔(毫秒)

    //图片获取
    public static BufferedImage backgroundImage;
    public static BufferedImage background2Image;
    public static BufferedImage startImage;
    public static BufferedImage djImage;
    public static BufferedImage dj2Image;
    public static BufferedImage beeImage;
    public static BufferedImage bee2Image;
    public static BufferedImage bullet;
    public static BufferedImage bullet2;
    public static BufferedImage hero0Image;
    public static BufferedImage hero1Image;
    public static BufferedImage hero2Image;
    public static BufferedImage hero3Image;
    public static BufferedImage hero4Image;
    public static BufferedImage hero5Image;
    public static BufferedImage pauseImage;
    public static BufferedImage xiayiguanImage;
    public static BufferedImage gameOver;

    //飞行物准备
    private List<FlyingObject> badFlys = new ArrayList<FlyingObject>(); // 敌机集合
    private List<Bullet> bullets = new ArrayList<Bullet>(); // 子弹集合
    private List<Bullet> bullets2 = new ArrayList<Bullet>(); // 敌人子弹集合

    private HeroFly hero = new HeroFly(); // 英雄机

    // 静态代码块，初始化资源
    static {
        try {
            WIDTH = Integer.parseInt(GetPropertiesUtil.getValue("WIDTH"));
            HEIGHT = Integer.parseInt(GetPropertiesUtil.getValue("HEIGHT"));

            backgroundImage = getImage("backgroundImage");
            background2Image = getImage("background2Image");
            startImage = getImage("startImage");
            djImage = getImage("djImage");
            dj2Image = getImage("dj2Image");
            beeImage = getImage("beeImage");
            beeImage = getImage("bee2Image");
            bullet = getImage("bullet");
            bullet2 = getImage("bullet2");
            hero0Image = getImage("hero0Image");
            hero1Image = getImage("hero1Image");
            hero2Image = getImage("hero2Image");
            hero3Image = getImage("hero3Image");
            hero4Image = getImage("hero4Image");
            hero5Image = getImage("hero5Image");
            pauseImage = getImage("pauseImage");
            xiayiguanImage = getImage("xiayiguanImage");
            gameOver = getImage("gameOver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取BufferedImage对象
    public static BufferedImage getImage(String pathName) throws IOException {
        String imagePath = GetPropertiesUtil.getValue(pathName);
        BufferedImage read = ImageIO.read(GameSet.class.getClassLoader().getResourceAsStream(imagePath));
        return read;
    }


    //在面板上画英雄飞机、敌机、子弹
    @Override
    public void paint(Graphics g) {
        if(guanka==1)
            g.drawImage(backgroundImage, 0, 0, null); // 画背景图
        else{
            g.drawImage(background2Image, 0, 0, null); // 画关卡2背景图
        }
        paintHero(g); // 画英雄机
        paintBullets(g); // 画子弹
        paintFlyingObjects(g); // 画飞行物
        paintScore(g); // 画分数
        paintState(g); // 画游戏状态
    }

    /**
     * 画英雄机
     */
    public void paintHero(Graphics g) {
        g.drawImage(hero.getImage(), (int) hero.getX(), (int) hero.getY(), null);
    }

    /**
     * 画子弹
     */
    public void paintBullets(Graphics g) {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            g.drawImage(b.getImage(), (int) b.getX() - b.getWidth() / 2, (int) b.getY(),
                    null);
        }
        for (int i = 0; i < bullets2.size(); i++) {
            Bullet b = bullets2.get(i);
            g.drawImage(b.getImage(), (int) b.getX() - b.getWidth() / 2, (int) b.getY(),
                    null);
        }
    }

    /**
     * 画敌机
     */
    public void paintFlyingObjects(Graphics g) {
        for (int i = 0; i < badFlys.size(); i++) {
            FlyingObject f = badFlys.get(i);
            g.drawImage(f.getImage(), (int) f.getX(), (int) f.getY(), null);
        }
    }

    /**
     * 画分数
     */
    public void paintScore(Graphics g) {
        int x = 10; // x坐标
        int y = 25; // y坐标
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18); // 字体
        g.setColor(new Color(0x3A3B3B));
        g.setFont(font); // 设置字体
        g.drawString("SCORE:" + score, x, y); // 画分数
        y += 20; // y坐标增20
        g.drawString("LIFE:" + hero.getLife(), x, y); // 画生命值
    }

    /**
     * 画游戏状态，不同状态有不同的画面
     */
    public void paintState(Graphics g) {
        switch (state) {
            case START: // 启动状态
                g.drawImage(startImage, 0, 0, null);
                break;
            case PAUSE: // 暂停状态
                g.drawImage(pauseImage, 0, 0, null);
                break;
            case GAME_OVER: // 游戏终止状态
                g.drawImage(gameOver, 0, 0, null);
                break;
            case XIAYIGUAN: // 下一关状态
                g.drawImage(xiayiguanImage, 100, 220, null);
                guanka++;
                break;

        }
    }

    /**
     * 启动执行代码
     */
    public void action() {
        // 鼠标监听事件
        MouseAdapter l = new MouseAdapter() {
            // 鼠标在界面移动
            @Override
            public void mouseMoved(MouseEvent e) {
                // 游戏运行状态下，英雄机随鼠标位置移动
                if (state == RUNNING) {
                    int x = e.getX();
                    int y = e.getY();
                    hero.moveTo(x, y);
                }
            }

            // 鼠标进入界面
            @Override
            public void mouseEntered(MouseEvent e) {
                // 游戏从暂停状态下进入运行状态
                if (state == PAUSE) {
                    state = RUNNING;
                }
            }

            // 鼠标退出界面
            @Override
            public void mouseExited(MouseEvent e) {
                // 游戏未结束，则设置游戏状态为暂停
                if (state != GAME_OVER && state != START) {
                    state = PAUSE;
                }
            }

            // 鼠标在界面点击
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (state) {
                    case START:
                        state = RUNNING; // 启动状态下运行
                        break;
                    case GAME_OVER:
                    // 游戏结束，清理现场
                        badFlys.removeAll(badFlys); // 清空飞行物
                        bullets.removeAll(bullets); // 清空子弹
                        bullets2.removeAll(bullets2); // 清空子弹
                        hero = new HeroFly(); // 重新创建英雄机
                        score = 0; // 清空成绩
                        state = START; // 状态设置为启动
                        break;
                    case XIAYIGUAN:
                        badFlys.removeAll(badFlys); // 清空飞行物
                        bullets.removeAll(bullets); // 清空子弹
                        bullets2.removeAll(bullets2); // 清空子弹
                        hero = new HeroFly(); // 重新创建英雄机
                        score = 0;
                        state = RUNNING;
                }
            }
        };

        this.addMouseListener(l); // 加载处理鼠标点击操作
        this.addMouseMotionListener(l); // 加载处理鼠标滑动操作

        timer = new Timer(); // 主流程控制,启动定时器任务
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (score > 30) {
                    state=XIAYIGUAN;
                    repaint();
                    return;
                }
                if (state == RUNNING) { // 运行状态
                    enterAction(); // 飞行物入场
                    stepAction(); // 飞行物运行
                    shootAction(); // 英雄机射击
                    bangAction(); // 子弹打飞行物
                    outOfBoundsAction(); // 删除越界飞行物及子弹
                    checkGameOverAction(); // 检查游戏结束
                }
                repaint(); // 重绘，调用paint()方法
            }

        }, intervel, intervel);
    }


    int flyEnteredIndex = 0; // 飞行物入场计数，统计出现了多少个飞行物

    /**
     * 飞行物入场
     */
    public void enterAction() {
        flyEnteredIndex++;
        if (flyEnteredIndex % 40 == 0) { // 400毫秒生成一个飞行物--10*40
            FlyingObject obj = nextOne(); // 随机生成一个飞行物
            badFlys.add(obj);
        }

    }

    /**
     * 飞行物运动
     */
    public void stepAction() {
        for (int i = 0; i < badFlys.size(); i++) { // 飞行物走一步
            FlyingObject f = badFlys.get(i);
            f.move();
        }

        for (int i = 0; i < bullets.size(); i++) { // 子弹走一步
            Bullet b = bullets.get(i);
            b.move();
        }

        for (int i = 0; i < bullets2.size(); i++) {
            Bullet b = bullets2.get(i);
            b.move();
        }
        hero.move(); // 英雄机走一步
    }


    int shootIndex = 0; // 射击计数
    /**
     * 射击
     */
    public void shootAction() {
        shootIndex++;
        if (shootIndex % 30 == 0) { // 英雄机300毫秒发一颗
            Bullet[] bs = hero.shoot(); // 英雄打出子弹

            for (int i = 0; i < bs.length; i++) {
                bullets.add(bs[i]);
            }
        }
        if (shootIndex % 60 == 0) {// 敌机600毫秒发一颗
            for (int i = 0; i < badFlys.size(); i++) {
                if (badFlys.get(i) instanceof Enemy) {//只有得分型敌机才能发子弹，奖励型不可以
                    BadFly f = (BadFly) badFlys.get(i);
                    Bullet[] bs2 = f.shoot();// 敌机打出子弹
                    for (int j = 0; j < bs2.length; j++) {
                        bullets2.add(bs2[j]);
                    }
                }
            }
        }
    }

    /**
     * 子弹与飞行物碰撞检测
     */
    public void bangAction() {
        for (int i = 0; i < bullets.size(); i++) { // 遍历所有子弹
            Bullet b = bullets.get(i);
            bang(b); // 子弹和飞行物之间的碰撞检查
        }
        for (int i = 0; i < bullets2.size(); i++) {
            Bullet b = bullets2.get(i);
            bang2(b);
        }
    }

    /**
     * 删除越界飞行物及子弹
     */
    public void outOfBoundsAction() {
        for (int i = 0; i < badFlys.size(); i++) {
            if (badFlys.get(i).outOfBounds()) {
                badFlys.remove(i);
            }
        }
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            if (b.outOfBounds()) {
                bullets.remove(i);
            }
        }
        for (int i = 0; i < bullets2.size(); i++) {
            Bullet b = bullets2.get(i);
            if (b.outOfBounds()) {
                bullets2.remove(i);
            }
        }
    }

    /**
     * 检查游戏结束，改变界面状态
     */
    public void checkGameOverAction() {
        if (isGameOver()) {
            state = GAME_OVER; // 改变状态
        }
    }

    /**
     * 检查游戏是否结束
     */
    public boolean isGameOver() {
        for (int i = 0; i < badFlys.size(); i++) {
            FlyingObject obj = badFlys.get(i);
            if (hero.hit(obj)) { // 检查英雄机与飞行物是否碰撞
                hero.subtractLife(); // 减命
                if (hero.getDoubleFire() == 40) {
                    hero.setDoubleFire(0); // 双倍火力解除
                } else if (hero.getDoubleFire() == 80) {
                    hero.setDoubleFire(40); // 三倍火力解除
                }
                badFlys.remove(i);// 删除碰上的飞行物
            }
        }
        return hero.getLife() <= 0;
    }

    /**
     * 子弹和飞行物之间的碰撞检查
     */
    public void bang2(Bullet bullet) {
        if (hero.shootBy(bullet)) {
            hero.subLife();
            bullet.not();
        }
    }

    public void bang(Bullet bullet) {
        for (int i = 0; i < badFlys.size(); i++) {
            FlyingObject obj = badFlys.get(i);
            // 判断是否击中
            if (obj.shootBy(bullet)) {
                // 检查obj的类型(敌人加分，奖励获取)
                if (obj instanceof Enemy) { // 检查类型，是敌人，则加分
                    Enemy e = (Enemy) obj; // 强制类型转换
                    score += e.getScore(); // 加分
                } else if (obj instanceof Award) { // 若为奖励，设置奖励
                    Award a = (Award) obj;
                    int type = a.getType(); // 获取奖励类型
                    switch (type) {
                        case Award.DOUBLE_FIRE:
                            hero.addDoubleFire(); // 设置双倍火力
                            break;
                        case Award.LIFE:
                            hero.addLife(); // 设置加命
                            break;
                    }
                }
                //移除被击中的飞行物
                badFlys.remove(i);
                bullet.not();
            }
        }

    }

    /**
     * 随机生成飞行物
     *
     * @return 飞行物对象
     */
    public  FlyingObject nextOne() {

        Random random = new Random();
        int type = random.nextInt(2); // [0,20)
        if (type == 0) {
            return new Bee();
        } else {
            if(guanka==1){
                return new BadFly(1);
            }else{
                return new BadFly(2);
            }

        }
    }
}
