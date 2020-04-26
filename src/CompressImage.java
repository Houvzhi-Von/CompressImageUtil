import task.CompressImageTask;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author fengouzhi
 */
public class CompressImage {

    /**
     * 获取 path 目录下所有的文件
     */
    public static File[] getAllFileByPath(String path) {
        File[] files = null;
        File file = new File(path);
        if (file.isDirectory()) {
            files = file.listFiles();
            for (File file1 : files) {
                System.out.println(file1.getAbsolutePath());
            }
        }
        return files;
    }

    /**
     * 压缩图片方法
     */
    public static void getImage(File[] files, String afterImagePath) throws Exception {
        if (files.length < 1) {
            throw new Exception("目录下无文件");
        }

        ThreadPoolExecutor executor = new ThreadPoolExecutor
                (4, 8, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5));
        for (File file : files) {
            CompressImageTask task = new CompressImageTask(afterImagePath, file);
            executor.execute(task);
        }
        executor.shutdown();
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        //String filePath = System.getProperty("filePath");
        //String exportFilePath = System.getProperty("exportFilePath");

        String filePath = "C:\\Users\\FHZ\\Pictures\\test\\";
        String exportFilePath = "C:\\Users\\FHZ\\Pictures\\test\\";
        File[] files = getAllFileByPath(filePath);

        try {
            getImage(files, exportFilePath);
            //getImage(files, afterImagePath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("本次压缩图片耗时:   " + (System.currentTimeMillis() - startTime) + "  ms");
        }
    }

    //String afterImagePath =
    //File[] files = getAllFileByPath(afterImagePath);

}