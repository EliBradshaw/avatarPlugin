package org.ridgerider.me.avatarnations;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Disc {
    String title;
    Material type;
    public Disc(String title, Material type) {
        this.title = title;
        this.type = type;
    }

    public ItemStack createItem() {
        ItemStack disc = new ItemStack(type);
        ItemMeta meta = disc.getItemMeta();
        Component displayNameComponent = Component.text(title)
                .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE);

        ArrayList<String> lores = new ArrayList<>();
        lores.add(AvatarEffectChecker.lore);

        meta.displayName(displayNameComponent);
        meta.setLore(lores);
        disc.setItemMeta(meta);
        return disc;
    }
}
