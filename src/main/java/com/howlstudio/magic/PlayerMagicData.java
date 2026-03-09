package com.howlstudio.magic;

import java.util.*;

public class PlayerMagicData {
    private static final int MAX_MANA = 100;
    private static final int MANA_REGEN_AMOUNT = 5;

    private int mana = MAX_MANA;
    private final Set<String> learnedSpells = new HashSet<>();
    private final Map<String, Long> cooldowns = new HashMap<>();

    public int getMana() { return mana; }
    public int getMaxMana() { return MAX_MANA; }

    public boolean hasMana(int amount) { return mana >= amount; }

    public void consumeMana(int amount) {
        mana = Math.max(0, mana - amount);
    }

    public void regenMana() {
        mana = Math.min(MAX_MANA, mana + MANA_REGEN_AMOUNT);
    }

    public boolean hasLearned(String spellId) {
        return learnedSpells.contains(spellId);
    }

    public boolean learn(String spellId) {
        return learnedSpells.add(spellId);
    }

    public Set<String> getLearnedSpells() {
        return Collections.unmodifiableSet(learnedSpells);
    }

    public boolean isOnCooldown(Spell spell) {
        Long last = cooldowns.get(spell.getId());
        if (last == null) return false;
        return (System.currentTimeMillis() - last) < (spell.getCooldownSeconds() * 1000L);
    }

    public long getCooldownRemaining(Spell spell) {
        Long last = cooldowns.get(spell.getId());
        if (last == null) return 0;
        long elapsed = System.currentTimeMillis() - last;
        long total = spell.getCooldownSeconds() * 1000L;
        return Math.max(0, (total - elapsed) / 1000L);
    }

    public boolean canCast(Spell spell) {
        return hasLearned(spell.getId()) && hasMana(spell.getManaCost()) && !isOnCooldown(spell);
    }

    public void registerCast(Spell spell) {
        consumeMana(spell.getManaCost());
        cooldowns.put(spell.getId(), System.currentTimeMillis());
    }
}
