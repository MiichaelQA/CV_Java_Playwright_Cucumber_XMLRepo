package framework.pageObject.elements;

import com.microsoft.playwright.Locator;
import framework.pageObject.ElementDef;

public interface UiElement {
    default void setValue(Locator loc, String value, ElementDef def) {
        throw new UnsupportedOperationException(def.type() + " не поддерживает setValue");
    }

    default void click(Locator loc, ElementDef def) {
        throw new UnsupportedOperationException(def.type() + " не поддерживает click");
    }

    default void assertContains(Locator loc, String expected, ElementDef def) {
        throw new UnsupportedOperationException(def.type() + " не поддерживает assertContains");
    }
}
