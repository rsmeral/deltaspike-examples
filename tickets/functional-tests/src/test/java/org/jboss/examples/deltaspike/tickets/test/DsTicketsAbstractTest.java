package org.jboss.examples.deltaspike.tickets.test;

import static org.jboss.arquillian.graphene.Graphene.guardHttp;
import static org.jboss.arquillian.graphene.Graphene.waitAjax;
import static org.jboss.arquillian.graphene.Graphene.waitModel;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@RunAsClient
@RunWith(Arquillian.class)
public abstract class DsTicketsAbstractTest {

    @FindByJQuery(".title:contains('Multiple-page mode')")
    protected WebElement MULTIPLE_PAGE_MODE_TITLE;

    @FindByJQuery("input[value = 'initialize DB']")
    protected List<WebElement> INITIALIZE_DB_BUTTON;

    @FindByJQuery("#departureSelection option[value = '']")
    protected WebElement DEPARTURE_EMPTY;

    @FindByJQuery("#departureSelection option[value = 'Prague']")
    protected WebElement DEPARTURE_PRAGUE;

    @FindByJQuery("#departureSelection option[value = 'Paris']")
    protected WebElement DEPARTURE_PARIS;

    @FindByJQuery("#arrivalSelection select")
    protected WebElement ARRIVAL_SELECTION;

    @FindByJQuery("#arrivalSelection option[value = '']")
    protected WebElement ARRIVAL_EMPTY;

    @FindByJQuery("#arrivalSelection option[value = 'Paris']")
    protected WebElement ARRIVAL_PARIS;

    @FindByJQuery("#arrivalSelection option[value = 'Prague']")
    protected WebElement ARRIVAL_PRAGUE;

    @FindByJQuery("input[value = 'Next']")
    protected WebElement NEXT_BUTTON;

    @FindByJQuery("input[value = 'Back']")
    protected WebElement BACK_BUTTON;

    @FindByJQuery("input[value = 'Submit']")
    protected WebElement SUBMIT_BUTTON;

    @FindByJQuery("input[value = 'Order']")
    protected WebElement ORDER_BUTTON;

    @FindByJQuery("input[value = 'Show All Orders']")
    protected WebElement ORDERS_BUTTON;

    @FindBy(id = "fromToLabel")
    protected WebElement FROM_TO_LABEL;

    @FindBy(id = "onDateLabel")
    protected WebElement ON_DATE_LABEL;

    @FindByJQuery("a:contains('2015-06-12 08:00')")
    protected WebElement DATE_2015_06_12;

    @FindByJQuery("a:contains('2015-07-20 08:00')")
    protected WebElement DATE_2015_07_20;

    @FindByJQuery("a:contains('2015-07-26 08:00')")
    protected WebElement DATE_2015_07_26;

    @FindBy(className = "selectedDate")
    protected List<WebElement> SELECTED_DATES;

    @FindByJQuery("select[name='selectSeatsForm:selectSeats']")
    protected WebElement SEATS_SELECTION;

    @FindByJQuery("select[name='selectSeatsForm:selectSeats'] option")
    protected List<WebElement> SEATS_OPTIONS;

    @FindByJQuery(".order-table tbody tr")
    protected List<WebElement> TICKET_ROWS;

    @FindBy(id = "priceLabel")
    protected WebElement PRICE_TO_PAY_LABEL;

    @FindBy(id = "orderIdLabel")
    protected List<WebElement> ORDER_ID_LABEL;

    @FindByJQuery("#busLinePart input[value='Reset']")
    protected WebElement RESET_BUS_LINE_BUTTON;

    @FindByJQuery("#datePart input[value='Reset']")
    protected WebElement RESET_DATE_BUTTON;

    @FindByJQuery("#seatsPart input[value='Reset']")
    protected WebElement RESET_SEATS_BUTTON;

    @FindByJQuery("a:contains('single-page mode')")
    protected WebElement SINGLE_PAGE_MODE_LINK;

    @FindBy(id = "orders")
    protected WebElement ORDERS_TABLE;

    @FindByJQuery("#orders > tbody > tr > td")
    protected List<WebElement> ORDER_PARTS;

    @Drone
    protected WebDriver browser;

    @ArquillianResource
    protected URL contextPath;

    private static final String TEST_DEPLOYMENT_PROPERTY = "testDeployment";
    private static final String TEST_DEPLOYMENT = System.getProperty(TEST_DEPLOYMENT_PROPERTY);

