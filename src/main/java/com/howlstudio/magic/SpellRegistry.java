package com.howlstudio.magic;

import java.util.*;
import java.util.stream.Collectors;

public class SpellRegistry {
    private static final Map<String, Spell> SPELLS = new LinkedHashMap<>();

    static {
        // FIRE
        register(new Spell("fireball",     "Fireball",      MagicSchool.FIRE,      20, 3,  "Launch a blazing fireball that ignites enemies on contact."));
        register(new Spell("flame_wall",   "Flame Wall",    MagicSchool.FIRE,      35, 8,  "Erect a wall of fire that burns any who pass through."));
        register(new Spell("inferno",      "Inferno",       MagicSchool.FIRE,      50, 15, "Unleash an inferno that engulfs a wide area in flames."));
        // ICE
        register(new Spell("frost_bolt",   "Frost Bolt",    MagicSchool.ICE,       15, 2,  "Hurl a bolt of ice that slows and chills the target."));
        register(new Spell("ice_lance",    "Ice Lance",     MagicSchool.ICE,       25, 5,  "Impale a target with a piercing lance of pure ice."));
        register(new Spell("blizzard",     "Blizzard",      MagicSchool.ICE,       45, 12, "Call down a blizzard that freezes all enemies in the area."));
        // LIGHTNING
        register(new Spell("spark",        "Spark",         MagicSchool.LIGHTNING, 10, 1,  "Zap a target with a quick jolt of electricity."));
        register(new Spell("chain_bolt",   "Chain Bolt",    MagicSchool.LIGHTNING, 30, 6,  "A bolt that chains between up to 3 nearby enemies."));
        register(new Spell("thunderstrike","Thunderstrike", MagicSchool.LIGHTNING, 55, 18, "Call down a massive lightning strike from the heavens."));
        // NATURE
        register(new Spell("thorns",       "Thorns",        MagicSchool.NATURE,    20, 4,  "Bind an enemy in thorny vines, dealing damage and rooting them."));
        register(new Spell("rejuvenate",   "Rejuvenate",    MagicSchool.NATURE,    25, 8,  "Heal yourself or a nearby ally with restorative energy."));
        register(new Spell("gale",         "Gale",          MagicSchool.NATURE,    40, 10, "Summon a fierce gale that knocks back all nearby enemies."));
        // SHADOW
        register(new Spell("shadow_bolt",  "Shadow Bolt",   MagicSchool.SHADOW,    20, 3,  "Hurl a bolt of shadow energy that corrupts and weakens."));
        register(new Spell("blink",        "Blink",         MagicSchool.SHADOW,    30, 10, "Instantly teleport up to 10 blocks in the direction you're facing."));
        register(new Spell("void_pulse",   "Void Pulse",    MagicSchool.SHADOW,    50, 20, "Emit a pulse of void energy that silences all nearby enemies."));
        // ARCANE
        register(new Spell("arcane_bolt",  "Arcane Bolt",   MagicSchool.ARCANE,    15, 2,  "A raw bolt of arcane energy — reliable and precise."));
        register(new Spell("mana_shield",  "Mana Shield",   MagicSchool.ARCANE,    35, 15, "Convert mana into a temporary shield that absorbs damage."));
        register(new Spell("arcane_surge", "Arcane Surge",  MagicSchool.ARCANE,    60, 25, "Amplify all magic damage for 10 seconds at the cost of most mana."));
    }

    private static void register(Spell spell) {
        SPELLS.put(spell.getId(), spell);
    }

    public static Spell getById(String id) {
        return SPELLS.get(id.toLowerCase());
    }

    public static List<Spell> getBySchool(MagicSchool school) {
        return SPELLS.values().stream()
                .filter(s -> s.getSchool() == school)
                .collect(Collectors.toList());
    }

    public static Collection<Spell> getAll() {
        return Collections.unmodifiableCollection(SPELLS.values());
    }
}
