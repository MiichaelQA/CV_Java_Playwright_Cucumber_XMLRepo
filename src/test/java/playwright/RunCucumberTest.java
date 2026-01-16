package playwright;

import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(
        key = "cucumber.glue",
        value = "playwright.steps, framework.context"
)
@ConfigurationParameter(
        key = "cucumber.plugin",
        value = "pretty, io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
)
@ConfigurationParameter(
        key = "cucumber.language",
        value = "ru"
)
public class RunCucumberTest {}
