package me.wintyy.wteams.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

@Getter @Setter
public class PlayerTeam {

    private String name;
    private UUID uuid;
    private List<UUID> allies;
    private Location home;
    private List<UUID> members;
    private UUID leader;

    public PlayerTeam(String name, UUID uuid, UUID leader){
        this.name = name;
        this.uuid = uuid;
        this.leader = leader;
    }

    public PlayerTeam(String name){
        this.name = name;
    }

    public PlayerTeam(){

    }
}
