package io.github.sudharsan_selvaraj.videorecorder;

import io.github.sudharsan_selvaraj.SpyDriver;
import org.openqa.selenium.WebDriver;

public class WebDriverVideoRecorder {

    private final SpyDriver spyDriver;
    private final DriverEventListener listener;

    public WebDriverVideoRecorder(SpyDriver spyDriver, VideoOptions options) {
        this.spyDriver = spyDriver;
        this.listener = new DriverEventListener(new VideoRecorder(options));
        this.spyDriver.addListener(this.listener);
    }

    private void pause() {
        this.listener.pause();
    }

    private void resume() {
        this.listener.resume();
    }

    public WebDriver getDriver() {
        return spyDriver.getSpyDriver();
    }
}
