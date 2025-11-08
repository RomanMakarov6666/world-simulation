package animal;

import organizm.Animal;
import organizm.Organizm;
import world.World;

public class Lion extends Animal {
    @Override
    public Organizm cloneAt(int x, int y, int strength, boolean male) {return new Lion(world, x, y, strength, initiative, male);}

    public Lion(World world, int x, int y, int strength, int initiative, boolean male) {
        super(world, x, y, strength, initiative, male);
        super.dieAge=200;
    }
}
