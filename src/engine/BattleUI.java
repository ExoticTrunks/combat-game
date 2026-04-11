package engine;

import ui.PlayerDecision;

public interface BattleUI {

    void showBattleState(BattleContext context);

    
    PlayerDecision getPlayerDecision(BattleContext context);


    void showVictory(BattleContext context);


    void showDefeat(BattleContext context);
}
