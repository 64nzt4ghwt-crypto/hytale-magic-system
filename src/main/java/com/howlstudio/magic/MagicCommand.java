package com.howlstudio.magic;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import java.util.Arrays;
import java.util.UUID;

public class MagicCommand extends AbstractPlayerCommand {
    private final MagicManager manager;

    public MagicCommand(MagicManager manager) {
        super("magic", "Cast and manage your spells. Usage: /magic <learn|cast|list|info>");
        this.manager = manager;
    }

    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        UUID uuid = playerRef.getUuid();
        if (uuid == null) return;
        PlayerMagicData data = manager.getData(uuid);

        String input = ctx.getInputString().trim();
        String[] parts = input.split("\\s+");
        String[] args = parts.length > 1 ? Arrays.copyOfRange(parts, 1, parts.length) : new String[0];

        if (args.length == 0) {
            sendHelp(playerRef);
            return;
        }

        switch (args[0].toLowerCase()) {
            case "list" -> doList(playerRef, data);
            case "learn" -> {
                if (args.length < 2) { playerRef.sendMessage(Message.raw("§cUsage: /magic learn <spell_id>")); return; }
                doLearn(playerRef, data, args[1]);
            }
            case "cast" -> {
                if (args.length < 2) { playerRef.sendMessage(Message.raw("§cUsage: /magic cast <spell_id>")); return; }
                doCast(playerRef, data, args[1]);
            }
            case "info" -> {
                if (args.length < 2) { playerRef.sendMessage(Message.raw("§cUsage: /magic info <spell_id>")); return; }
                doInfo(playerRef, args[1]);
            }
            default -> sendHelp(playerRef);
        }
    }

    private void sendHelp(PlayerRef ref) {
        ref.sendMessage(Message.raw("§6[Magic] §eCommands:"));
        ref.sendMessage(Message.raw("§f/magic list §7— Show your learned spells"));
        ref.sendMessage(Message.raw("§f/magic learn <id> §7— Learn a spell"));
        ref.sendMessage(Message.raw("§f/magic cast <id> §7— Cast a spell"));
        ref.sendMessage(Message.raw("§f/magic info <id> §7— Get spell details"));
    }

    private void doList(PlayerRef ref, PlayerMagicData data) {
        if (data.getLearnedSpells().isEmpty()) {
            ref.sendMessage(Message.raw("§6[Magic] §fYou haven't learned any spells yet. Try §e/magic learn fireball§f."));
            return;
        }
        ref.sendMessage(Message.raw("§6[Magic] §fYour spells (Mana: §b" + data.getMana() + "§f/§b" + data.getMaxMana() + "§f):"));
        for (String id : data.getLearnedSpells()) {
            Spell spell = SpellRegistry.getById(id);
            if (spell != null) {
                String cd = data.isOnCooldown(spell) ? " §c(" + data.getCooldownRemaining(spell) + "s)" : " §a(ready)";
                ref.sendMessage(Message.raw("  " + spell.getDisplayLine() + cd));
            }
        }
    }

    private void doLearn(PlayerRef ref, PlayerMagicData data, String spellId) {
        Spell spell = SpellRegistry.getById(spellId);
        if (spell == null) {
            ref.sendMessage(Message.raw("§c[Magic] Unknown spell: §f" + spellId));
            return;
        }
        if (data.hasLearned(spell.getId())) {
            ref.sendMessage(Message.raw("§6[Magic] §fYou already know §e" + spell.getName() + "§f."));
            return;
        }
        data.learn(spell.getId());
        ref.sendMessage(Message.raw("§6[Magic] §fLearned §e" + spell.getName()
                + "§f! (" + spell.getSchool().getColorCode() + spell.getSchool().getDisplayName() + "§f)"));
    }

    private void doCast(PlayerRef ref, PlayerMagicData data, String spellId) {
        Spell spell = SpellRegistry.getById(spellId);
        if (spell == null) {
            ref.sendMessage(Message.raw("§c[Magic] Unknown spell: §f" + spellId));
            return;
        }
        if (!data.hasLearned(spell.getId())) {
            ref.sendMessage(Message.raw("§c[Magic] You haven't learned §f" + spell.getName() + "§c. Try §f/magic learn " + spell.getId()));
            return;
        }
        if (!data.hasMana(spell.getManaCost())) {
            ref.sendMessage(Message.raw("§c[Magic] Not enough mana! Need §f" + spell.getManaCost() + "§c, have §f" + data.getMana()));
            return;
        }
        if (data.isOnCooldown(spell)) {
            ref.sendMessage(Message.raw("§c[Magic] §f" + spell.getName() + "§c is on cooldown for §f"
                    + data.getCooldownRemaining(spell) + "s§c more."));
            return;
        }
        data.registerCast(spell);
        ref.sendMessage(Message.raw("§6[Magic] §fYou cast " + spell.getSchool().getColorCode() + spell.getName()
                + "§f! §7(Mana: " + data.getMana() + "/" + data.getMaxMana() + ")"));
    }

    private void doInfo(PlayerRef ref, String spellId) {
        Spell spell = SpellRegistry.getById(spellId);
        if (spell == null) {
            ref.sendMessage(Message.raw("§c[Magic] Unknown spell: §f" + spellId));
            return;
        }
        ref.sendMessage(Message.raw("§6[Magic] §e" + spell.getName() + " §7(" + spell.getSchool().getDisplayName() + ")"));
        ref.sendMessage(Message.raw("  §fMana: §b" + spell.getManaCost() + " §f| Cooldown: §b" + spell.getCooldownSeconds() + "s"));
        ref.sendMessage(Message.raw("  §7" + spell.getDescription()));
    }
}
