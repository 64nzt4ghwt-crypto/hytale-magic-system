package com.howlstudio.magic;

import com.hypixel.hytale.server.core.command.system.CommandManager;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

public final class MagicSystemPlugin extends JavaPlugin {

    private MagicManager magicManager;

    public MagicSystemPlugin(JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        System.out.println("[MagicSystem] Loading...");

        magicManager = new MagicManager();

        new MagicListener(magicManager).register();

        CommandManager.get().register(new MagicCommand(magicManager));

        System.out.println("[MagicSystem] Loaded " + SpellRegistry.getAll().size() + " spells across 6 schools.");
    }
}
