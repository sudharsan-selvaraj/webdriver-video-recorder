package io.github.sudharsan_selvaraj.videorecorder;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.Dimension;

@Getter
@Setter
public class VideoOptions {

    private int framesPerSecond;
    private String outputPath;
    private Dimension videoDimension;

    public VideoOptions(String outputPath) {
        this(outputPath,1);
    }


    public VideoOptions(String outputPath, int framesPerSecond) {
        this(outputPath, framesPerSecond, null);
    }

    public VideoOptions(String outputPath, int framesPerSecond, Dimension videoDimension) {
        this.setOutputPath(outputPath);
        this.setFramesPerSecond(framesPerSecond);
        this.setVideoDimension(videoDimension);
    }
}
