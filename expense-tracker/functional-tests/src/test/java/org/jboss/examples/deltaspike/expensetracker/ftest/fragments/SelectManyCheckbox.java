package org.jboss.examples.deltaspike.expensetracker.ftest.fragments;

import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.fragment.Root;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SelectManyCheckbox {

    @Root
    private WebElement root;

    @FindByJQuery("tbody td input")
    private List<WebElement> options;

    public WebElement getRoot() {
        return root;
    }

    public boolean isEnabled() {
        return options.get(0).isEnabled();
    }

    public List<WebElement> getSelectedOptions() {
        List<WebElement> result = new ArrayList<WebElement>();

        for (WebElement option : options) {
            if (option.isSelected()) {
                result.add(option);
            }
        }

        return result;
    }

    public List<WebElement> getAllOptions() {
        return options;
    }

    public List<String> getSelectedOptionsValues() {
        return optionValues(getSelectedOptions());
    }

    public List<String> getSelectedOptionsLabels() {
        return optionLabels(getSelectedOptions());
    }

    public List<String> getAllOptionsValues() {
        return optionValues(getAllOptions());
    }

    public List<String> getAllOptionsLabels() {
        return optionLabels(getAllOptions());
    }

    public void selectByValue(String value) {
        List<WebElement> matchingInputs = root.findElements(ByJQuery.selector("input[value='" + value + "']"));

        for (WebElement input : matchingInputs) {
            if (!input.isSelected()) {
                input.click();
            }
        }
    }

    public void selectByLabel(String value) {
        List<WebElement> matchingLabels = root.findElements(ByJQuery.selector("label:contains('" + value + "')"));

        for (WebElement label : matchingLabels) {
            String inputId = label.getAttribute("for");
            WebElement input = root.findElement(By.id(inputId));

            if (!input.isSelected()) {
                input.click();
            }
        }
    }

    public void selectByIndex(int id) {
        WebElement input = root.findElement(By.id(root.getAttribute("id") + ":" + id));

        if (!input.isSelected()) {
            input.click();
        }
    }

    public void deselectAll() {
        for (WebElement option : options) {
            if (option.isSelected()) {
                option.click();
            }
        }
    }

    private List<String> optionValues(List<WebElement> elements) {
        List<String> result = new ArrayList<String>();

        for (WebElement element : elements) {
            result.add(element.getAttribute("value"));
        }

        return result;
    }

    private List<String> optionLabels(List<WebElement> elements) {
        List<String> result = new ArrayList<String>();

        for (WebElement element : elements) {
            WebElement foundLabel = root.findElement(ByJQuery.selector("label[for='" + element.getAttribute("id") + "']"));
            result.add(foundLabel.getText());
        }

        return result;
    }

}
