package server;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author wangdm
 * @description 查找固定文件夹内的class文件
 */
public class ClassLoaderExpand extends ClassLoader {

    protected Class<?> findClass(String basePath, String name) {
        String myPath = "file:///"+basePath+name.replaceAll("\\.","/")+".class";
        byte[] classBytes = null;
        Path path = null;
        try {
            path = Paths.get(new URI(myPath));
            classBytes = Files.readAllBytes(path);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        Class<?> aClass = defineClass(name, classBytes, 0, classBytes.length);
        return aClass;
    }

}

