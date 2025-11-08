package organizm;

import world.World;


public abstract class Organizm{
    public int strength, x, y;
    protected int initiative, age;
    public boolean alive;
    protected World world;
    public boolean acted = false;
    public boolean isStrongest = false;
    protected boolean male;
    protected int dieAge = 90;

    public Organizm(World world, int x, int y) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.age = 0;
        this.alive = true;
    }
    public abstract void action() throws InterruptedException;
    abstract void collision(Organizm other) throws InterruptedException;

    void die(){ alive=false; isStrongest=false; world.deleteOrganism(x, y);}
    void increaseAge(){age++;}


    public int getInitiative() {
        return initiative;
    }
    public int getAge() {
        return age;
    }
    public String getName() {
        return this.getClass().getSimpleName();
    }

}