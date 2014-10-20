package org.jboss.examples.deltaspike.task.management.test;

import static org.jboss.arquillian.graphene.Graphene.guardHttp;
import static org.jboss.arquillian.graphene.Graphene.waitAjax;
import static org.jboss.arquillian.graphene.Graphene.waitModel;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By.ByTagName;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@RunAsClient
@RunWith(Arquillian.class)
public class DsTaskManagementTest {

    @FindBy(id = "buttonbar")
    protected WebElement BUTTON_BAR;

    @FindByJQuery("#pagetitle:contains('DeltaSpike Task Management System - Login')")
    protected WebElement LOGIN_PAGE_TITLE;

    @FindByJQuery("#pagetitle:contains('DeltaSpike Task Management System - Home')")
    protected WebElement HOME_PAGE_TITLE;

    @FindByJQuery("#pagetitle:contains('DeltaSpike Task Management System - My Tasks')")
    protected WebElement MY_TASKS_PAGE_TITLE;

    @FindByJQuery("#pagetitle:contains('DeltaSpike Task Management System - Create Task')")
    protected WebElement CREATE_TASK_PAGE_TITLE;

    @FindByJQuery("#pagetitle:contains('DeltaSpike Task Management System - Web Log')")
    protected WebElement WEB_LOG_PAGE_TITLE;

    @FindByJQuery("#pagetitle:contains('DeltaSpike Task Management System - Access Denied')")
    protected WebElement ACCESS_DENIED_PAGE_TITLE;

    @FindByJQuery("input[value='Home']")
    protected WebElement HOME_BUTTON;

    @FindByJQuery("input[value='Show My Tasks']")
    protected WebElement MY_TASKS_BUTTON;

    @FindByJQuery("input[value='Create New Task']")
    protected WebElement NEW_TASK_BUTTON;

    @FindByJQuery("input[value='Show Web Log']")
    protected WebElement WEB_LOG_BUTTON;

    @FindByJQuery("input[value='Login']")
    protected WebElement LOGIN_BUTTON;

    @FindByJQuery("input[value='Logout']")
    protected WebElement LOGOUT_BUTTON;

    @FindBy(id = "errorMessages")
    protected WebElement ERROR_MESSAGES;

    @FindByJQuery("#loginRowName > input")
    protected WebElement NAME_INPUT_FIELD;

    @FindByJQuery("#loginRowPassword > input")
    protected WebElement PASSWORD_INPUT_FIELD;

    @FindByJQuery("#loginForm input[value='Login']")
    protected WebElement LOGIN_FORM_BUTTON;

    @FindBy(id = "currentUser")
    protected WebElement CURRENT_USER_LABEL;

    @FindByJQuery("#newTaskForm > div:contains('Task name:') > input")
    protected WebElement TASK_NAME_FIELD;

    @FindByJQuery("#newTaskForm > div:contains('User:') > select > option")
    protected List<WebElement> TASK_USERS_OPTIONS;

    @FindByJQuery("#newTaskForm > div:contains('Description:') > textarea")
    protected WebElement TASK_DESCRIPTION_FIELD;

    @FindByJQuery("#newTaskForm > div:contains('Deadline:') > input")
    protected WebElement TASK_DEADLINE_FIELD;

    @FindByJQuery("input[value='Submit']")
    protected WebElement SUBMIT_BUTTON;

    // @FindBy(id = "taskName")
    // protected WebElement TASK_NAME;
    //
    // @FindBy(id = "taskUser")
    // protected WebElement TASK_USER;
    //
    // @FindBy(id = "taskDescription")
    // protected WebElement TASK_DESCRIPTION;
    //
    // @FindBy(id = "taskDeadline")
    // protected WebElement TASK_DEADLINE;

    @FindByJQuery("#myTaskTable > tbody > tr")
    protected List<WebElement> MY_TASK_ROWS;

    @Drone
    protected WebDriver browser;

    private String[] user = new String[] { "user", "user123", "User Userovic" };
    private String[] admin = new String[] { "admin", "admin123", "Admin Adminovic" };

    @ArquillianResource
    protected URL contextPath;

