package task;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author FHZ
 */
public class CompressImageTask implements Runnable{

    private String afterImagePath;
    private String newFilePath;
    private File file;

    public CompressImageTask(String afterImagePath, File file) {
        this.afterImagePath = afterImagePath;
        this.newFilePath = afterImagePath;
        this.file = file;
    }

    public String getAfterImagePath() {
        return afterImagePath;
    }

    public void setAfterImagePath(String afterImagePath) {
        this.afterImagePath = afterImagePath;
    }

    public String getNewFilePath() {
        return newFilePath;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
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

        BufferedImage outImageMartrix = new BufferedImage(inWidth, inHeight, type);
        for (int i = 0; i < inWidth; i++) {
            for (int j = 0; j < inHeight; j++) {
                int pixel = bi.getRGB(i * multiple + inMinx, j * multiple + inMiny);
                outImageMartrix.setRGB(i, j, pixel);
            }
        }

        try {
            newFilePath = newFilePath + imageName + ".jpg";
            ImageIO.write(outImageMartrix, "jpg", new File(newFilePath));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bi = null;
            outImageMartrix = null;
            newFilePath = afterImagePath;
        }
    }

}
