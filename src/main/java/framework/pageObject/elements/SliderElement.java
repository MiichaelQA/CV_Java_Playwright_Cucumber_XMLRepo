package framework.pageObject.elements;

import com.microsoft.playwright.Locator;
import framework.pageObject.ElementDef;

public final class SliderElement implements UiElement {

    @Override
    public void setValue(Locator loc, String value, ElementDef def) {
        String v = (value == null) ? "" : value.trim();
        loc.evaluate(
                "(el, v) => {" +
                        "  if (!el) throw new Error('Slider element not found');" +
                        "  el.value = String(v);" +
                        "  el.dispatchEvent(new Event('input', { bubbles: true }));" +
                        "  el.dispatchEvent(new Event('change', { bubbles: true }));" +
                        "}",
                v
        );
    }
}
