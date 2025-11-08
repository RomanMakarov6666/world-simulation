package organizm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import world.World;

import javax.swing.*;

public abstract class Plant extends Organizm {
    private static final int[][] DIRECTIONS = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };

    private static final Random rn = new Random();
    protected float percentage = 15;

    public Plant(World world, int x, int y, int strength, int initiative) {
        super(world, x, y);
        this.strength = strength;
        this.initiative = initiative;
    }

    public abstract Organizm cloneAt(int x, int y);

    int newX;
    int newY;
    @Override
    public void action() throws InterruptedException {

        List<int[]> directions = new ArrayList<>();
        boolean plantPlace = false;
        if(rn.nextInt(100)<percentage){
        for (int[] dir : DIRECTIONS) {
            newX = x + dir[0];
            newY = y + dir[1];
            if (newX >= 0 && newY >= 0 && newX < super.world.worldArr[0].length && newY < super.world.worldArr.length
                    && world.getOrganizm(newX, newY) == null) {
                directions.add(new int[]{newX, newY});
                plantPlace = true;
            }
        }}

        increaseAge();

        if (plantPlace) {
            int random = rn.nextInt(directions.size());
            int[] chosen = directions.get(random);
            world.reproduceOrganism(this.cloneAt(chosen[0], chosen[1]));
            Thread.sleep(50);
        }
    }

    @Override
    void collision(Organizm other) {}
}
