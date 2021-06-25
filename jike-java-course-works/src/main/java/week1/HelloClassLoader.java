package week1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @description: 自定义类加载器
 * @author:
 * @create:
 * @other:
 **/

public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) throws Exception {
        Object helloObj = new HelloClassLoader().findClass("Hello").newInstance();
        Method helloMethod = helloObj.getClass().getMethod("hello");
        helloMethod.invoke(helloObj);
    }


    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {

        Path filePath = Paths.get("/Users/mtdp/jike-java-course/jike-java-course-works/src/main/java/Test/Hello.xlass");
        byte[] helloBase64 = new byte[]{};

        try {
            helloBase64 = Files.readAllBytes(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < helloBase64.length; i++ ) {
            helloBase64[i] = (byte) ((byte) 255 - helloBase64[i]);
        }
        return defineClass(name,helloBase64,0,helloBase64.length);
    }
}



