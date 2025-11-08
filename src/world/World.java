package world;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import animal.Dog;
import animal.Lion;
import animal.Wolf;
import organizm.Animal;
import organizm.Organizm;
import organizm.Plant;
import plant.Grass;
import plant.Oleander;
import plant.SowThistle;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class World {
    public Organizm strongest = null;
    public Organizm[][] worldArr = new Organizm[40][40];
    private static final Random rn = new Random();
    private int dogs = 0;
    private int wolfs = 0;
    private int sowThistles = 0;
    private int grasses = 0;
    private int round = 0;
    private int oleanders = 0;
    private int lions = 0;
    public void createWorld() {
//        int count = 50;
        for (int y = 0; y < worldArr.length; y++) {
            for (int x = 0; x < worldArr[y].length; x++) {
//                int choice = rn.nextInt(4);
                round = 0;
                if(dogs<160){
                    worldArr[y][x] = new Dog(this, x, y, 15, 9+ rn.nextInt(2), rn.nextBoolean()); //Dogs are the best creatures in the world. Especially my dog.
                    dogs++;
                }
                else if (grasses<30){
                    grasses++;
                    worldArr[y][x] = new Grass(this, x, y, 1, 10);
                }
                else if(wolfs<60) {
                    wolfs++;
                    worldArr[y][x] = new Wolf(this, x, y, 20, 9+ rn.nextInt(2), rn.nextBoolean());
                }
                else if (sowThistles<30){
                    sowThistles++;
                    worldArr[y][x] = new SowThistle(this, x, y, 10, 9);
                }
                else if (oleanders<10){
                    oleanders++;
                    worldArr[y][x] = new Oleander(this, x, y, 20, 9);
                }
                else if(lions<15){
                    lions++;
                    worldArr[y][x] = new Lion(this, x, y, 30 + rn.nextInt(6), 100, rn.nextBoolean());
                }
            }
        }
    }

    public void shuffle() {
        List<Organizm> all = new ArrayList<>();

        // collect all organisms
        for (int y = 0; y < worldArr.length; y++) {
            for (int x = 0; x < worldArr[0].length; x++) {
                if (worldArr[y][x] != null) {
                    all.add(worldArr[y][x]);
                    worldArr[y][x] = null;
                }
            }
        }

        // place them in random empty cells
        for (Organizm o : all) {
            int x, y;
            do {
                x = rn.nextInt(worldArr[0].length);
                y = rn.nextInt(worldArr.length);
            } while (worldArr[y][x] != null);

            worldArr[y][x] = o;
            o.x = x;
            o.y = y;
        }
    }


    public void Round() throws InterruptedException {
        round++;
        List<Organizm> animals = new ArrayList<>();
        List<Organizm> plants = new ArrayList<>();
        strongest=null;
        for (int y = 0; y < worldArr.length; y++) {
            for (int x = 0; x < worldArr[y].length; x++) {
                Organizm o = worldArr[y][x];
                if (o != null) {
                    o.acted = false;
                    if (o instanceof Animal) animals.add(o);
                    else plants.add(o);
                    o.isStrongest = false;


                    if (strongest == null || o.strength > strongest.strength) {
                        strongest = o;
                    }
                }

                updateCell(y, x);
            }
        }
        if(strongest!=null)getOrganizm(strongest.x, strongest.y).isStrongest=true;

        Comparator<Organizm> comp = Comparator
                .comparingInt(Organizm::getInitiative).reversed()
// basicly does this:  Comparator<Organizm> comp = (a, b) -> Integer.compare(a.getInitiative(), b.getInitiative()); fcking lambda
                .thenComparingInt(Organizm::getAge).reversed();

        animals.sort(comp);
        plants.sort(comp);

        for (Organizm o : animals)
            if (o.alive && !o.acted) { o.action(); o.acted = true;}

        for (Organizm o : plants)
            if (o.alive && !o.acted) { o.action(); o.acted = true;}
    }

    public Organizm getOrganizm(int x, int y) {
        return worldArr[y][x];
    }

    public void moveOrganism(Organizm o, int newX, int newY) {
//        System.out.println(o.getName() + " moves from (" + o.x + "," + o.y + ") to (" + newX + "," + newY + ")");
        worldArr[o.y][o.x] = null;


        int oldX = o.x, oldY = o.y;
        o.x = newX;
        o.y = newY;
        worldArr[newY][newX] = o;
        SwingUtilities.invokeLater(() -> {
            try {
                updateCell(o.y, o.x);
                updateCell(oldY, oldX);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void deleteOrganism(int x, int y) {
        worldArr[y][x] = null;
        SwingUtilities.invokeLater(() -> {
            try {
                updateCell(y, x);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

    public void reproduceOrganism(Organizm o) {

        worldArr[o.y][o.x] = o;
        SwingUtilities.invokeLater(() -> {
            try {
                updateCell(o.y, o.x);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void printWorld() {
        System.out.println("\n=== WORLD MAP ===\n");
        for (Organizm[] organizms : worldArr) {
            for (Organizm o : organizms) {
                if (o != null) {
                    String shortName = o.getName().substring(0, Math.min(3, o.getName().length()));
                    System.out.printf("%-4s| ", shortName);
                } else {
                    System.out.print("--- | ");
                }
            }
            System.out.println();
        }
//        if(lastStrongest!=null)System.out.println("The strongest animal in the world: " + lastStrongest.getName() + ", " + lastStrongest.strength);
    }

    DefaultTableModel model;
    JTable table;
    public JTable createTable() {
        int cols = worldArr[0].length;
        String[] columnNames = new String[cols];
        for (int i = 0; i < cols; i++) columnNames[i] = "" + i;

        model = new DefaultTableModel(worldArr, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if(worldArr[row][column]!=null && worldArr[row][column].isStrongest) c.setBackground(Color.ORANGE);
                else if (worldArr[row][column] instanceof Animal) {
                    if(worldArr[row][column].getName().equals("Wolf")) c.setBackground(Color.GRAY);
                    else if(worldArr[row][column].getName().equals("Lion")) c.setBackground(Color.PINK);
                    else c.setBackground(Color.CYAN);
                } else if (worldArr[row][column] instanceof Plant) {
                    if(worldArr[row][column].getName().equals("SowThistle"))c.setBackground(Color.RED);
                    else if(worldArr[row][column].getName().equals("Oleander"))c.setBackground(Color.MAGENTA);
                    else c.setBackground(Color.GREEN);
                } else {
                    c.setBackground(Color.WHITE);
                }


                if (isSelected) {
                    c.setBackground(Color.BLUE);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setForeground(Color.BLACK);
                }

                return c;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        return table;
    }

    public void updateCell(int row, int col) throws InterruptedException {
        if (worldArr[row][col] != null) {
            model.setValueAt(worldArr[row][col].getName(), row, col);
        } else {
            model.setValueAt("", row, col);
        }
        table.repaint();
    }
    public int getRound(){return round;}

    public List<int[]> checkForPlaces(){
        List<int[]> list = new ArrayList<>();
        for (int y = 0; y < worldArr.length; y++)
            for (int x = 0; x < worldArr[y].length; x++)
                if(worldArr[y][x]==null)list.add(new int[]{x, y});

        return list;
    }

}
