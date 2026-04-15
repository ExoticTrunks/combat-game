package engine;

import java.util.List;

import ui.PlayerDecision;

public interface BattleUI {

    void showBattleState(BattleContext context);

    
    PlayerDecision getPlayerDecision(BattleContext context);


    void showVictory(BattleContext context);


    void showDefeat(BattleContext context);
    
    
    void printEvents(List<String> events);
}
