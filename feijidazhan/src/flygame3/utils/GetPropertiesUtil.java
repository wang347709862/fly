package flygame3.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetPropertiesUtil {

    //获取properties文件内容
    public static String getValue(String key) throws IOException {
        Properties ps = new Properties();
        InputStream in = GetPropertiesUtil.
                class.getClassLoader().getResourceAsStream("flygame3/gameset.properties");
        ps.load(in);
        String imagePath = ps.getProperty(key);

        return imagePath;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(getValue("backgroundImage"));
    }
}
