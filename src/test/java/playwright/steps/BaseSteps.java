package playwright.steps;

import framework.context.AppContext;
import framework.pageObject.Navigator;
import io.cucumber.java.ru.*;
import framework.context.TestContext;

public class BaseSteps {

    private final TestContext ctx;
    private final Navigator nav;

    public BaseSteps(TestContext ctx) {
        this.ctx = ctx;
        this.nav = new Navigator(ctx, AppContext.repo());
    }

    @Когда("выполнен переход к странице {string}")
    public void openMainPage(String pageName) {
        if (pageName.equals(ctx.getCurrentPageName())) {
            nav.setCurrent(ctx.getPage(), pageName);
        } else {
            nav.open(ctx.getPage(), pageName);
        }
    }

    @Тогда("Страница обновлена")
    public void pageRefreshed(){
        ctx.getPage().reload();
    }

    @Тогда("Страница загружена")
    public void pageLoaded(){ }


    @Тогда("поле {string} заполняется значением {string}")
    public void fillField(String name, String value) {
        nav.setValue(ctx.getPage(), name, value);
    }

    @Тогда("выполнено нажатие на кнопку {string}")
    public void clickBtn(String name) {
        nav.click(ctx.getPage(), name);
    }

    @Тогда("поле {string} содержит сообщение {string}")
    public void contains(String name, String expected) {
        nav.assertContains(ctx.getPage(), name, expected);
    }

}
