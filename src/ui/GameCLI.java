package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import actions.BasicAttackAction;
import actions.BattleAction;
import actions.DefendAction;
import actions.UseItemAction;
import domain.AbstractCombatant;
import domain.Combatant;
import domain.Enemy;
import domain.PlayerCharacter;
import engine.BasicEnemyActionStrategy;
import engine.BattleContext;
import engine.BattleEngine;
import engine.BattleUI;
import engine.SpeedTurnOrderStrategy;
import items.Inventory;
import items.Item;
import items.Potion;
import items.PowerStone;
import items.SmokeBomb;
import level.LevelConfig;
import level.LevelFactory;

public class GameCLI implements BattleUI {
    private final Scanner scanner = new Scanner(System.in);
    private int lastPlayerChoice = -1;
    private int lastItem1Choice = -1;
    private int lastItem2Choice = -1;
    private int lastLevelChoice = -1;

    public void run() {
        boolean running = true;
        while (running) {
            printLoadingScreen();
            Inventory inventory = chooseItems();
            PlayerCharacter player = choosePlayer(inventory);
            LevelConfig level = chooseLevel();

            BattleEngine engine = new BattleEngine(
                    new SpeedTurnOrderStrategy(),
                    new BasicEnemyActionStrategy(),
                    this
            );
            engine.runBattle(player, level);

            int postChoice = postGameMenu();
            switch (postChoice) {
                case 1 -> {
                    replayLoop(level);
                }
                case 2 -> {
                    // New game — loop continues
                }
                case 3 -> running = false;
            }
        }
        System.out.println("Thanks for playing Turn-Based Combat Arena.");
    }

    // Replay system
    private void replayLoop(LevelConfig level) {
        boolean replaying = true;
        while (replaying) {
            Inventory inv = buildInventory(lastItem1Choice, lastItem2Choice);
            PlayerCharacter player = buildPlayer(lastPlayerChoice, inv);

            BattleEngine engine = new BattleEngine(
                    new SpeedTurnOrderStrategy(),
                    new BasicEnemyActionStrategy(),
                    this
            );
            engine.runBattle(player, level);

            int postChoice = postGameMenu();
            switch (postChoice) {
                case 1 -> { /* replay again */ }
                case 2 -> replaying = false; // back to new game
                case 3 -> {
                    replaying = false;
                    System.out.println("Thanks for playing Turn-Based Combat Arena.");
                    System.exit(0);
                }
            }
        }
    }

    // Loading Screen

    public void printLoadingScreen() {
        System.out.println("========================================");
        System.out.println("     SC2002 Turn-Based Combat Arena     ");
        System.out.println("========================================");
        System.out.println();
        System.out.println("Players:");
        System.out.println("1) Warrior  HP 260 ATK 40 DEF 20 SPD 30 | Special: Shield Bash");
        System.out.println("2) Wizard   HP 200 ATK 50 DEF 10 SPD 20 | Special: Arcane Blast");
        System.out.println();
        System.out.println("Enemies:");
        System.out.println("- Goblin  HP 55  ATK 35 DEF 15 SPD 25");
        System.out.println("- Wolf    HP 40  ATK 45 DEF 5  SPD 35");
        System.out.println();
        System.out.println("Items:");
        System.out.println("1) Potion      - Heal 100 HP");
        System.out.println("2) Power Stone - Trigger special skill without changing cooldown");
        System.out.println("3) Smoke Bomb  - Enemy attacks deal 0 damage this and next turn");
        System.out.println();
    }

    // Setup choices

    private Inventory chooseItems() {
        System.out.println("Choose 2 items (duplicates allowed):");
        lastItem1Choice = promptInt("  Item 1 (1 Potion, 2 Power Stone, 3 Smoke Bomb): ", 1, 3);
        lastItem2Choice = promptInt("  Item 2 (1 Potion, 2 Power Stone, 3 Smoke Bomb): ", 1, 3);
        return buildInventory(lastItem1Choice, lastItem2Choice);
    }

