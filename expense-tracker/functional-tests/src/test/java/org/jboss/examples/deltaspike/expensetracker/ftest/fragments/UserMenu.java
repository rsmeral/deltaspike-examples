package org.jboss.examples.deltaspike.expensetracker.ftest.fragments;

import org.richfaces.fragment.common.Event;
import org.richfaces.fragment.dropDownMenu.RichFacesDropDownMenu;

/*
 * Hack around RF page fragments bug: the default showEvent on DropDownMenu is
 * CONTEXT but should be CLICK
 */
public class UserMenu extends RichFacesDropDownMenu {

    {
        advanced().setupShowEvent(Event.CLICK);
    }

}
