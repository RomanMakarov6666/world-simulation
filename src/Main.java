import world.World;

import javax.swing.*;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        World world = new World();
        world.createWorld();


        JFrame frame = new JFrame("WORLD SIMULATION");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JScrollPane(world.createTable()));
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        world.printWorld();
        world.shuffle();
        world.printWorld();

        for(int i = 0; i<200; i++){
            world.Round();
            System.out.println(world.getRound());
//            world.printWorld();
        }
        world.printWorld();


    }
}
