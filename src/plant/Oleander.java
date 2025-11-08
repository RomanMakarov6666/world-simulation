package plant;

import organizm.Organizm;
import organizm.Plant;
import world.World;

public class Oleander extends Plant {
    @Override
    public Organizm cloneAt(int x, int y) {return new Oleander(world, x, y, strength, getInitiative());}

    public Oleander(World world, int x, int y, int strength, int initiative) {
        super(world, x, y, strength, initiative);
        super.percentage=3;
        if (super.world.getRound()%20==0) super.strength++;
    }
}
