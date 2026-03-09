package com.howlstudio.magic;

public class Spell {
    private final String id;
    private final String name;
    private final MagicSchool school;
    private final int manaCost;
    private final int cooldownSeconds;
    private final String description;

    public Spell(String id, String name, MagicSchool school, int manaCost, int cooldownSeconds, String description) {
        this.id = id;
        this.name = name;
        this.school = school;
        this.manaCost = manaCost;
        this.cooldownSeconds = cooldownSeconds;
        this.description = description;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public MagicSchool getSchool() { return school; }
    public int getManaCost() { return manaCost; }
    public int getCooldownSeconds() { return cooldownSeconds; }
    public String getDescription() { return description; }

    public String getDisplayLine() {
        return school.getColorCode() + name + " §7(" + school.getDisplayName() + ") §f— "
                + manaCost + " mana, " + cooldownSeconds + "s CD§r";
    }
}
