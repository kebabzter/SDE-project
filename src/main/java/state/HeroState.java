package state;

import model.Hero;

/**
 * State Pattern - State Interface
 * 
 * States have access to the Hero (context) so they can trigger their own transitions.
 * The context calls handleTurnUpdate() and the state decides when to transition.
 */
public interface HeroState {
    
    // Set context reference so state can trigger transitions
    void setContext(Hero context);
    
    void onEnter();
    void onExit();
    
    int modifyAttack(int baseAttack);
    int modifyDefense(int baseDefense);
    boolean canAct();
    
    String getStateName();
    String getStateDescription();
    
    // State manages its own lifecycle and transitions
    void handleTurnUpdate();
    
    default int getTurnDamage() {
        return 0;
    }
}
