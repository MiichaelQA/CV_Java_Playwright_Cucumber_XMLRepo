package framework.context;

import framework.pageObject.XmlRepository;

public final class AppContext {
    private AppContext() {}
    public static volatile XmlRepository REPO; // read-only snapshot

    public static XmlRepository repo() {
        if (REPO == null) {
            synchronized (AppContext.class) {
                if (REPO == null) {
                    REPO = XmlRepository.loadFromResource("elements/SiteElements.xml");
                }
            }
        }
        return REPO;
    }
}