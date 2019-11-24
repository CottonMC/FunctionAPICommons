package io.github.cottonmc.functionapi.api.content;


import io.github.cottonmc.functionapi.api.content.enums.CreativeTabs;
import io.github.cottonmc.functionapi.api.content.enums.ItemType;
import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;

/**
 * Stores enough data to create a new item.
* */
public interface ItemTemplate {


    int maxCount();
    ItemType getType();
    void setType(ItemType type);
    boolean isColored();
    void setLootId(String id);
    String getLootid();
    void setMaxCount(int amount);

    int getMaxUseTime();
    void setMaxUseTime(int maxUseTime);

    void setTab(CreativeTabs creativeTabs);
    void setTab(String creativeTabs, FunctionAPIIdentifier item);
    boolean isDefaultTab();

    CreativeTabs getDefaultTab();
    String getCustomTabName();
    FunctionAPIIdentifier getCreativeTabItem();
}
