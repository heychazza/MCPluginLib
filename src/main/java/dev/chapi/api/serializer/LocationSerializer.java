package dev.chapi.api.serializer;

import dev.chapi.api.exception.InvalidLocationException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public class LocationSerializer {
    public static String toString(Location loc) throws InvalidLocationException {
        if(loc.getWorld() == null) throw new InvalidLocationException();
        return loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getWorld().getName();
    }

    public static Location fromString(String s) throws InvalidLocationException {
        String [] parts = s.split(";");
        double x = Double.parseDouble(parts[0]);
        double y = Double.parseDouble(parts[1]);
        double z = Double.parseDouble(parts[2]);
        UUID u = UUID.fromString(parts[3]);
        World w = Bukkit.getServer().getWorld(u);

        if(w == null) throw new InvalidLocationException();
        return new Location(w, x, y, z);
    }
}
