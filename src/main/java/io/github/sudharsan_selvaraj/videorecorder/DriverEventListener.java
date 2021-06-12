package io.github.sudharsan_selvaraj.videorecorder;

import io.github.sudharsan_selvaraj.SpyDriverListener;
import io.github.sudharsan_selvaraj.types.BaseCommand;
import io.github.sudharsan_selvaraj.types.driver.DriverCommandResult;
import io.github.sudharsan_selvaraj.types.element.ElementCommandResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.List;

public class DriverEventListener implements SpyDriverListener {

    private final IVideoRecorder videoRecorder;
    private static final List<String> allowedMethods;
    private boolean isPaused = false;

    static {
        allowedMethods = Arrays.asList(
                "get", "to", "executeScript", "executeAsyncScript", "window", "frame", "perform",
                "click", "clear", "sendKeys"
        );
    }

    public DriverEventListener(IVideoRecorder videoRecorder) {
        this.videoRecorder = videoRecorder;
        videoRecorder.start();
    }


    public void pause() {
        this.isPaused = true;
    }

    public void resume() {
        this.isPaused = false;
    }

    @Override
    public void afterDriverCommandExecuted(DriverCommandResult command) {
        if (command.getMethod().getName().equals("window") && !(command.getTarget().getClass().getName().contains("TargetLocator"))) {
            return;
        }
        if (command.getMethod().getName().matches("close|quit")) {
            videoRecorder.stop();
        } else {
            addFrameToVideo(command, command.getDriver());
        }
    }

    @Override
    public void afterElementCommandExecuted(ElementCommandResult command) {
        addFrameToVideo(command, command.getDriver());
    }

    private void addFrameToVideo(BaseCommand command, WebDriver driver) {
        if (!isPaused && allowedMethods.contains(command.getMethod().getName())) {
            videoRecorder.addImage(getScreenShot(driver));
        }
    }

    private byte[] getScreenShot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
