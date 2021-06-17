package io.github.willqi.pizzaserver.server.network.protocol.packets;

import io.github.willqi.pizzaserver.server.item.ItemStack;

public class CreativeContentPacket extends BedrockPacket {

    public static final int ID = 0x91;

    private ItemStack[] itemStacks = new ItemStack[0];


    public CreativeContentPacket() {
        super(ID);
    }

    public ItemStack[] getItems() {
        return this.itemStacks;
    }

    public void setItems(ItemStack[] itemStacks) {
        this.itemStacks = itemStacks;
    }

}
