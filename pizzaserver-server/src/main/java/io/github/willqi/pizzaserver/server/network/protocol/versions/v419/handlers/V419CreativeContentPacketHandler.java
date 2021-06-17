package io.github.willqi.pizzaserver.server.network.protocol.versions.v419.handlers;

import com.nukkitx.network.VarInts;
import io.github.willqi.pizzaserver.server.item.ItemStack;
import io.github.willqi.pizzaserver.server.network.protocol.packets.CreativeContentPacket;
import io.github.willqi.pizzaserver.server.network.protocol.versions.PacketHelper;
import io.github.willqi.pizzaserver.server.network.protocol.versions.ProtocolPacketHandler;
import io.netty.buffer.ByteBuf;

public class V419CreativeContentPacketHandler extends ProtocolPacketHandler<CreativeContentPacket> {

    @Override
    public void encode(CreativeContentPacket packet, ByteBuf buffer, PacketHelper helper) {
        VarInts.writeUnsignedInt(buffer, packet.getItems().length);
        for (ItemStack itemStack : packet.getItems()) {
            VarInts.writeUnsignedInt(buffer, itemStack.getItem().ordinal());
            helper.writeItem(itemStack, buffer);
        }
    }

}
