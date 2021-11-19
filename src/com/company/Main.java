package com.company;

import java.util.Random;

public class Main {

    public static int bossHealth = 1200;
    public static int bossDamage = 140;
    public static String bossDefence = "";
    public static int[] heroesHealth = {270, 280, 240, 250, 450, 150, 200, 200};
    public static int[] heroesDamage = {20, 15, 25, 0, 8, 15, 20, 35};
    public static String[] heroesAttackType = {"Physical",
            "Magical", "Kinetic", "Medic", "Golemepta", "Vezunchik", "Berserk", "Thor"};
    public static int round_number = 0;
    public static Random random = new Random();

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(
                heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
        System.out.println("Boss chose " + bossDefence);
    }

    public static void round() {
        round_number++;
        chooseBossDefence();
        if (bossHealth > 0) { // на всякий случай
            bossHits();
        }
        heroesHit();
        Medichealed();
        GolemZashita();
        Vezunchik();
        Berserk();
        Thor();
        printStatistics();
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
       /* if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0
                && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(11); //0,1,2,3,4,5,6,7,8,9
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println("Critical damage: " + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static void Medichealed() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (i == 3) {
                continue;
            }

            if (heroesHealth[i] > 0 && heroesHealth[i] < 100 && heroesHealth[3] > 0) {
                heroesHealth[i] += 50;
                System.out.println("Medic hill ***" + heroesAttackType[i]);
                break;
            }
        }
    }


    public static void GolemZashita() {
        int UronBoss = bossDamage / 5;
        int AliveHeroes = 0;
        if (heroesHealth[4] > 0) {
            for (int i = 0; i < heroesDamage.length; i++) {
                if (i == 4) {
                    continue;
                } else if (heroesHealth[i] > 0)
                    AliveHeroes++;
                heroesHealth[i] += UronBoss;
            }
        }
        heroesHealth[4] -= UronBoss * AliveHeroes;
        System.out.println("Golem получает урон = " + UronBoss * AliveHeroes);
    }

    public static void Vezunchik() {
        Random random = new Random();
        boolean povezlo = random.nextBoolean();
        if (heroesHealth[5] > 0) {
            if (povezlo) heroesHealth[5] += bossDamage - 10;
            System.out.println("Vezunchik Увернулся от удара: " + povezlo);
        }
    }

    public static void Berserk() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[6] > 0) {
                heroesHealth[6] -= bossDamage * 1 / 10;
                heroesDamage[6] = heroesDamage[6] + bossDamage * 1 / 10;
                System.out.println("Berserk has just blocked and gained bonus damage by blocking Boss' one");
                break;
            }
        }
    }


    public static void Thor() {
        Random random = new Random();
        boolean Oglushil = random.nextBoolean();
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[7] > 0) ;
            {
                if (Oglushil) {
                    bossDamage = 0;

                    System.out.println("Thor oglushil: " + Oglushil);
                    break;
                } else {
                    bossDamage = 50;
                    break;
                }
            }
        }
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;

                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println(round_number + " ROUND ______________");
        System.out.println("Boss health: " + bossHealth
                + " (" + bossDamage + ")");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i]
                    + " health: " + heroesHealth[i]
                    + " (" + heroesDamage[i] + ")");
        }
        System.out.println("____________________");
    }
}

