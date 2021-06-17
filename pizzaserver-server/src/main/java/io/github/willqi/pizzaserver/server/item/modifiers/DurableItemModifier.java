package io.github.willqi.pizzaserver.server.item.modifiers;

import io.github.willqi.pizzaserver.server.item.ItemStack;

/**
 * Any item that has durability
 */
public class DurableItemModifier extends ItemModifier {

    private final int baseDurability;


    public DurableItemModifier(int baseDurability) {
        this.baseDurability = baseDurability;
    }

    @Override
    public void onCreated(ItemStack itemStack) {
        itemStack.setDamage(this.baseDurability);
    }

}
