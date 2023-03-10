package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class Platform {
    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";
    private static String APPIUM_URL = "http://127.0.0.1:4723/wd/hub";

    private static Platform instance;

    private Platform() {};

    public static Platform getInstance()
    {
        if(instance==null) {
            instance = new Platform();
        }
        return instance;
    }

    public AppiumDriver getDriver() throws Exception {
        URL URL = new URL(APPIUM_URL);
        if (this.isAndroid()) {
            return new AppiumDriver(URL, this.getAndroidDesiredCapabilities());
        } else if (this.isIOS()) {
            return new IOSDriver(URL, this.getIOSDesiredCapabilities());
        } else {
            throw new Exception("Cannot detect type of the driver. Platform value: " + this.getPlatformVar());
        }
    }

    public boolean isAndroid() {
        return isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOS() {
        return isPlatform(PLATFORM_IOS);
    }

    private DesiredCapabilities getAndroidDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platform", "android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "11.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability(
                "app",
                "/Users/ulyana/IdeaProjects/JavaAppiumAutomation/JavaAppiumAutomation/apks/org.wikipedia.apk");
        return capabilities;
    }

    private DesiredCapabilities getIOSDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platform", "iOS");
        capabilities.setCapability("deviceName", "iPhone 13");
        capabilities.setCapability("platformVersion", "15.5");
        capabilities.setCapability("waitForIdleTimeout", 0);
        capabilities.setCapability(
                "app",
                "/Users/ulyana/IdeaProjects/JavaAppiumAutomation/JavaAppiumAutomation/src/apks/Wikipedia77.app");
        return capabilities;
    }

    private boolean isPlatform(String myPlatform) {
        String platform = this.getPlatformVar();
        return myPlatform.equals(platform);
    }

    private String getPlatformVar() {
        return System.getenv("PLATFORM");
    }
}
