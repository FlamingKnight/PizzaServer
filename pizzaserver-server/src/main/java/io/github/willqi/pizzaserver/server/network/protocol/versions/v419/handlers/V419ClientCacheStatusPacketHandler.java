package io.github.willqi.pizzaserver.server.network.protocol.versions.v419.handlers;

import io.github.willqi.pizzaserver.server.network.protocol.packets.ClientCacheStatusPacket;
import io.github.willqi.pizzaserver.server.network.protocol.versions.PacketHelper;
import io.github.willqi.pizzaserver.server.network.protocol.versions.ProtocolPacketHandler;
import io.netty.buffer.ByteBuf;

public class V419ClientCacheStatusPacketHandler extends ProtocolPacketHandler<ClientCacheStatusPacket> {

    @Override
    public ClientCacheStatusPacket decode(ByteBuf buffer, PacketHelper helper) {
        ClientCacheStatusPacket packet = new ClientCacheStatusPacket();
        packet.setSupported(buffer.readBoolean());
        return packet;
    }

    @Override
    public void encode(ClientCacheStatusPacket packet, ByteBuf buffer, PacketHelper helper) {
        buffer.writeBoolean(packet.isSupported());
    }

}
