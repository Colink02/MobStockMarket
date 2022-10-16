package com.colink02dev.mobstockmarket;

import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;

public class EntityRate {
    public static double getEntityRate(EntityType type) {
        double value = 0.0;
        //Health points
        assert type.getEntityClass() != null;
        if(Attributable.class.isAssignableFrom(type.getEntityClass())) {
            value += 2 * type.getDefaultAttributes().getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue();
            value = switch (type) {
                case AXOLOTL, CAT, BAT, SNOWMAN, DOLPHIN, BEE, VILLAGER, TURTLE -> value * -5;
                default -> value;
            };
        }
        return value;
    }
}
