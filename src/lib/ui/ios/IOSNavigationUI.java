package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationUI;

public class IOSNavigationUI extends NavigationUI {
    static {
        MY_LISTS_LINK = "id:Saved";
        CLOSE_POP_UP_BUTTON = "xpath://XCUIElementTypeButton[@name='Close']";
    }
    public IOSNavigationUI(AppiumDriver driver) {
        super(driver);
    }
}
