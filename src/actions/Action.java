package action;
import model.Combatant; //yet to be implimented but this is the rough idea
import java.util.List;

public interface Action {
    String execute(Combatant actor, List<Combatant> targets); // try this first and then see if it holds up later on
    String getName();
}
