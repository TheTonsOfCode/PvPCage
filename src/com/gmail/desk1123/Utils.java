package com.gmail.desk1123;

import de.slikey.effectlib.effect.TextEffect;
import de.slikey.effectlib.util.ParticleEffect;
import org.bukkit.Location;

/**
 *
 * @author dekros987
 */
public class Utils {

    public static void createParticleText(String chars, Location loc) {
        TextEffect text = new TextEffect(PvPCage.getEffectManager());
        text.text = chars;
        text.particle.equals(ParticleEffect.FLAME);
        text.period = 10;
        text.visibleRange = 50F;
        text.autoOrient = true;
        text.particle = de.slikey.effectlib.util.ParticleEffect.FLAME;
//        text.setLocation(loc.add(5, 2, 0));
        text.start();
        //dupa dupa dupa
        
    }
}
