package io.github.cottonmc.functionapi.tools.template;

import io.github.cottonmc.functionapi.api.content.ItemTemplate;
import io.github.cottonmc.functionapi.api.content.enums.CreativeTabs;
import io.github.cottonmc.functionapi.api.content.enums.ItemType;
import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;

public class ItemTemplateImpl implements ItemTemplate {

    private int maxCount = 64;
    private ItemType type = ItemType.NORMAL;
    private String lootID = null;
    private CreativeTabs creativeTabs = CreativeTabs.MISCELLANEOUS;
    private FunctionAPIIdentifier creativeTabItem;
    private String customCreativeTab;

    @Override
    public int maxCount() {
        return maxCount;
    }

    @Override
    public ItemType getType() {
        return type;
    }

    @Override
    public void setType(ItemType type) {
        this.type = type;
    }

    @Override
    public boolean isColored() {
        return false;
    }

    @Override
    public void setLootId(String id) {
     lootID = id;
    }

    @Override
    public String getLootid() {
        return lootID;
    }

    @Override
    public void setMaxCount(int amount) {
        maxCount = amount;
    }

    @Override
    public int getMaxUseTime() {
        return 0;
    }

    @Override
    public void setMaxUseTime(int maxUseTime) {

    }

    @Override
    public void setTab(CreativeTabs creativeTabs) {
        this.creativeTabs = creativeTabs;
    }

    @Override
    public void setTab(String creativeTabs, FunctionAPIIdentifier item) {

        this.customCreativeTab = creativeTabs;
        this.creativeTabItem = item;
    }

    @Override
    public boolean isDefaultTab() {
        return customCreativeTab == null;
    }

    @Override
    public CreativeTabs getDefaultTab() {
        return creativeTabs;
    }

    @Override
    public String getCustomTabName() {
        return customCreativeTab;
    }

    @Override
    public FunctionAPIIdentifier getCreativeTabItem() {
        return creativeTabItem;
    }
}
