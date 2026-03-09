package com.howlstudio.magic;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MagicManager {
    private final Map<UUID, PlayerMagicData> playerData = new ConcurrentHashMap<>();

    public PlayerMagicData getData(UUID uuid) {
        return playerData.computeIfAbsent(uuid, k -> new PlayerMagicData());
    }

    public void onPlayerJoin(UUID uuid) {
        playerData.put(uuid, new PlayerMagicData());
    }

    public void onPlayerLeave(UUID uuid) {
        playerData.remove(uuid);
    }

    public boolean hasData(UUID uuid) {
        return playerData.containsKey(uuid);
    }
}
