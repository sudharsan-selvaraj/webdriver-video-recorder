package io.github.sudharsan_selvaraj.videorecorder;

public interface IVideoRecorder {

    public void start();

    public void stop();

    public void addImage(byte[] bytes);

    public String getVideoPath();

}
