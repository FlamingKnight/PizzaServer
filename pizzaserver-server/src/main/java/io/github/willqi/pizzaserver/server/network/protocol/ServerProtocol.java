package io.github.willqi.pizzaserver.server.network.protocol;

import io.github.willqi.pizzaserver.server.Server;
import io.github.willqi.pizzaserver.server.network.protocol.versions.MinecraftVersion;
import io.github.willqi.pizzaserver.server.network.protocol.versions.v419.V419MinecraftVersion;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ServerProtocol {

    public static final String GAME_VERSION = "1.16.100";
    public static final int LATEST_PROTOCOL_VERISON = V419MinecraftVersion.PROTOCOL;
    public static final int LATEST_BLOCK_STATES_VERSION = 17825806;

    public static final Map<Integer, MinecraftVersion> VERSIONS = new HashMap<>();

    /**
     * Called to load all version resources at boot rather than when a player joins.
     * Doing this allows developers to be aware of missing files in version support immediately on boot.
     */
    public static void loadVersions() {
        loadVersion(V419MinecraftVersion.PROTOCOL, V419MinecraftVersion.class);
    }

    private static void loadVersion(int protocol, Class<? extends MinecraftVersion> minecraftVersionClazz) {
        try {
            Constructor<? extends MinecraftVersion> constructor = minecraftVersionClazz.getDeclaredConstructor(Server.class);
            VERSIONS.put(protocol, constructor.newInstance(Server.getInstance()));
        } catch (IllegalAccessException exception) {
            Server.getInstance().getLogger().error("Failed to access protocol version v" + protocol, exception);
            return;
        } catch (InstantiationException exception) {
            Server.getInstance().getLogger().error("Failed to load protocol version v" + protocol, exception);
            return;
        } catch (NoSuchMethodException exception) {
            Server.getInstance().getLogger().error("Failed to find protocol version v" + protocol + " constructor.", exception);
            return;
        } catch (InvocationTargetException exception) {
            Server.getInstance().getLogger().error("Failed to call protocol version v" + protocol + " constructor.", exception);
            return;
        }
        Server.getInstance().getLogger().info("Loaded protocol version v" + protocol);
    }

}
