import java.util.Random;
import java.util.Scanner;

public class Battleship {

    /*
    You will be recreating the game of battleships. A player will place 5 of their ships on a 10 by 10 grid.
    The computer player will deploy five ships on the same grid. Once the game starts the player and computer
    take turns, trying to sink each other's ships by guessing the coordinates to "attack". The game ends when
    either the player or computer has no ships left.
     */

    public static String[][] playingField = new String[10][10];
    public static String[][] computerShots = new String[10][10];
    public static int shipCount = 0;
    public static int compCount = 0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("**** Welcome to Battle Ships game ****");
        System.out.println();
        System.out.println("Right now, the sea is empty.");
        System.out.println();

        showMap();      // Prints the current Array data. (Shows as map!)

        System.out.println();
        System.out.println("Where would you like to place your ships? \t (" + shipCount + "/5 USED)");
        System.out.println();


        /* Step 2 - Deploy Player's Ships */
        for (shipCount = 0; shipCount < 5; shipCount++) {
            System.out.print("Enter X coordinate for ship " + (shipCount + 1) + ": ");
            int getX = input.nextInt();
            System.out.print("Enter Y coordinate for ship " + (shipCount + 1) + ": ");
            int getY = input.nextInt();
            System.out.println();

            verifyInput(getX, getY);

        }
        System.out.println();

        /* Step 3 - Deploy Computer's Ships */

        System.out.println("Computer is deploying ships");
        for (compCount = 0; compCount < 5; compCount++) {
            boolean success = false;
            int X = 0;
            int Y = 0;
            while (success == false) {
                Random gen = new Random();
                X = gen.nextInt(9);
                Y = gen.nextInt(9);

                success = checkCoords(X, Y);
            }
            playingField[Y][X] = "2";
            System.out.println((compCount + 1) + ". ship DEPLOYED");
        }
        System.out.println("-------------------------------------");
        System.out.println();

        showMap();
        System.out.println();

        /* Step 4 - Battle */

        System.out.println("**** BEGIN BATTLE ****");
        int turnNum = 0;        // Track turns.
        System.out.println();
        while ((shipCount != 0) && (compCount != 0)) {
            if (turnNum % 2 == 0) {
                boolean fired = false;
                System.out.println("YOUR TURN");
                while (fired == false) {
                    System.out.print("Enter X coordinate: ");
                    int X = input.nextInt();
                    System.out.print("Enter Y coordinate: ");
                    int Y = input.nextInt();

                    if (!validate(X, Y)) {
                        System.out.println("Coordinates Invalid. Try again! (0-9)");
                    }
                    if (shotAlready(X, Y)) {
                        System.out.println("You've shot here already. Try a new coordinate.");
                    } else {
                        fireWeapon(X, Y);
                        fired = true;
                    }
                }
            } else {
                System.out.println();
                System.out.println("COMPUTER'S TURN");

                boolean goodshot = false;
                int X = 0;
                int Y = 0;
                while (goodshot == false) {

                    Random shot = new Random();
                    X = shot.nextInt(9);
                    Y = shot.nextInt(9);

                    goodshot = compShots(X, Y); // Turn true once the shot can be made.
                }

                compShoots(X, Y); // Fire Computer's Weapon!

                System.out.println();
                showMap();
                System.out.println();
                System.out.println("Your ships: " + shipCount + " | Computer ships: " + compCount);
            }
                turnNum++;
            }

            System.out.println();
            if (compCount == 0) {
                System.out.println("Hooray! You win the battle. :)");
            }
            else {
                System.out.println("Aw... the computer wins this time! Better luck next round!");
            }

        }

    public static boolean compShots(int X, int Y) { // Checks computer array for shots taken.
        if (computerShots[Y][X] != null) {
            if (computerShots[Y][X].equals("x")) {
                return false;
            }
        }
        return true; // Take the shot!
    }

    public static void compShoots(int X, int Y) { // Fires on the map array, tracks on computer array.
        if (computerShots[Y][X] != null) {
            if (!computerShots[Y][X].equals("x")) {
                if (playingField[Y][X].equals("1")) { // player ship.
                    System.out.println("Computer destroyed your ship at: " + X + ", " + Y + "!");
                    playingField[Y][X] = "x"; // Update player's map.
                    computerShots[Y][X] = "x"; // Update computer's record.
                    shipCount--;
                }
                if (playingField[Y][X].equals("2")) { // computer ship.
                    System.out.println("Computer sunk his own ship at: " + X + ", " + Y + "!");
                    playingField[Y][X] = "!";
                    computerShots[Y][X] = "x";
                    compCount--;
                }
                else {
                    System.out.println("Computer missed.");
                    computerShots[Y][X] = "x";
                }
            }
        }
        else {
            System.out.println("Computer missed.");
            computerShots[Y][X] = "x";
        }
    }

    public static void fireWeapon(int X, int Y) {
        String target = getTarget(X, Y);
        if (target == "") {
            System.out.println("Sorry, you missed!");
            playingField[Y][X] = "-";
        }
        if (target == "1") {
            System.out.println("Oh no, you sunk your own ship! :(");
            playingField[Y][X] = "x";
            shipCount--;
        }
        if (target == "2") {
            System.out.println("Boom! You sunk the ship!");
            playingField[Y][X] = "!";
            compCount--;
        }
    }

    public static boolean shotAlready(int X, int Y) {
        if (playingField[Y][X] != null) {
            if ((playingField[Y][X] == "!") || (playingField[Y][X] == "x") || (playingField[Y][X]) == "-") {
                return true;
            }
        }
        else {
            return false;
        }
        return false;
    }

    public static String getTarget(int X, int Y) {
        if (playingField[Y][X] != null) {
            return playingField[Y][X];
        }
        else {
            return "";
        }
    }

    public static boolean validate(int X, int Y) {
        if ((X > 9) || (X < 0)) {
            return false;
        }
        if ((Y > 9) || (Y < 0)) {
            return false;
        }
        return true;
    }

    public static void showMap() {

        System.out.println("   0123456789   ");
        for (int r = 0; r < playingField.length; r++) {
            System.out.print(r + " |");
            for (int c = 0; c < playingField[r].length; c++) {
                if (playingField[r][c] != null) {
                    if (playingField[r][c].equals("1")) {
                        System.out.print("@");
                    }
                    else if (playingField[r][c].equals("2")) {
                        System.out.print(" ");
                    }
                    else {
                        System.out.print(playingField[r][c]);
                    }
                } else {
                    System.out.print(" ");
                }
            }
            System.out.print("| " + r + "\n");
        }
        System.out.println("   0123456789   ");

    }

    public static void verifyInput(int X, int Y) {
        if ((X > 9) || (X < 0)) {
            System.out.println("Your X coordinate must be 0-9.");
            shipCount--;
            return;
        }
        if ((Y > 9) || (Y < 0)) {
            System.out.println("Your Y coordinate must be 0-9");
            shipCount--;
            return;
        }
        else {
            if (playingField[Y][X] != null) {
                if (playingField[Y][X].equals("1")) {
                    System.out.println("Ship already located here.");
                    shipCount--;
                }
            }
            else {
                playingField[Y][X] = "1";
                showMap();
                System.out.println();
                System.out.println("Ship successfully added.");
            }
        }
    }

    public static boolean checkCoords(int X, int Y) {
        if (playingField[Y][X] != null) {
            if ((playingField[Y][X].equals("1")) || (playingField[Y][X].equals("2"))) {
                return false;
            }
        } else {
            return true;
        }
        return false;
    }


}
