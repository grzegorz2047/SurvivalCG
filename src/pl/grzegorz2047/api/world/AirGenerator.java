package pl.grzegorz2047.api.world;

/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

/**
 *
 * @author Aleksander
 */
public class AirGenerator extends ChunkGenerator {
    @Override
    public byte[] generate(World world, Random random, int x, int z) {
        return new byte[16 * 16 * 256]; // generate an empty world without blocks
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(Bukkit.getWorlds().get(0), 0, 64, 0);
    }
}