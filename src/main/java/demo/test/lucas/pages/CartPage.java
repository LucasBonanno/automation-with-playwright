package demo.test.lucas.pages;

import com.microsoft.playwright.Locator;
import demo.test.lucas.dto.PlaceOrderDto;
import io.cucumber.datatable.DataTable;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static demo.test.lucas.factory.PlaywrightFactory.getPage;
import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class CartPage extends CommonPage {
    private PlaceOrderDto placeOrder;
    private String purchaseDataFromPage, totalPaidAmount;
    private final Locator tableTitle = getPage().locator( "xpath=//div[@id='page-wrapper']//h2[contains(text(),'Products')]" );
    private final Locator totalToPay = getPage().locator( "xpath=//*[@id='totalp']" );
    private final Locator btnPlaceOrder = getPage().locator( "xpath=//button[@data-target='#orderModal']" );
    private final Locator orderModal = getPage().locator( "xpath=//*[@id='orderModal']" );
    private final Locator inputName = getPage().locator( "xpath=//input[@id='name']" );
    private final Locator inputCountry = getPage().locator( "xpath=//input[@id='country']" );
    private final Locator inputCity = getPage().locator( "xpath=//input[@id='city']" );
    private final Locator inputCard = getPage().locator( "xpath=//input[@id='card']" );
    private final Locator inputMonth = getPage().locator( "xpath=//input[@id='month']" );
    private final Locator inputYear = getPage().locator( "xpath=//input[@id='year']" );
    private final Locator btnPurchaseOrder = getPage().locator( "xpath=//button[@onclick='purchaseOrder()']" );
    private final Locator purchaseConfirmationDataText = getPage().locator( "xpath=//*[@class='lead text-muted ']" );

    public void validatePage() {
        assertHasUrl( ".*cart.*" );
        validateCommonsSectionsPage();
        tableTitle.isVisible();
        tableTitle.isEnabled();
        btnPlaceOrder.isEnabled();
    }

    public void clickBtnPlaceOrder() {
        totalToPay.waitFor();
        totalPaidAmount = totalToPay.textContent();
        btnPlaceOrder.isEnabled();
        btnPlaceOrder.click();
    }

    public void fillPlaceOrderModal( DataTable dt ) {
        placeOrder = getPlaceOrderDto( dt );
        orderModal.isVisible();
        inputName.fill( placeOrder.getUserName() );
        inputCountry.fill( placeOrder.getCountry() );
        inputCity.fill( placeOrder.getCity() );
        inputCard.fill( placeOrder.getCardNumber() );
        inputMonth.fill( placeOrder.getMonth() );
        inputYear.fill( placeOrder.getYear() );
        btnPurchaseOrder.click();
    }

    private static PlaceOrderDto getPlaceOrderDto( DataTable dt ) {
        List< Map< String, String > > list = dt.asMaps( String.class, String.class );
        Map< String, String > item = list.stream().findFirst().orElseThrow( () -> new IllegalStateException( "Invalid Parameter" ) );

        String userName = requireNonNull( item.get( "USER_NAME" ), "USER_NAME is required" );
        String country = requireNonNull( item.get( "COUNTRY" ), "COUNTRY is required" );
        String city = requireNonNull( item.get( "CITY" ), "CITY is required" );
        String cardNumber = requireNonNull( item.get( "CARD_NUMBER" ), "CARD_NUMBER is required" );
        String month = requireNonNull( item.get( "MONTH" ), "MONTH is required" );
        String year = requireNonNull( item.get( "YEAR" ), "YEAR is required" );

        return new PlaceOrderDto( userName, country, city, cardNumber, month, year );
    }

    public void assertPurchaseConfirmationContainsOrderIdDisplayed() {
        purchaseDataFromPage = purchaseConfirmationDataText.textContent();
        String id = extractValue( purchaseDataFromPage, "Id: (\\d+)" );
        assertNotNull( id );
    }

    public void assertPurchaseConfirmationContainsPaidAmountEqualToExpected() {
        String amount = extractValue( purchaseDataFromPage, "Amount: (\\d+)" );
        assertEquals( totalPaidAmount, amount );
    }

    public void assertPurchaseConfirmationContainsDisplayedNameMatchingTheMockedInformation() {
        String cardNumber = extractValue( purchaseDataFromPage, "Card Number: (\\d+)" );
        String name = extractValue( purchaseDataFromPage, "Name: (.+?)Date:" );
        assertEquals( placeOrder.getCardNumber(), cardNumber );
        assertEquals( placeOrder.getUserName(), name );
    }

    private static String extractValue( String input, String pattern ) {
        Pattern regex = Pattern.compile( pattern );
        Matcher matcher = regex.matcher( input );

        if( matcher.find() ) {
            return matcher.group( 1 );
        } else {
            throw new IllegalArgumentException( "Pattern not found in input string" );
        }
    }

    public void handleAlert() {
        getPage().onDialog( dialog -> {
            assertEquals( "alert", dialog.type() );
            assertEquals( "Please fill out Name and Creditcard.", dialog.message() );
            dialog.accept();
        } );
        btnPurchaseOrder.click();
    }

}
