package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.domain.Seed;

import java.util.List;

public interface SeedsRepository {
    List<Seed> getAllSeeds();
    List<Seed> getAllSeedsWhereTypeIsFruit();
    List<Seed> getAllSeedsWhereTypeIsVegetable();
}