    private Inventory buildInventory(int choice1, int choice2) {
        Inventory inventory = new Inventory();
        inventory.addItem(createItem(choice1));
        inventory.addItem(createItem(choice2));
        return inventory;
    }

    private Item createItem(int choice) {
        return switch (choice) {
            case 1 -> new Potion();
            case 2 -> new PowerStone();
            case 3 -> new SmokeBomb();
            default -> throw new IllegalStateException("Unexpected item choice: " + choice);
        };
    }

    private PlayerCharacter choosePlayer(Inventory inventory) {
        lastPlayerChoice = promptInt("Choose player (1 Warrior, 2 Wizard): ", 1, 2);
        return buildPlayer(lastPlayerChoice, inventory);
    }

    private PlayerCharacter buildPlayer(int choice, Inventory inventory) {
        return choice == 1
                ? new domain.Warrior(inventory)
                : new domain.Wizard(inventory);
    }

    private LevelConfig chooseLevel() {
        System.out.println();
        System.out.println("Difficulty Levels:");
        System.out.println("1) Easy   - Initial Spawn: 3 Goblins");
        System.out.println("2) Medium - Initial Spawn: 1 Goblin + 1 Wolf | Backup: 2 Wolves");
        System.out.println("3) Hard   - Initial Spawn: 2 Goblins | Backup: 1 Goblin + 2 Wolves");
        lastLevelChoice = promptInt("Select level (1-3): ", 1, 3);
        return buildLevel(lastLevelChoice);
    }

    private LevelConfig buildLevel(int choice) {
        return switch (choice) {
            case 1 -> LevelFactory.easy();
            case 2 -> LevelFactory.medium();
            case 3 -> LevelFactory.hard();
            default -> throw new IllegalStateException("Unexpected level choice: " + choice);
        };
    }

    // BattleUI implementation

    @Override
    public PlayerDecision getPlayerDecision(BattleContext context) {
        PlayerCharacter player = context.getPlayer();

        while (true) {
            System.out.println();
            System.out.println("--- " + player.getName() + "'s Turn ---");
            showBattleState(context);
            System.out.println();
            System.out.println("Choose action:");
            System.out.println("1) BasicAttack");
            System.out.println("2) Defend");
            System.out.println("3) Item" + (player.getInventory().isEmpty() ? " [None left]" : ""));
            System.out.println("4) SpecialSkill (" + player.getSpecialSkillName() + ")"
                    + (player.getSpecialCooldownRemaining() > 0
                    ? " [Cooldown: " + player.getSpecialCooldownRemaining() + "]"
                    : " [Ready]"));
            int choice = promptInt("Action: ", 1, 4);

            switch (choice) {
                case 1 -> {
                    return new PlayerDecision(new BasicAttackAction(),
                            chooseTarget(context.getAliveEnemies()));
                }
                case 2 -> {
                    return new PlayerDecision(new DefendAction(), null);
                }
                case 3 -> {
                    if (player.getInventory().isEmpty()) {
                        System.out.println("No items left! Choose another action.");
                        continue;
                    }
                    return chooseItemDecision(context, player);
                }
                case 4 -> {
                    if (player.getSpecialCooldownRemaining() > 0) {
                        System.out.println("Special skill is on cooldown ("
                                + player.getSpecialCooldownRemaining() + " turns). Choose another action.");
                        continue;
                    }
                    BattleAction action = player.createSpecialSkillAction(false);
                    Combatant target = action.requiresTarget()
                            ? chooseTarget(context.getAliveEnemies()) : null;
                    return new PlayerDecision(action, target);
                }
            }
        }
    }

    private PlayerDecision chooseItemDecision(BattleContext context, PlayerCharacter player) {
        Inventory inventory = player.getInventory();

        System.out.println("Items:");
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            System.out.println("  " + (i + 1) + ") " + item.getName() + " - " + item.getDescription());
        }

