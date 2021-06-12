package io.github.sudharsan_selvaraj.videorecorder;

import org.jcodec.api.awt.AWTSequenceEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.BufferOverflowException;

public class VideoRecorder implements IVideoRecorder {

    private VideoOptions options;
    private AWTSequenceEncoder encoder;
    private boolean isStarted;
    private boolean isCompleted;

    public VideoRecorder(VideoOptions options) {
        this.options = options;
        this.isCompleted = false;
        this.isStarted = false;
    }

    @Override
    public void start() {
        try {
            if (!isStarted) {
                encoder = AWTSequenceEncoder.createSequenceEncoder(new File(options.getOutputPath()), options.getFramesPerSecond());
                isStarted = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        if (isStarted && !isCompleted) {
            try {
                encoder.finish();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                isCompleted = true;
            }
        }
    }

    @Override
    public void addImage(byte[] bytes) {
        if (isStarted && !isCompleted) {
            try {
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
                encoder.encodeImage(resize(image));
            } catch (Throwable e) {
                if(!(e instanceof BufferOverflowException)) {
                    e.printStackTrace();
                }
            }
        }
    }

    private BufferedImage resize(BufferedImage img) {
        if(options.getVideoDimension() == null) {
            return img;
        }
        int height = options.getVideoDimension().getHeight();
        int width = options.getVideoDimension().getWidth();
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_FAST);
        BufferedImage dimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    @Override
    public String getVideoPath() {
        return options.getOutputPath();
    }

}
