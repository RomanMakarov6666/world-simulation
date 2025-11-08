package animal;

import organizm.Animal;
import organizm.Organizm;
import world.World;

public class Wolf extends Animal {
    public Wolf(World world, int x, int y, int strength, int initiative, boolean male) {
        super(world, x, y, strength, initiative, male);
        super.dieAge=100;
    }

    @Override
    public Organizm cloneAt(int x, int y, int strength, boolean male) {
        return new Wolf(world, x, y, strength, initiative, male);
    }
}
