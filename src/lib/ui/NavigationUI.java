package lib.ui;

import io.appium.java_client.AppiumDriver;

abstract public class NavigationUI extends MainPageObject {
    protected static String
            MY_LISTS_LINK,
            CLOSE_POP_UP_BUTTON;

    public NavigationUI(AppiumDriver driver) {
        super(driver);
    }

    public void clickMyList() {
        this.waitForElementByAndClick(
                MY_LISTS_LINK,
                "Cannot find navigation button to my lists",
                5
        );
        if(Platform.getInstance().isIOS()) {
            this.waitForElementByAndDoubleClick(
                    CLOSE_POP_UP_BUTTON,
                    "Cannot find 'X' button to close pop-up",
                    15
            );
        }
    }
}
