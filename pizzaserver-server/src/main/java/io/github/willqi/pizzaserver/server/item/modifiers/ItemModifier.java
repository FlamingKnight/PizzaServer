package io.github.willqi.pizzaserver.server.item.modifiers;

import io.github.willqi.pizzaserver.server.item.ItemStack;

/**
 * Unique behaviour of items
 */
public abstract class ItemModifier {

    /**
     * Called when the item stack is created
     * Used to setup any unique properties for the item stack
     * @param itemStack
     */
    public void onCreated(ItemStack itemStack) {}

}
