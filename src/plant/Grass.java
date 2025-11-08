package plant;

import organizm.Organizm;
import organizm.Plant;
import world.World;

public class Grass extends Plant {
    public Grass(World world, int x, int y, int strength, int initiative) {
        super(world, x, y, strength, initiative);
        super.percentage = 10;
        if (super.world.getRound()%50==0) super.strength++;
    }

    @Override
    public Organizm cloneAt(int x, int y) {
        return new Grass(world, x, y, strength, getInitiative());
    }
}
