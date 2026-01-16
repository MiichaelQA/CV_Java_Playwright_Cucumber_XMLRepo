package framework.context;

import com.microsoft.playwright.*;
import framework.pageObject.errorsAction.UiActionException;
import io.cucumber.java.*;

import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Hooks {
    private final TestContext ctx;
    private static final Logger log = LoggerFactory.getLogger(Hooks.class);

    public Hooks(TestContext ctx) {
        this.ctx = ctx;
    }

    // Инициализация браузера и установка контекста главой страницы
    @Before
    public void setUp() {
        var pw = Playwright.create();
        var browser = pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        var context = browser.newContext();
        var page = context.newPage();

        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true)
        );

        ctx.setPlaywright(pw);
        ctx.setBrowser(browser);
        ctx.setContext(context);
        ctx.setPage(page);
    }


    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed() && ctx.getPage() != null) {
                // 1) Screenshot
                byte[] png = ctx.getPage().screenshot(new Page.ScreenshotOptions().setFullPage(true));
                Allure.addAttachment("Screenshot", "image/png", new ByteArrayInputStream(png), ".png");
                // 2) URL
                String url = ctx.getPage().url();
                Allure.addAttachment("URL", "text/plain",
                        new ByteArrayInputStream(url.getBytes(StandardCharsets.UTF_8)), ".txt");
                // 3) HTML
                String html = ctx.getPage().content();
                Allure.addAttachment("Page HTML", "text/html",
                        new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8)), ".html");
                // 4) Trace.zip
                if (ctx.getContext() != null) {
                    Path trace = Files.createTempFile("trace-", ".zip");
                    ctx.getContext().tracing().stop(new Tracing.StopOptions().setPath(trace));
                    try (InputStream is = Files.newInputStream(trace)) {
                        Allure.addAttachment("Playwright Trace", "application/zip", is, ".zip");
                    }
                    Files.deleteIfExists(trace);
                }
            } else {
                if (ctx.getContext() != null) {
                    ctx.getContext().tracing().stop();
                }
            }
        } catch (Exception e) {
            Allure.addAttachment("Allure attachment error", e.toString());
        } finally {
            if (ctx.getBrowser() != null) ctx.getBrowser().close();
            if (ctx.getPlaywright() != null) ctx.getPlaywright().close();
        }
    }


    @After
    public void afterScenario(Scenario scenario) {
        if (!scenario.isFailed()) return;
        if (ctx.getPage() == null) return;
        Throwable error = getScenarioError(scenario);
        if (!(error instanceof UiActionException uae)) return;
        attachScreenshot(uae);
    }

    private Throwable getScenarioError(Scenario scenario) {
        try {
            return (Throwable)
                    scenario.getClass()
                            .getMethod("getError")
                            .invoke(scenario);
        } catch (Exception e) {
            return null;
        }
    }

    private void attachScreenshot(UiActionException e) {
        try {
            byte[] png = ctx.getPage().screenshot(
                    new Page.ScreenshotOptions().setFullPage(true)
            );
            Allure.addAttachment(
                    "Ошибка UI — " + e.action() + " — " + e.elementName(),
                    "image/png",
                    new ByteArrayInputStream(png),
                    ".png"
            );
        } catch (Throwable ignore) {}
    }

}
