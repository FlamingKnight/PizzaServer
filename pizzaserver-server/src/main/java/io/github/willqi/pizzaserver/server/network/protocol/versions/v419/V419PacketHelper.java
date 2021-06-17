package io.github.willqi.pizzaserver.server.network.protocol.versions.v419;

import com.nukkitx.network.VarInts;
import io.github.willqi.pizzaserver.nbt.streams.nbt.NBTOutputStream;
import io.github.willqi.pizzaserver.nbt.streams.varint.VarIntDataOutputStream;
import io.github.willqi.pizzaserver.server.item.ItemStack;
import io.github.willqi.pizzaserver.server.item.Item;
import io.github.willqi.pizzaserver.server.item.modifiers.BlockingItemModifier;
import io.github.willqi.pizzaserver.server.network.protocol.versions.PacketHelper;
import io.netty.buffer.ByteBuf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class V419PacketHelper extends PacketHelper {

    public final static PacketHelper INSTANCE = new V419PacketHelper();

    @Override
    public void writeItem(ItemStack itemStack, ByteBuf buffer) {

        // network id
        VarInts.writeInt(buffer, itemStack.getItem().ordinal());   // TODO: This probably isn't the proper item id. Find out how to get it

        // item damage + count
        int itemData = ((itemStack.getDamage() << 8) | itemStack.getCount());   // TODO: or maybe it's this id. The above id is just the network id. Does it affect anything?
        VarInts.writeInt(buffer, itemData);

        // Write NBT tag
        if (itemStack.getTag() != null) {
            ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
            try {
                NBTOutputStream stream = new NBTOutputStream(new VarIntDataOutputStream(resultStream));
                stream.writeCompound(itemStack.getTag());
            } catch (IOException exception) {
                throw new RuntimeException("Unable to write NBT tag", exception);
            }

            buffer.writeShortLE(resultStream.toByteArray().length);
            buffer.writeBytes(resultStream.toByteArray());
        } else {
            buffer.writeShortLE(0);
        }

        // Blocks this item can be placed on
        VarInts.writeInt(buffer, itemStack.getBlocksCanPlaceOn().length);
        for (Item item : itemStack.getBlocksCanPlaceOn()) {
            this.writeString(item.getId(), buffer);
        }

        // Blocks this item can break
        VarInts.writeInt(buffer, itemStack.getBlocksCanBreak().length);
        for (Item item : itemStack.getBlocksCanBreak()) {
            this.writeString(item.getId(), buffer);
        }

        if (itemStack.hasItemModifier(BlockingItemModifier.class)) {
            VarInts.writeInt(buffer, 0);    // TODO: blocking ticks?
        }

    }

}