        int idx = promptInt("Choose item: ", 1, inventory.size()) - 1;
        Item item = inventory.remove(idx);

        Combatant target = null;
        boolean needsTarget = item.requiresTarget();
        if (item instanceof PowerStone) {
            needsTarget = player.createSpecialSkillAction(true).requiresTarget();
        }
        if (needsTarget) {
            target = chooseTarget(context.getAliveEnemies());
        }

        return new PlayerDecision(new UseItemAction(item), target);
    }

    private Combatant chooseTarget(List<Enemy> enemies) {
        List<Enemy> alive = new ArrayList<>(enemies);
        if (alive.isEmpty()) {
            return null;
        }

        System.out.println("Targets:");
        for (int i = 0; i < alive.size(); i++) {
            Enemy enemy = alive.get(i);
            System.out.println("  " + (i + 1) + ") " + enemy.getName()
                    + " HP " + enemy.getHp() + "/" + enemy.getMaxHp()
                    + " ATK " + enemy.getAttack()
                    + " DEF " + enemy.getDefense()
                    + " SPD " + enemy.getSpeed());
        }

        int idx = promptInt("Choose target: ", 1, alive.size()) - 1;
        return alive.get(idx);
    }

    // Display methods

    @Override
    public void showBattleState(BattleContext context) {
        PlayerCharacter player = context.getPlayer();
        AbstractCombatant p = (AbstractCombatant) player;

        System.out.println("End of Round " + context.getRoundNumber());
        System.out.println(player.getName()
                + " | HP " + player.getHp() + "/" + player.getMaxHp()
                + " | ATK " + player.getAttack()
                + " | DEF " + player.getDefense()
                + " | SPD " + player.getSpeed()
                + " | Status: " + p.statusSummary()
                + " | Cooldown: " + player.getSpecialCooldownRemaining()
                + " | Items: " + player.getInventory().summary());

        System.out.println("Enemies:");
        for (Enemy enemy : context.getEnemies()) {
            AbstractCombatant e = (AbstractCombatant) enemy;
            String status = enemy.isAlive() ? "ALIVE" : "ELIMINATED";
            System.out.println("  - " + enemy.getName()
                    + " | HP " + enemy.getHp() + "/" + enemy.getMaxHp()
                    + " | ATK " + enemy.getAttack()
                    + " | DEF " + enemy.getDefense()
                    + " | SPD " + enemy.getSpeed()
                    + " | " + status
                    + " | Status: " + e.statusSummary());
        }
    }

    @Override
    public void printEvents(List<String> events) {
        System.out.println("----------------------------------------------");
        for (String event : events) {
            System.out.println(event);
        }
    }

    @Override
    public void showVictory(BattleContext context) {
        PlayerCharacter player = context.getPlayer();
        System.out.println();
        System.out.println("=============================================");
        System.out.println("              PLAYER VICTORY!                ");
        System.out.println("=============================================");
        System.out.println("Congratulations, you have defeated all your enemies.");
        System.out.println("Remaining HP: " + player.getHp() + "/" + player.getMaxHp()
                + " | Total Rounds: " + context.getRoundNumber()
                + " | Remaining Items: " + player.getInventory().summary());
        System.out.println();
    }

    @Override
    public void showDefeat(BattleContext context) {
        System.out.println();
        System.out.println("=============================================");
        System.out.println("              PLAYER DEFEAT                  ");
        System.out.println("=============================================");
        System.out.println("Don't give up, try again!");
        System.out.println("Enemies remaining: " + context.getAliveEnemies().size()
                + " | Total Rounds Survived: " + context.getRoundNumber());
        System.out.println();
    }

    // Post-game menu

    private int postGameMenu() {
        System.out.println("What would you like to do?");
        System.out.println("1) Replay with same settings");
        System.out.println("2) Start a new game");
        System.out.println("3) Exit");
        return promptInt("Choose: ", 1, 3);
    }

    // Input utility

    private int promptInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
        }
    }
}
