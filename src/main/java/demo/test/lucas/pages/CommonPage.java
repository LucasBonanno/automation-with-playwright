package demo.test.lucas.pages;

import com.microsoft.playwright.Locator;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static demo.test.lucas.factory.PlaywrightFactory.getPage;


public class CommonPage {
    private final Locator bmLogo = getPage().locator( "xpath=(//*[contains(@src,'bm.png')])[1]" );
    private final Locator mainMenu = getPage().locator( "xpath=//*[@id='navbarExample']" );
    private final Locator footerSection = getPage().locator( "xpath=//*[@id='footc']" );
    private final Locator copyright = getPage().getByText( "Copyright © Product Store 2017" );
    private final Locator cartMenu = getPage().locator( "xpath=//a[@class='nav-link'][contains(text(),'Cart')]" );

    protected void assertHasUrl( String regex ) {
        assertThat( getPage() ).hasURL( Pattern.compile( regex ) );
    }

    protected void validateCommonsSectionsPage() {
        bmLogo.isEnabled();
        mainMenu.isEnabled();
        footerSection.isEnabled();
        copyright.isEnabled();
    }

    public void selectCartMenu() {
        cartMenu.click();
    }
}
