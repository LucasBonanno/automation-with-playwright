package demo.test.lucas.pages;


import com.microsoft.playwright.Locator;

import static demo.test.lucas.factory.PlaywrightFactory.getPage;

public class ProductPage extends CommonPage {
    private final Locator productImage = getPage().locator( "xpath=//*[@class='product-image']" );
    private final Locator name = getPage().locator( "xpath=//*[@class='name']" );
    private final Locator priceContainer = getPage().locator( "xpath=//*[@class='price-container']" );
    private final Locator btnSuccess = getPage().locator( "xpath=//*[contains(@class, 'btn-success')]" );

    public void validatePage() {
        assertHasUrl( ".*prod.*" );
        validateCommonsSectionsPage();
        productImage.isEnabled();
        name.isVisible();
        priceContainer.isVisible();
        btnSuccess.isEnabled();
    }

    public void clickAddToCart() {
        btnSuccess.click();
    }
}
