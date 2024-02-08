package demo.test.lucas.setup;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.junit.Rule;
import org.junit.rules.TestName;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static demo.test.lucas.factory.PlaywrightFactory.*;


public class Hooks {
    @Rule
    public TestName name = new TestName();

    @Before
    public void beforeScenario( Scenario scenario ) {
        Thread.currentThread().setName( scenario.getName() );
        initBrowser( randomBrowser(), false, 600 );
        getPage().navigate( "https://demoblaze.com/index.html" );
    }

    @After
    public void afterScenario( Scenario scenario ) throws IOException {
        browserContextClose();
        Path path = getPage().video().path();
        byte[] videoAttachment = Files.readAllBytes( path );
        scenario.attach( videoAttachment, "video/webm", "Attached Video - " + scenario.getName() + " with status: " + scenario.getStatus().name() );
        tearDown();
    }

    public String randomBrowser() {
        List< String > browserList = Arrays.asList( "chromium", "chrome", "firefox" );
        Random random = new Random();
        int randomItem = random.nextInt( browserList.size() );
        return browserList.get( randomItem );
    }

}