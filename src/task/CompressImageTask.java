package task;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author FHZ creatre on 2020-4-27 09:10:05
 * 压缩图片线程任务处理类 - CompressImageTask
 */
public class CompressImageTask implements Runnable {

    /**
     * 压缩后图片的导出路径
     */
    private final String exportFilePath;

    /**
     * 压缩后图片的全路径（包含文件名称及后缀）
     */
    private String newFilePath;

    /**
     * 源图片文件
     */
    private final File file;

    public CompressImageTask(String exportFilePath, File file) {
        this.exportFilePath = exportFilePath;
        this.newFilePath = exportFilePath + "\\";
        this.file = file;
    }

    @Override
    public void run() {
        String beforeImagePath = file.getAbsolutePath();
        int begin = beforeImagePath.lastIndexOf("\\");
        int end = beforeImagePath.lastIndexOf(".");
        String imageName = "new_" + beforeImagePath.substring(begin + 1, end);

        File inFile = new File(beforeImagePath);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(inFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert bi != null;
        int inWidth = bi.getWidth();
        int inHeight = bi.getHeight();
        int inMinx = bi.getMinX();
        int inMiny = bi.getMinY();
        int type = bi.getType();
        int multiple = 1;

        BufferedImage outImageMatrix = new BufferedImage(inWidth, inHeight, type);
        for (int i = 0; i < inWidth; i++) {
            for (int j = 0; j < inHeight; j++) {
                int pixel = bi.getRGB(i * multiple + inMinx, j * multiple + inMiny);
                outImageMatrix.setRGB(i, j, pixel);
            }
        }

        try {
            newFilePath = newFilePath + imageName + ".jpg";
            ImageIO.write(outImageMatrix, "jpg", new File(newFilePath));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bi = null;
            outImageMatrix = null;
            newFilePath = exportFilePath;
        }
    }

}
