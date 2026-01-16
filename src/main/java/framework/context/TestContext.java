package framework.context;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.BrowserContext;


public class TestContext {

    private Playwright playwright;
    private Browser browser;
    private Page page;
    private String currentPageName;
    private BrowserContext context;

    public BrowserContext getContext() { return context; }
    public void setContext(BrowserContext context) { this.context = context; }

    public Playwright getPlaywright() { return playwright; }
    public void setPlaywright(Playwright playwright) { this.playwright = playwright; }

    public Browser getBrowser() { return browser; }
    public void setBrowser(Browser browser) { this.browser = browser; }

    public Page getPage() { return page; }
    public void setPage(Page page) { this.page = page; }

    public String getCurrentPageName() { return currentPageName; }
    public void setCurrentPageName(String currentPageName) { this.currentPageName = currentPageName; }
}
