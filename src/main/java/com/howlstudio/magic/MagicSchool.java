package com.howlstudio.magic;

public enum MagicSchool {
    FIRE("Fire", "§c"),
    ICE("Ice", "§b"),
    LIGHTNING("Lightning", "§e"),
    NATURE("Nature", "§a"),
    SHADOW("Shadow", "§8"),
    ARCANE("Arcane", "§d");

    private final String displayName;
    private final String colorCode;

    MagicSchool(String displayName, String colorCode) {
        this.displayName = displayName;
        this.colorCode = colorCode;
    }

    public String getDisplayName() { return displayName; }
    public String getColorCode() { return colorCode; }
    public String colored(String text) { return colorCode + text + "§r"; }
}
