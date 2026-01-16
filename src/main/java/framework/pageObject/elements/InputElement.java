package framework.pageObject.elements;

import com.microsoft.playwright.Locator;
import framework.pageObject.ElementDef;

public final class InputElement implements UiElement {
    @Override
    public void setValue(Locator loc, String value, ElementDef def) {
        loc.fill("");
        loc.fill(value == null ? "" : value);
    }
}
