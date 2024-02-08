package demo.test.lucas.pages;

import com.microsoft.playwright.Locator;

import static demo.test.lucas.factory.PlaywrightFactory.getPage;


public class IndexPage extends CommonPage {

    private final Locator sideBarMenu = getPage().locator( "xpath=//*[@id='cat']" );
    private final Locator carousel = getPage().locator( "xpath=//*[@id='carouselExampleIndicators']" );
    private final Locator productTable = getPage().locator( "xpath=//*[@id='tbodyid']" );
    private final Locator notebook = getPage().locator( "xpath=//a[@onclick=\"byCat('notebook')\"]" );

    private Locator product( String productName ) {
        return getPage().locator( "xpath=//*[@class='card-title']//a[contains(text(),'" + productName + "')]" );
    }

    public void validatePage() {
        assertHasUrl( ".*index.*" );
        validateCommonsSectionsPage();
        sideBarMenu.isEnabled();
        carousel.isEnabled();
        productTable.isEnabled();
    }

    public void clickLaptopsSection() {
        notebook.isEnabled();
        notebook.click();
    }

    public void selectProduct( String productName ) {
        product( productName ).isEnabled();
        product( productName ).click();
    }
}
