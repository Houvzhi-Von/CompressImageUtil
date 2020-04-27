import task.CompressImageTask;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author FHZ creatre on 2020-4-27 09:10:05
 * 压缩图片工具执行类 - CompressImage
 */
public class CompressImage {

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
            for (File file1 : files) {
                System.out.println(file1.getAbsolutePath());
            }
        }
        return files;
    }

    /**
     * 压缩图片方法
     */
    public static void getImage(File[] files, String exportFilePath) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor
                (4, 8, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5));
        for (File file : files) {
            CompressImageTask task = new CompressImageTask(exportFilePath, file);
            executor.execute(task);
        }
        executor.shutdown();
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        String filePath = args[0];
        String exportFilePath = args[1];
        try {
            File[] files = getAllFileByPath(filePath);
            getImage(files, exportFilePath);
            System.out.println("本次压缩图片耗时:   " + (System.currentTimeMillis() - startTime) + "  ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}