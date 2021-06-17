package io.github.willqi.pizzaserver.server.item;

import io.github.willqi.pizzaserver.nbt.tags.NBTCompound;
import io.github.willqi.pizzaserver.server.item.modifiers.ItemModifier;

import java.util.Arrays;

public class ItemStack {

    private Item item;
    private int damage;
    private int count;

    private NBTCompound tag;

    private Item[] canBreak = new Item[0];
    private Item[] canPlace = new Item[0];


    public ItemStack(Item item) {
        this(item, 1);
    }

    public ItemStack(Item item, int count) {
        this.item = item;
        this.count = count;

        for (ItemModifier itemModifier : this.getItem().getItemModifiers()) {
            itemModifier.onCreated(this);
        }
    }

    public ItemStack(Item item, int count, int damage) {
        this(item, count);
        this.damage = damage;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean hasItemModifier(Class<? extends ItemModifier> clazz) {
        return Arrays.stream(this.item.getItemModifiers())
                .anyMatch(item -> item.getClass().equals(clazz));
    }

    public ItemModifier getItemModifier(Class<? extends ItemModifier> clazz) {
        return Arrays.stream(this.item.getItemModifiers())
                .filter(item -> item.getClass().equals(clazz))
                .findAny()
                .orElse(null);
    }

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public NBTCompound getTag() {
        return this.tag;
    }

    public void setTag(NBTCompound tag) {
        this.tag = tag;
    }

    public Item[] getBlocksCanBreak() {
        return this.canBreak;
    }

    public void setBlocksCanBreak(Item[] blocks) {
        this.canBreak = blocks;
    }

    public Item[] getBlocksCanPlaceOn() {
        return this.canPlace;
    }

    public void setBlocksCanPlaceOn(Item[] blocks) {
        this.canPlace = blocks;
    }

}
