package org.jboss.examples.deltaspike.tickets.test;

import static org.jboss.arquillian.graphene.Graphene.guardHttp;
import static org.jboss.arquillian.graphene.Graphene.waitAjax;
import static org.jboss.arquillian.graphene.Graphene.waitModel;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import org.openqa.selenium.WebElement;

public class DsTicketsTest extends DsTicketsAbstractTest {

    private static List<String> accessSeats = new ArrayList<String>();
    private static int originalSeatsCount;
    private static Map<Long, Order> orders = new HashMap<Long, Order>();

    /**
     * Tests if the annotations @ViewControllerRef and @InitView work and the callback has been invoked
     */
    @Test
    @InSequence(1)
    public void dbInitialized() {
        openBrowser();
        assertTrue("The databaze has not been initialized", INITIALIZE_DB_BUTTON.size() == 0);
    }

    @Test
    @InSequence(2)
    public void selectBuLine() {

        selectFromTo(DEPARTURE_PRAGUE, ARRIVAL_PARIS);
        // Go to dates page
        clickOn(NEXT_BUTTON);

        // Check if the right bus line is used and go back
        waitModel().until().element(FROM_TO_LABEL).is().visible();
        assertEquals("From: Prague to: Paris", FROM_TO_LABEL.getText());
        guardHttp(BACK_BUTTON).click();

        // Check if the state persists
        assertTrue("The Prague should be selected as the departure city", DEPARTURE_PRAGUE.isSelected());
        assertTrue("The Paris should be selected as the arrival city", ARRIVAL_PARIS.isSelected());

        // Change the direction
        selectFromTo(DEPARTURE_PARIS, ARRIVAL_PRAGUE);

        // Go to dates selection and check if it has been changed
        clickOn(NEXT_BUTTON);
        assertEquals("From: Paris to: Prague", FROM_TO_LABEL.getText());

    }

    @Test
    @InSequence(3)
    public void selectDate() {

        // Select date 2015-06-12
        assertTrue("The date 2015-06-12 should be displayed", DATE_2015_06_12.isDisplayed());
        guardHttp(DATE_2015_06_12).click();
        waitModel().until().element(ON_DATE_LABEL).is().visible();
        assertEquals("On: 2015-06-12 08:00", ON_DATE_LABEL.getText());
        assertEquals("From: Paris to: Prague", FROM_TO_LABEL.getText());

        // Go back on the first page and switch the direction again
        openBrowser();
        selectFromTo(DEPARTURE_PRAGUE, ARRIVAL_PARIS);

        // Go back to seats
        browser.get("http://127.0.0.1:8080/ds-tickets/pages/access/seat.jsf");
        waitModel().until().element(ON_DATE_LABEL).is().visible();
        assertEquals("On:", ON_DATE_LABEL.getText());
        assertEquals("From: Prague to: Paris", FROM_TO_LABEL.getText());

        // Select date and check
        guardHttp(BACK_BUTTON).click();
        waitAjax().until().element(DATE_2015_07_26).is().visible();
        guardHttp(DATE_2015_07_26).click();
        waitModel().until().element(ON_DATE_LABEL).is().visible();
        assertEquals("On: 2015-07-26 08:00", ON_DATE_LABEL.getText());
        assertEquals("From: Prague to: Paris", FROM_TO_LABEL.getText());
    }

    @Test
    @InSequence(4)
    public void selectSeats() throws InterruptedException {
        assertFalse("The free offered seats should not be empty", SEATS_OPTIONS.isEmpty());

        originalSeatsCount = SEATS_OPTIONS.size();
        // Select the seats: 1D, 3A, 4A, 5D, 11D
        for (int i = 1; i < SEATS_OPTIONS.size(); i += i) {
            SEATS_OPTIONS.get(i).click();
            accessSeats.add(SEATS_OPTIONS.get(i).getText());
        }
        clickOn(SUBMIT_BUTTON);
    }

    @Test
    @InSequence(5)
    public void orderTickets() {

        Order order = new Order("Prague", "Paris", "2015-07-26 08:00", accessSeats);

        double toPay = checkOverview(order, TICKET_ROWS, PRICE_TO_PAY_LABEL);
        orders.put(orderAndCheck(toPay), order);
    }

    @Test
    @InSequence(11)
    public void switchToSinglePageMode() {
        openBrowser();
        waitModel().until().element(SINGLE_PAGE_MODE_LINK).is().visible();
        clickOn(SINGLE_PAGE_MODE_LINK);
    }

    @Test
    @InSequence(12)
    public void selectBusDateSeat() {

        selectFromTo(DEPARTURE_PRAGUE, ARRIVAL_PARIS);
        clickOn(DATE_2015_07_26);
        waitModel().until().element(SEATS_SELECTION).is().visible();

        checkTheRestAccessSeats();
        selectSeat(2);
    }

