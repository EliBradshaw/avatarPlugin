package org.ridgerider.me.avatarnations;

import org.bukkit.Material;

import java.util.Arrays;

public class DiscData {
    static Disc[] discs = {
            new Disc("Fire Nation", Material.MUSIC_DISC_PIGSTEP),
            new Disc("Water Nation", Material.MUSIC_DISC_WAIT),
            new Disc("Air Nation", Material.MUSIC_DISC_STRAD),
            new Disc("Earth Nation", Material.MUSIC_DISC_CAT)
    };

    static String[] names = (String[]) Arrays.stream(discs).map(disc -> disc.title.split(" ")[0].toLowerCase()).toArray();

    public static Disc getDisc(String name) {
        int index = -1;
        int i = 0;
        for (String type : names) {
            if (type.equalsIgnoreCase(name)) {
                index = i;
                break;
            }
            i++;
        }
        if (index == -1)
            return null;
        return discs[index];
    }
}
