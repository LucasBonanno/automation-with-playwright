package demo.test.lucas.factory;

import com.microsoft.playwright.*;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PlaywrightFactory {
    private static final Path recordVideoDirPath = Paths.get( "target/videos-report/" );
    private static final ThreadLocal< Playwright > tlPlaywright = new ThreadLocal<>();
    private static final ThreadLocal< Browser > tlBrowser = new ThreadLocal<>();
    private static final ThreadLocal< BrowserContext > tlBrowserContext = new ThreadLocal<>();
    private static final ThreadLocal< Page > tlPage = new ThreadLocal<>();

    public static Playwright getPlaywright() {
        return tlPlaywright.get();
    }

    public static Browser getBrowser() {
        return tlBrowser.get();
    }

    public static BrowserContext getBrowserContext() {
        return tlBrowserContext.get();
    }

    public static Page getPage() {
        return tlPage.get();
    }

    public static synchronized void initBrowser( String browserName, Boolean isHeadless, int slowMotion ) {
        tlPlaywright.set( Playwright.create() );
        setBrowserConfig( browserName, isHeadless, slowMotion );
        tlBrowserContext();
        tlPage.set( getBrowserContext().newPage() );
    }

    private static void setBrowserConfig( String browserName, Boolean isHeadless, int slowMotion ) {
        switch( browserName.toLowerCase() ) {
            case "chromium" ->
                    tlBrowser.set( getPlaywright().chromium().launch( new BrowserType.LaunchOptions().setHeadless( isHeadless ).setSlowMo( slowMotion ) ) );
            case "chrome" ->
                    tlBrowser.set( getPlaywright().chromium().launch( new BrowserType.LaunchOptions().setChannel( browserName.toLowerCase() ).setHeadless( isHeadless ).setSlowMo( slowMotion ) ) );
            case "firefox" ->
                    tlBrowser.set( getPlaywright().firefox().launch( new BrowserType.LaunchOptions().setHeadless( isHeadless ).setSlowMo( slowMotion ) ) );
            default -> throw new IllegalArgumentException( "Please provide valid browser name" );
        }
    }

    private static void tlBrowserContext() {
        tlBrowserContext.set( getBrowser().newContext( new Browser.NewContextOptions()
                .setRecordVideoDir( recordVideoDirPath )
        ) );
    }

    public static synchronized void browserContextClose() {
        getBrowserContext().close();
    }

    public static synchronized void tearDown() {
        getBrowser().close();
        getPlaywright().close();
    }
}
