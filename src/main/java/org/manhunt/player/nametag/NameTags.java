package org.manhunt.player.nametag;

import org.bukkit.ChatColor;

public enum NameTags {
    RUNNER1(ChatColor.GREEN + "(勇者)"),
    HUNTER1(ChatColor.GREEN + "(猎人)"),
    RUNNER2(ChatColor.RED + "(勇者)"),
    HUNTER2(ChatColor.RED + "(猎人)"),
    SPE(ChatColor.GRAY + "(旁观者)");
    private String display;
    NameTags(String display) {
        this.display = display;
    }
    public String getDisplay() {
        return display;
    }
}
