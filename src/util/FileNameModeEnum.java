package util;

/**
 * @author FHZ creatre on 2020-5-4 12:15:26
 * 导出图片的文件命名模式 - FileNameModeEnum
 */
public enum FileNameModeEnum {

    /**
     * 默认模式命名
     */
    DEFAULT_EXPORT_MODE("default", "默认模式"),

    /**
     * 日期模式命名
     */
    DATE_EXPORT_MODE("date", "日期模式");

    private String modeName;
    private String modeDesc;

    FileNameModeEnum(String modeName, String modeDesc) {
        this.modeName = modeName;
        this.modeDesc = modeDesc;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public String getModeDesc() {
        return modeDesc;
    }

    public void setModeDesc(String modeDesc) {
        this.modeDesc = modeDesc;
    }

}