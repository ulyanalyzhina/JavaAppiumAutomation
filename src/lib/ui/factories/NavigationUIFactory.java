package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.NavigationUI;
import lib.ui.android.AndroidArticlePageObject;
import lib.ui.android.AndroidNavigationUIPageObject;
import lib.ui.ios.IOSArticlePageObject;
import lib.ui.ios.IOSNavigationUI;

public class NavigationUIFactory {
    public static NavigationUI get(AppiumDriver driver) {
        if (Platform.getInstance().isAndroid()) {
            return new AndroidNavigationUIPageObject(driver);
        } else {
            return new IOSNavigationUI(driver);
        }
    }
}
