package animal;

import organizm.Animal;
import organizm.Organizm;
import world.World;

public class Dog extends Animal {
    public Dog(World world, int x, int y, int strength, int initiative, boolean male) {super(world, x, y, strength, initiative, male);}

    @Override
    public Organizm cloneAt(int x, int y, int strength, boolean male) {
        return new Dog(world, x, y, strength, initiative, male);
    }
}
