package flygame3;

import flygame3.common.GameSet;
import javax.swing.*;

public class GameStart {
    public static void main(String[] args) {
        JFrame frame = new JFrame("东方幻想乡");
        frame.setSize(GameSet.WIDTH, GameSet.HEIGHT); // 设置窗口大小跟面板一样大
        frame.setAlwaysOnTop(true); // 设置其总在最上
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 默认关闭操作
        //frame.setIconImage(new ImageIcon("images/icon.jpg").getImage()); // 设置窗体的图标
        frame.setLocationRelativeTo(null); // 设置窗体初始位置

        GameSet game = new GameSet(); // 创建面板
        frame.add(game); // 将面板添加到JFrame中
        frame.setVisible(true); // 尽快调用paint
        game.action(); // 启动执行
    }
}
