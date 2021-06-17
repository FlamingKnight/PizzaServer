package io.github.willqi.pizzaserver.server.item;

import io.github.willqi.pizzaserver.server.item.modifiers.DurableItemModifier;
import io.github.willqi.pizzaserver.server.item.modifiers.ItemModifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Item {

    WOODEN_SWORD("minecraft:wooden_sword", new DurableItemModifier(60));

    private final static Map<String, Item> nameIdLookup = new HashMap<>();

    static {
        Arrays.stream(Item.values())
                .forEach(item -> nameIdLookup.put(item.getId(), item));
    }

    private final String nameId;
    private final ItemModifier[] itemModifiers;


    Item(String nameId, ItemModifier... itemModifiers) {
        this.nameId = nameId;
        this.itemModifiers = itemModifiers;
    }

    public String getId() {
        return this.nameId;
    }

    public ItemModifier[] getItemModifiers() {
        return this.itemModifiers;
    }

    public static Item getItemById(String id) {
        return nameIdLookup.getOrDefault(id, null);
    }

}
