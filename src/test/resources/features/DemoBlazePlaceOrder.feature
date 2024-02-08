@DemoBlaze
Feature: Demoblaze - Place an order

  User History:

  As a Demoblaze user
  I want to be able to place an order
  To be able to purchase any product available in the application

  Background:
    Given that the user has access to the demoblaze index page

  @PlaceOrder
  Scenario Outline: Place an order using <CARD> credit card
    And select to the laptops section
    And add "<PRODUCT>" to the cart
    And go to cart page
    And click place order button
    When filling out place order form with bellow mocked information:
      | USER_NAME   | COUNTRY   | CITY   | CARD_NUMBER   | MONTH   | YEAR   |
      | <USER_NAME> | <COUNTRY> | <CITY> | <CARD_NUMBER> | <MONTH> | <YEAR> |
    Then the purchase confirmation should contain the Order ID displayed
    And the purchase confirmation should contain the paid amount equal to the expected value
    And the purchase confirmation should contain the displayed name matching the mocked information


    Examples:
      | CARD | PRODUCT     | USER_NAME     | COUNTRY       | CITY         | CARD_NUMBER      | MONTH | YEAR |
      | AMEX | MacBook Pro | John Doe      | Portugal      | Lisbon       | 375567668884617  | 02    | 2030 |
      | VISA | MacBook Pro | Percy Clayton | United States | Jacksonville | 4411732769254916 | 4     | 2029 |

  @PlaceOrderWithoutCard
  Scenario: Try to place an order without a credit card
    And select to the laptops section
    And add "MacBook Pro" to the cart
    And go to cart page
    When click place order button
    Then check if the credit card information is needed to complete the order
