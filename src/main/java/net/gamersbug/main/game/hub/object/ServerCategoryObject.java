package net.gamersbug.main.game.hub.object;

import org.bukkit.Location;
import org.bukkit.entity.Villager;

public class ServerCategoryObject {

    private Location location;

    private String name, category;

    private Villager entity = null;

    public ServerCategoryObject(String name, String category) {

        this.name = name;
        this.category = category;

    }

    public String getCategory() {

        return this.category;

    }

    public String getName() {

        return this.name;

    }

    public Location getLocation() {

        return this.location;

    }

    public boolean isEntitySet() {

        return (this.entity != null);

    }

    public void setEntity(Villager entity, Location entityLocation) {

        this.entity = entity;
        this.location = entityLocation;

    }

    public Villager getEntity() {

        return entity;

    }

}
