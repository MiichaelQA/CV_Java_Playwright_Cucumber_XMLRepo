package framework.pageObject.elements;

import framework.pageObject.UiType;
import java.util.EnumMap;

public final class UiElementRegistry {
    private final EnumMap<UiType, UiElement> map = new EnumMap<>(UiType.class);

    public UiElementRegistry() {
        map.put(UiType.INPUT, new InputElement());
        map.put(UiType.SLIDER, new SliderElement());
        map.put(UiType.BUTTON, new ButtonElement());
        map.put(UiType.STATICTEXT, new StaticTextElement());
        // map.put(UiType.CHECKBOX, new CheckboxElement());
        // map.put(UiType.LINK, new LinkElement());
        // map.put(UiType.ANY, new AnyElement());
    }

    public UiElement by(UiType type) {
        UiElement element = map.get(type);
        if (element == null) throw new IllegalStateException("Нет UiElement для типа: " + type);
        return element;
    }
}
