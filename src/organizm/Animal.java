package organizm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import world.World;

import javax.swing.*;

public abstract class Animal extends Organizm {
    private static final int[][] DIRECTIONS = {
            {0, -1}, {0, 1}, {-1, 0}, {1, 0}
    };
    private static final Random rn = new Random();

    public Animal(World world, int x, int y, int strength, int initiative, boolean male) {
        super(world, x, y);
        this.strength = strength;
        this.initiative = initiative;
        this.male = male;
    }
    int newX, newY;
    @Override
    public void action() throws InterruptedException {

        int random;

        while (true) {
            random = rn.nextInt(4);
            newX = x + DIRECTIONS[random][0];
            newY = y + DIRECTIONS[random][1];
            if (newX >= 0 && newY >= 0 && newX < super.world.worldArr[0].length && newY < super.world.worldArr.length) break;
        }
        increaseAge();
        if (super.world.getOrganizm(newX, newY) != null) {
            collision(super.world.getOrganizm(newX, newY));
        } else {
            super.world.moveOrganism(this, newX, newY);
        }if (age>rn.nextInt(10)+dieAge){die();super.world.strongest=null;}
    }

    @Override
    void collision(Organizm other) throws InterruptedException {
        if(!other.getName().equals(this.getName())) {
            if (this.strength >= other.strength) {
                other.die();
                super.world.moveOrganism(this, newX, newY);
                Thread.sleep(50);
            } else this.die();
        }else{ if(other.male!=this.male)newAnimal(other, this);}

    }
    public void newAnimal(Organizm o1, Organizm o2) throws InterruptedException {
        List<int[]> directions = new ArrayList<>();
        boolean plantPlace = false;
        if(rn.nextInt(100)<30)
            for (int[] dir : DIRECTIONS) {
                newX = x + dir[0];
                newY = y + dir[1];
                if (newX >= 0 && newY >= 0 && newX < super.world.worldArr[0].length && newY < super.world.worldArr.length
                        && world.getOrganizm(newX, newY) == null) {
                    directions.add(new int[]{newX, newY});
                    plantPlace = true;
                }
            }
        if (plantPlace) {
            Thread.sleep(50);
            int random = rn.nextInt(directions.size());
            int[] chosen = directions.get(random);
            world.reproduceOrganism(this.cloneAt(chosen[0], chosen[1], Math.max(o1.strength, o2.strength)+rn.nextInt(2), rn.nextBoolean()));
        }
    }

    public abstract Organizm cloneAt(int x, int y, int strength, boolean male);
    @Override
    void increaseAge() {
        super.increaseAge();
    }

    @Override
    void die() {
        super.die();
    }
}