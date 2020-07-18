import task.CompressImageTask;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author FHZ creatre on 2020-4-27 09:10:05
 * 压缩图片工具执行类 - CompressImage
 */
public class CompressImage {

    /**
     * 压缩图片工具执行方法 - 程序入口
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("------------------- Task Start -------------------");
        String filePath = args[0];
        String exportFilePath = args[1];
        String modeName = args[2];

        try {
            File[] files = getAllFileByPath(filePath);
            getImage(files, exportFilePath, modeName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取 path 目录下所有的文件
     */
    public static File[] getAllFileByPath(String path) throws Exception {
        File[] files = null;
        File file = new File(path);
        if (file.isDirectory()) {
            files = file.listFiles();
            assert files != null;
            if (files.length < 1) {
                throw new Exception("[ " + path + "\\ ] 目录下无文件");
            }
            System.out.println("[ " + path + "\\ ] 目录下的图片文件如下:    ");
            for (File file1 : files) {
                System.out.println(file1.getAbsolutePath());
            }
        }
        return files;
    }

    /**
     * 压缩图片方法
     */
    public static void getImage(File[] files, String exportFilePath, String modeName) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor
                (4, 8, 200, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        for (File file : files) {
            CompressImageTask task = new CompressImageTask(exportFilePath, modeName, file);
            executor.execute(task);
        }
        executor.shutdown();
    }

}