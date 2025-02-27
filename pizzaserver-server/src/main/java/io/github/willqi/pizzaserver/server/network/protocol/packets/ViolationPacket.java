package io.github.willqi.pizzaserver.server.network.protocol.packets;

public class ViolationPacket extends BedrockPacket {

    public static final int ID = 0x9c;

    private int type;
    private int severity;
    private int packetId;
    private String message;

    public ViolationPacket() {
        super(ID);
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSeverity() {
        return this.severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public int getPacketId() {
        return this.packetId;
    }

    public void setPacketId(int packetId) {
        this.packetId = packetId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
