package framework.pageObject.elements;

import com.microsoft.playwright.Locator;
import framework.pageObject.ElementDef;

public final class ButtonElement implements UiElement {
    @Override
    public void click(Locator loc, ElementDef def) {
        loc.click();
    }
}