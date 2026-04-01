package engine;


public interface BattleUI {

    void showBattleState(BattleContext context);

    //TODO: update this when PlayerDecision is added
    //PlayerDecision getPlayerDecision(BattleContext context);


    void showVictory(BattleContext context);


    void showDefeat(BattleContext context);
}
