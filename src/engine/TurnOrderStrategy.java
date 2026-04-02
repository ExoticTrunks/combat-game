package engine;

import domain.Combatant;
import java.util.List;

//Takes in a list of all combatants and returns them sorted in the order they should act. 
//This acts as a DIP allowing for different ordering systems than the one the assignment specifies
public interface TurnOrderStrategy {
    List<Combatant> determineTurnOrder(List<Combatant> combatants);
}
