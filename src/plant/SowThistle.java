package plant;

import organizm.Organizm;
import organizm.Plant;
import world.World;

import java.util.Random;

public class SowThistle extends Plant {
    private static final Random rn = new Random();
    public SowThistle(World world, int x, int y, int strength, int initiative) {
        super(world, x, y, strength, initiative);
        super.percentage=5;
        if (super.world.getRound()%20==0) super.strength++;
    }

    @Override
    public void action() throws InterruptedException {
        super.action();
        if(age%20==0 && rn.nextInt(100)<15 && !world.checkForPlaces().isEmpty()){
            int[] arr = world.checkForPlaces().get(rn.nextInt(world.checkForPlaces().size()));
            world.reproduceOrganism(new SowThistle(world, arr[0], arr[1], strength, getInitiative()));
        }

    }

    @Override
    public Organizm cloneAt(int x, int y) {
        return new SowThistle(world, x, y, strength, getInitiative());
    }
}