    private static final String WAR_FILE = "../target/ds-task-management.war";
    private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64; rv:29.0) Gecko/20100101 Firefox/29.0";

    @Deployment(testable = false)
    public static WebArchive deployment() {
        return ShrinkWrap.createFromZipFile(WebArchive.class, new File(WAR_FILE));
    }

    protected void openBrowser() {
        browser.get(contextPath.toString());
        waitModel().until().element(BUTTON_BAR).is().visible();
    }

    /**
     * Tests the access decision voters and annotation @Secured
     */
    @Test
    @InSequence(1)
    public void testNotLoggedInAccess() {
        openBrowser();

        // have access
        clickOn(HOME_BUTTON);
        waitModel().until().element(HOME_PAGE_TITLE).is().visible();

        // doesn't hame access
        clickOn(MY_TASKS_BUTTON);
        userNotLoggedinWarning();

        // doesn't hame access
        clickOn(NEW_TASK_BUTTON);
        userNotLoggedinWarning();

        // doesn't hame access
        clickOn(WEB_LOG_BUTTON);
        userNotLoggedinWarning();
    }

    /**
     * Tests the access decision voters and annotation @Secured
     */
    @Test
    @InSequence(2)
    public void testUserAccess() {
        openBrowser();
        login(user);

        // have access
        clickOn(HOME_BUTTON);
        waitModel().until().element(HOME_PAGE_TITLE).is().visible();

        // have access
        clickOn(MY_TASKS_BUTTON);
        waitModel().until().element(MY_TASKS_PAGE_TITLE).is().visible();

        // doesn't hame access
        clickOn(NEW_TASK_BUTTON);
        accessDeniedWarning();

        // doesn't hame access
        clickOn(WEB_LOG_BUTTON);
        accessDeniedWarning();

        // logout and check the access
        logout();
        testNotLoggedInAccess();
    }

    /**
     * Tests the access decision voters and annotation @Secured
     */
    @Test
    @InSequence(3)
    public void testAdminAccess() {
        openBrowser();
        login(admin);

        // have access
        clickOn(HOME_BUTTON);
        waitModel().until().element(HOME_PAGE_TITLE).is().visible();

        // have access
        clickOn(MY_TASKS_BUTTON);
        waitModel().until().element(MY_TASKS_PAGE_TITLE).is().visible();

        // have access
        clickOn(NEW_TASK_BUTTON);
        waitModel().until().element(CREATE_TASK_PAGE_TITLE).is().visible();

        // have access
        clickOn(WEB_LOG_BUTTON);
        waitModel().until().element(WEB_LOG_PAGE_TITLE).is().visible();

        // logout and check the access
        logout();
        testNotLoggedInAccess();
    }

    /**
     * Tests the access control per view (the functionality of the scope @WindowScoped)
     */
    @Test
    @InSequence(4)
    public void multipleWindowTest() throws Exception {
        openBrowser();
        login(admin);
        String url = browser.getCurrentUrl();
        openBrowser();
        checkLoggedUser(admin[2]);

        Cookie sessionidCookie = browser.manage().getCookieNamed("JSESSIONID");
        String sessionId = sessionidCookie.toString();
        // browser.manage().deleteAllCookies();
        // browser.get(url.substring(0, url.length() - 1));
        // // System.err.println(browser.getPageSource());
        //
        // browser.manage().addCookie(sessionidCookie);
        // browser.get(url.substring(0, url.length() - 1));
        // // System.err.println(browser.getPageSource());

        String body = sendRequest(url, sessionId, null);
        assertTrue("admin should be logged in", body.contains("User: Admin Adminovic"));

        body = sendRequest(url.substring(0, url.length() - 1), sessionId, null);
        assertFalse("admin should NOT be logged in", body.contains("User: Admin Adminovic"));

        // body = sendRequest("http://127.0.0.1:8080/ds-task-management/pages/login.jsf?dswid=8086", sessionId, null);
        // String vs = "id=\"javax.faces.ViewState\" value=\"";
        // String pom = body.substring(body.indexOf(vs) + vs.length());
        // String viewState = pom.substring(0, pom.indexOf("\""));
        // System.err.println(viewState);
        //
        // body = sendRequest(
        // "http://127.0.0.1:8080/ds-task-management/pages/login.jsf?dswid=8086",
        // sessionId,
        // "loginForm=loginForm&loginForm%3Aname=user&loginForm%3Apassword=user1234&loginForm%3Alogin=Login&javax.faces.ViewState="
        // + viewState + "&dspwid=8086");
        // body = sendRequest("http://127.0.0.1:8080/ds-task-management/pages/home.jsf?dswid=8086", sessionId, null);
        // System.out.println(body);

        body = sendRequest(url, null, null);
        assertFalse("admin should NOT be logged in", body.contains("User: Admin Adminovic"));
    }

    // Test only storing in DB, so the repositories
    @Test
    @InSequence(5)
    public void createNewTaskTest() {
        openBrowser();
        login(admin);

        // create new task for user
        clickOn(NEW_TASK_BUTTON);
        waitModel().until().element(TASK_NAME_FIELD).is().visible();
        clearAndFillField(TASK_NAME_FIELD, "Cool task");
        TASK_USERS_OPTIONS.get(1).click();
        clearAndFillField(TASK_DESCRIPTION_FIELD, "Do something realy cool");
        clearAndFillField(TASK_DEADLINE_FIELD, "12/07/2014");
        clickOn(SUBMIT_BUTTON);

    }

    // Test the data auditing of entities (concretely the @ModifiedOn)
    @Test
    @InSequence(6)
    public void finishTaskTest() {
        openBrowser();
        login(user);

        clickOn(MY_TASKS_BUTTON);
        waitModel().until().element(MY_TASKS_PAGE_TITLE).is().visible();
        assertEquals("number of my tasks", 3, MY_TASK_ROWS.size());

        // finish the task
        clickOn(MY_TASK_ROWS.get(2).findElement(ByJQuery.selector("form > input[type='image']")));
        waitModel().until().element(MY_TASKS_PAGE_TITLE).is().visible();

        List<WebElement> createdTask = MY_TASK_ROWS.get(2).findElements(ByTagName.tagName("td"));
        assertEquals("task name", "Cool task", createdTask.get(0).getText());
        assertEquals("task description", "Do something realy cool", createdTask.get(1).getText());
        assertEquals("task deadline", "07-12-2014", createdTask.get(2).getText());
        // and the main check :-)
        assertEquals("finished date", formatShortDate(new Date()), createdTask.get(3).getText());

    }

    protected void clearAndFillField(WebElement field, String textToFill) {
        waitModel().until().element(field).is().visible();
        field.clear();
        field.sendKeys(textToFill);
    }

    protected void login(String[] userToLogin) {
        waitModel().until().element(LOGIN_BUTTON).is().visible();
        clickOn(LOGIN_BUTTON);

        clearAndFillField(NAME_INPUT_FIELD, userToLogin[0]);
        clearAndFillField(PASSWORD_INPUT_FIELD, userToLogin[1]);

        guardHttp(LOGIN_FORM_BUTTON).click();
        checkLoggedUser(userToLogin[2]);
    }

    protected void logout() {
        clickOn(LOGOUT_BUTTON);
        waitModel().until().element(LOGIN_PAGE_TITLE).is().visible();
        waitModel().until().element(CURRENT_USER_LABEL).is().not().visible();
    }

    protected void checkLoggedUser(String userName) {
        waitModel().until().element(CURRENT_USER_LABEL).is().visible();
        assertEquals("loggedin user", "User: " + userName, CURRENT_USER_LABEL.getText());
    }

    protected void accessDeniedWarning() {
        waitModel().until().element(ACCESS_DENIED_PAGE_TITLE).is().visible();
        waitModel().until().element(ERROR_MESSAGES).is().visible();
        assertEquals("warning message", "Access denied !!!", ERROR_MESSAGES.getText());
    }

    protected void userNotLoggedinWarning() {
        waitModel().until().element(LOGIN_PAGE_TITLE).is().visible();
        waitModel().until().element(ERROR_MESSAGES).is().visible();
        assertEquals("warning message", "You have to login !!!", ERROR_MESSAGES.getText());
    }

    private String sendRequest(String url, String sessionId, String user) {
        String body = null;
        try {
            HttpURLConnection con = createUrlConnection(sessionId, url, user == null);
            if (user != null) {
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(user);
                wr.flush();
                wr.close();
            }

            InputStream in = con.getInputStream();
            String encoding = con.getContentEncoding();
            encoding = encoding == null ? "UTF-8" : encoding;
            body = IOUtils.toString(in, encoding);

            con.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    private HttpURLConnection createUrlConnection(String sessionId, String path, boolean get) throws IOException {
        URL url = new URL(path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod(get ? "GET" : "POST");
        con.setDoOutput(true);
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Connection", "keep-alive");

        if (sessionId != null) {
            con.setRequestProperty("Cookie", sessionId);
        }

        return con;
    }

    protected void clickOn(WebElement element) {
        waitModel().until().element(element).is().visible();
        waitAjax().until().element(element).is().enabled();
        guardHttp(element).click();
    }

    protected String formatShortDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
        return formater.format(date);
    }

    public String formatLongDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS");
        return formater.format(date);
    }
}
