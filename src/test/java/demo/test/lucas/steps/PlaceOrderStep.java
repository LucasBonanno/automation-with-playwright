package demo.test.lucas.steps;

import demo.test.lucas.pages.CartPage;
import demo.test.lucas.pages.IndexPage;
import demo.test.lucas.pages.ProductPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PlaceOrderStep {

    private final IndexPage indexPage = new IndexPage();
    private final ProductPage productPage = new ProductPage();
    private final CartPage cartPage = new CartPage();

    @Given( "that the user has access to the demoblaze index page" )
    public void thatTheUserHasAccessToTheDemoblazeIndexPage() {
        indexPage.validatePage();
    }

    @Given( "select to the laptops section" )
    public void navigateToTheLaptopsSection() {
        indexPage.clickLaptopsSection();
    }

    @Given( "add {string} to the cart" )
    public void addToTheCart( String productName ) {
        indexPage.selectProduct( productName );
        productPage.validatePage();
    }

    @Given( "go to cart page" )
    public void goToCartPage() {
        productPage.clickAddToCart();
        cartPage.selectCartMenu();
        cartPage.validatePage();
    }

    @Given( "click place order button" )
    public void clickPlaceOrderButton( ) {
        cartPage.clickBtnPlaceOrder();
    }

    @When( "filling out place order form with bellow mocked information:" )
    public void fillingOutPlaceOrderFormWithBellowMockedInformation( final DataTable dt ) {
        cartPage.fillPlaceOrderModal(dt);
    }

    @Then( "the purchase confirmation should contain the Order ID displayed" )
    public void thePurchaseConfirmationShouldContainTheOrderIdDisplayed() {
        cartPage.assertPurchaseConfirmationContainsOrderIdDisplayed();
    }

    @Then( "the purchase confirmation should contain the paid amount equal to the expected value" )
    public void thePurchaseConfirmationShouldContainThePaidAmountEqualToTheExpectedValue() {
        cartPage.assertPurchaseConfirmationContainsPaidAmountEqualToExpected();
    }

    @Then( "the purchase confirmation should contain the displayed name matching the mocked information" )
    public void thePurchaseConfirmationShouldContainTheDisplayedNameMatchingTheMockedInformation() {
        cartPage.assertPurchaseConfirmationContainsDisplayedNameMatchingTheMockedInformation();
    }

    @Then( "check if the credit card information is needed to complete the order" )
    public void checkIfTheCreditCardInformationIsNeededToCompleteTheOrder() {
        cartPage.handleAlert();
    }
}
