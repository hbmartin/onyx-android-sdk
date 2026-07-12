package com.onyx.android.sdk.api.device.screensaver;

import java.io.File;

public class ScreenSaverConfig {
    public String screenSaverName;
    public String targetFormat;
    public String targetDir;
    public int screenSaverInitialNumber;
    public int picRotateDegrees;
    public String sourcePicPathString;
    public String targetPicPathString;
    public int fullScreenPhysicalHeight;
    public int fullScreenPhysicalWidth;
    public boolean convertToGrayScale = true;

    public ScreenSaverConfig(String name, String format, String targetDir, int initialNumber, int targetWidth, int targetHeight) {
        this.screenSaverName = name;
        this.targetFormat = format;
        this.targetDir = targetDir;
        this.screenSaverInitialNumber = initialNumber;
        this.fullScreenPhysicalHeight = targetHeight;
        this.fullScreenPhysicalWidth = targetWidth;
    }

    public String createTargetPicPath(int index) {
        String str = this.targetDir + File.separator + this.screenSaverName + index + this.targetFormat;
        this.targetPicPathString = str;
        return str;
    }

    public ScreenSaverConfig copy() {
        ScreenSaverConfig screenSaverConfig = new ScreenSaverConfig(this.screenSaverName, this.targetFormat, this.targetDir, this.screenSaverInitialNumber, this.fullScreenPhysicalWidth, this.fullScreenPhysicalHeight);
        screenSaverConfig.sourcePicPathString = this.sourcePicPathString;
        screenSaverConfig.targetPicPathString = this.targetPicPathString;
        screenSaverConfig.convertToGrayScale = this.convertToGrayScale;
        screenSaverConfig.picRotateDegrees = this.picRotateDegrees;
        return screenSaverConfig;
    }
}