    @Deployment(testable = false)
    public static WebArchive deployment() {
        return ShrinkWrap.createFromZipFile(WebArchive.class, new File(TEST_DEPLOYMENT));
    }

    protected void openBrowser() {
        browser.get(contextPath.toString());
        waitModel().until().element(MULTIPLE_PAGE_MODE_TITLE).is().visible();
    }

    protected void selectFromTo(WebElement from, WebElement to) {
        waitModel().until().element(from).is().enabled();
        guardHttp(from).click();
        waitModel().until().element(ARRIVAL_SELECTION).is().enabled();
        guardHttp(to).click();
    }

    protected void clickOn(WebElement element) {
        waitModel().until().element(element).is().visible();
        waitAjax().until().element(element).is().enabled();
        guardHttp(element).click();
    }

    protected double checkOverview(Order order, List<WebElement> ticketRows, WebElement priceToPayLabel) {
        List<String> seats = order.getSeats();
        assertEquals("number of tickets", seats.size(), ticketRows.size());
        double toPay = 0;

        for (int i = 0; i < seats.size(); i++) {
            WebElement row = ticketRows.get(i);
            List<WebElement> columns = row.findElements(By.tagName("td"));

            assertEquals("ticket number", String.valueOf(i + 1), columns.get(0).getText());
            assertEquals("departure", order.getDeparture(), columns.get(1).getText());
            assertEquals("arrival", order.getArrival(), columns.get(2).getText());
            assertEquals("date", order.getDate(), columns.get(3).getText());
            assertEquals("seat number", seats.get(i), columns.get(4).getText());

            boolean fc = Integer.valueOf(seats.get(i).substring(0, seats.get(i).length() - 1)) < 4;
            assertEquals("first class", fc ? "yes" : "no", columns.get(5).getText());

            toPay += fc ? 1350 : 900;
            assertEquals("price", fc ? "1350.0" : "900.0", columns.get(6).getText());
        }

        assertEquals("Price to pay", String.valueOf(toPay), priceToPayLabel.getText());

        return toPay;
    }

    protected List<String> selectSeat(int frequency) {
        waitModel().until().element(SEATS_SELECTION).is().visible();
        assertFalse("The free offered seats should not be empty", SEATS_OPTIONS.isEmpty());

        List<String> selectedSeats = new ArrayList<String>();
        for (int i = 0; i < SEATS_OPTIONS.size(); i += frequency) {
            SEATS_OPTIONS.get(i).click();
            selectedSeats.add(SEATS_OPTIONS.get(i).getText());
        }

        return selectedSeats;
    }

    protected void checkSinglePageState(WebElement departureEl, WebElement arrivalEl, WebElement dateEl, int seatFrequency) {
        if (departureEl != null) {
            assertTrue("The Prague should be selected as the departure city", departureEl.isSelected());
        } else {
            assertTrue("The empty value be selected as the arrival city", DEPARTURE_EMPTY.isSelected());
        }

        if (arrivalEl != null) {
            assertTrue("The Paris should be selected as the arrival city", arrivalEl.isSelected());
        } else {
            assertTrue("The empty value be selected as the arrival city", ARRIVAL_EMPTY.isSelected());
        }

        if (dateEl != null) {
            assertEquals("number of selected dates", 1, SELECTED_DATES.size());
            assertEquals("Style of the selected date link", "selectedDate",
                dateEl.getAttribute("class"));
        } else {
            assertEquals("number of selected dates", 0, SELECTED_DATES.size());
        }

        if (seatFrequency < 0) {
            waitModel().until().element(SEATS_SELECTION).is().not().present();
        } else {
            waitModel().until().element(SEATS_SELECTION).is().visible();
            if (seatFrequency > 0) {
                checkSeatSelected(seatFrequency);
            }
        }
    }

    protected void checkSeatSelected(int frequency) {
        waitModel().until().element(SEATS_SELECTION).is().visible();
        for (int i = 0; i < SEATS_OPTIONS.size(); i += frequency) {
            assertTrue("the seat " + SEATS_OPTIONS.get(i).getText() + " should be selected", SEATS_OPTIONS.get(i).isSelected());
        }
    }

    protected Long orderAndCheck(double toPay) {
        clickOn(ORDER_BUTTON);

        waitModel().until().element(PRICE_TO_PAY_LABEL).is().visible();
        assertEquals("the price of the order to pay", String.valueOf(toPay), PRICE_TO_PAY_LABEL.getText());

        return Long.valueOf(ORDER_ID_LABEL.get(0).getText());
    }
}
