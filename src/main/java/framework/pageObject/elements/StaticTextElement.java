package framework.pageObject.elements;

import com.microsoft.playwright.Locator;
import framework.pageObject.ElementDef;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public final class StaticTextElement implements UiElement {

    @Override
    public void assertContains(Locator loc, String expected, ElementDef def) {
        assertThat(loc).containsText(expected);
    }
}