    @Test
    @InSequence(13)
    public void goAwayAndReturnBack() {
        // Go away
        openBrowser();

        // Do some stuff
        selectBuLine();
        selectDate();

        // return back
        switchToSinglePageMode();

        // Check if the state persists
        checkSinglePageState(DEPARTURE_PRAGUE, ARRIVAL_PARIS, DATE_2015_07_26, 2);
        checkTheRestAccessSeats();
    }

    @Test
    @InSequence(14)
    public void groupsReseting() {
        // Reset the seats part
        clickOn(RESET_SEATS_BUTTON);
        checkSinglePageState(DEPARTURE_PRAGUE, ARRIVAL_PARIS, DATE_2015_07_26, 0);
        checkTheRestAccessSeats();

        // Select every second seat and submit
        List<String> selectedSeats = selectSeat(2);
        clickOn(SUBMIT_BUTTON);
        checkOverview(new Order("Prague", "Paris", "2015-07-26 08:00", selectedSeats), TICKET_ROWS, PRICE_TO_PAY_LABEL);

        // Reset the seats and overview parts
        clickOn(RESET_SEATS_BUTTON);
        for (int i = 1; i < SEATS_OPTIONS.size(); i += 2) {
            assertFalse("the seat " + SEATS_OPTIONS.get(i) + " should NOT be selected", SEATS_OPTIONS.get(i).isSelected());
        }
        // the overview should not be visible, so this wait should pass
        waitModel().until().element(PRICE_TO_PAY_LABEL).is().not().present();

        // Select every third seat and submit, refresh and check
        selectedSeats = selectSeat(3);
        clickOn(SUBMIT_BUTTON);
        switchToSinglePageMode();
        checkSinglePageState(DEPARTURE_PRAGUE, ARRIVAL_PARIS, DATE_2015_07_26, 3);
        checkTheRestAccessSeats();
        checkOverview(new Order("Prague", "Paris", "2015-07-26 08:00", selectedSeats), TICKET_ROWS, PRICE_TO_PAY_LABEL);

        // Reset the dates, seats, overview parts
        clickOn(RESET_DATE_BUTTON);
        // both the seats selection and overview should not be visible, so this waits should pass
        checkSinglePageState(DEPARTURE_PRAGUE, ARRIVAL_PARIS, null, -1);
        waitModel().until().element(PRICE_TO_PAY_LABEL).is().not().present();

        // Select date and every fourth seat
        clickOn(DATE_2015_07_20);
        waitModel().until().element(SEATS_SELECTION).is().visible();
        selectSeat(4);
        clickOn(SUBMIT_BUTTON);
        checkSinglePageState(DEPARTURE_PRAGUE, ARRIVAL_PARIS, DATE_2015_07_20, 4);

        clickOn(RESET_BUS_LINE_BUTTON);
        checkSinglePageState(null, null, null, -1);
    }

    @Test
    @InSequence(15)
    public void makeOrder() {

        selectFromTo(DEPARTURE_PARIS, ARRIVAL_PRAGUE);
        clickOn(DATE_2015_06_12);

        waitModel().until().element(SEATS_SELECTION).is().visible();
        switchToSinglePageMode();
        List<String> selectedSeat = selectSeat(3);
        clickOn(SUBMIT_BUTTON);

        checkSinglePageState(DEPARTURE_PARIS, ARRIVAL_PRAGUE, DATE_2015_06_12, 3);
        Order order = new Order("Paris", "Prague", "2015-06-12 08:00", selectedSeat);
        double toPay = checkOverview(order, TICKET_ROWS,
            PRICE_TO_PAY_LABEL);

        Long orderId = orderAndCheck(toPay);
        orders.put(orderId, order);

        switchToSinglePageMode();
        checkSinglePageState(null, null, null, -1);
    }

    @Test
    @InSequence(16)
    public void showAllOrders() {
        openBrowser();
        clickOn(ORDERS_BUTTON);

        assertEquals("expected number of orders", orders.size(), ORDER_PARTS.size());

        waitModel().until().element(ORDERS_TABLE).is().visible();
        for (WebElement orderPart : ORDER_PARTS) {
            String orderId = orderPart.findElement(ByJQuery.selector("h3 i")).getText();
            List<WebElement> orderRows = orderPart.findElements(ByJQuery.selector(".order-table > tbody > tr"));
            WebElement toPayLabel = orderPart.findElement(ByJQuery.selector("#toPayLabel label"));
            checkOverview(orders.get(Long.valueOf(orderId)), orderRows, toPayLabel);
        }
    }

    private void checkTheRestAccessSeats() {
        assertEquals("count of the rest of the 'access' seats", originalSeatsCount - accessSeats.size(), SEATS_OPTIONS.size());
        for (int i = 1; i < SEATS_OPTIONS.size(); i++) {
            for (String accessSeat : accessSeats) {
                assertNotEquals("seat should not be offered", accessSeat, SEATS_OPTIONS.get(i).getText());
            }
        }
    }
}
