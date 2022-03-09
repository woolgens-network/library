package net.woolgens.library.spigot.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public final class LocationParser {

    public static Location stringToLocation(String stringLocation) {
        String[] args = stringLocation.split(";");
        World world = Bukkit.getWorld(args[0]);
        double x = Double.valueOf(args[1]).doubleValue();
        double y = Double.valueOf(args[2]).doubleValue();
        double z = Double.valueOf(args[3]).doubleValue();
        double yaw = Double.valueOf(args[4]).doubleValue();
        double pitch = Double.valueOf(args[5]).doubleValue();
        return new Location(world, x, y, z, (float) yaw, (float) pitch);
    }

    public static String locationToString(Location location) {
        StringBuilder builder = (new StringBuilder()).append(location.getWorld().getName()).append(";").append(location.getX()).append(";").append(location.getY()).append(";").append(location.getZ()).append(";").append(location.getYaw()).append(";").append(location.getPitch());
        return builder.toString();
    }
}
