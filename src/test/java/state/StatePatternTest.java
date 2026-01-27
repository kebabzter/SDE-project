package state;

import model.Hero;
import model.HeroType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the State pattern implementation.
 * Verifies structure and behaviour match Refactoring.Guru:
 * https://refactoring.guru/design-patterns/state
 *
 * Structure: Context (stores state, delegates, exposes setter), State interface,
 * Concrete States (backreference to context, initiate transitions).
 */
@DisplayName("State Pattern")
class StatePatternTest {

    private Hero context;

    @BeforeEach
    void setUp() {
        context = new Hero("TestHero", HeroType.KNIGHT);
    }

    // -------------------------------------------------------------------------
    // 1. CONTEXT – stores state, delegates, exposes setter
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("1. Context (Hero)")
    class ContextTests {

        @Test
        @DisplayName("Context stores reference to current state")
        void contextStoresCurrentState() {
            assertNotNull(context.getCurrentState());
            assertTrue(context.getCurrentState() instanceof HeroState);
        }

        @Test
        @DisplayName("Context delegates getAttack to state")
        void contextDelegatesAttack() {
            int attack = context.getAttack();
            assertTrue(attack > 0);
            context.receivePoison();
            assertTrue(context.getAttack() < attack);
        }

        @Test
        @DisplayName("Context delegates getDefense to state")
        void contextDelegatesDefense() {
            int def = context.getDefense();
            context.receiveStun();
            assertTrue(context.getDefense() < def);
        }

        @Test
        @DisplayName("Context delegates canAct to state")
        void contextDelegatesCanAct() {
            assertTrue(context.canAct());
            context.receiveStun();
            assertFalse(context.canAct());
        }

        @Test
        @DisplayName("Context delegates updateState to state (handleTurnUpdate)")
        void contextDelegatesUpdateState() {
            context.receivePoison();
            assertTrue(context.getCurrentState() instanceof PoisonedState);
            for (int i = 0; i < 3; i++) {
                context.updateState();
            }
            assertTrue(context.getCurrentState() instanceof NormalState);
        }

        @Test
        @DisplayName("Context exposes setter for state (setState)")
        void contextExposesStateSetter() {
            HeroState poisoned = new PoisonedState(context);
            context.setState(poisoned);
            assertSame(poisoned, context.getCurrentState());
            assertTrue(context.getCurrentState() instanceof PoisonedState);
        }
    }

    // -------------------------------------------------------------------------
    // 2. STATE INTERFACE – declares state-specific methods
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("2. State interface")
    class StateInterfaceTests {

        @Test
        @DisplayName("HeroState is an interface")
        void heroStateIsInterface() {
            assertTrue(HeroState.class.isInterface());
        }

        @Test
        @DisplayName("All concrete states implement HeroState")
        void concreteStatesImplementHeroState() {
            assertTrue(HeroState.class.isAssignableFrom(NormalState.class));
            assertTrue(HeroState.class.isAssignableFrom(PoisonedState.class));
            assertTrue(HeroState.class.isAssignableFrom(StunnedState.class));
        }

        @Test
        @DisplayName("Context works with state via interface only")
        void contextWorksViaInterface() {
            HeroState s = context.getCurrentState();
            assertEquals("Normal", s.getStateName());
            assertTrue(s.canAct());
            assertEquals(10, s.modifyAttack(10));
            assertEquals(5, s.modifyDefense(5));
        }
    }

    // -------------------------------------------------------------------------
    // 3. CONCRETE STATES – backreference, initiate transitions
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("3. Concrete States")
    class ConcreteStateTests {

        @Test
        @DisplayName("States store backreference to context (getContext)")
        void statesStoreBackreferenceToContext() {
            HeroState s = context.getCurrentState();
            assertNotNull(s.getContext());
            assertSame(context, s.getContext());
        }

        @Test
        @DisplayName("NormalState has correct behavior (canAct, modifyAttack)")
        void normalStateBehavior() {
            assertTrue(context.getCurrentState() instanceof NormalState);
            assertTrue(context.canAct());
            assertEquals(context.getBaseAttack() + 5, context.getAttack());
        }

        @Test
        @DisplayName("PoisonedState reduces attack and has getTurnDamage")
        void poisonedStateBehavior() {
            context.setState(new PoisonedState(context));
            assertTrue(context.canAct());
            assertTrue(context.getAttack() < context.getBaseAttack() + 5);
            assertTrue(context.getCurrentState().getTurnDamage() > 0);
        }

        @Test
        @DisplayName("StunnedState prevents action and halves defense")
        void stunnedStateBehavior() {
            context.setState(new StunnedState(context));
            assertFalse(context.canAct());
            assertTrue(context.getDefense() < 10);
        }
    }

    // -------------------------------------------------------------------------
    // 4. STATES INITIATE TRANSITIONS (state-guided)
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("4. States initiate transitions")
    class StateTransitionTests {

        @Test
        @DisplayName("NormalState initiates transition to PoisonedState on receivePoison")
        void normalToPoisoned() {
            assertTrue(context.getCurrentState() instanceof NormalState);
            context.receivePoison();
            assertTrue(context.getCurrentState() instanceof PoisonedState);
        }

        @Test
        @DisplayName("NormalState initiates transition to StunnedState on receiveStun")
        void normalToStunned() {
            assertTrue(context.getCurrentState() instanceof NormalState);
            context.receiveStun();
            assertTrue(context.getCurrentState() instanceof StunnedState);
        }

        @Test
        @DisplayName("PoisonedState initiates transition to NormalState when duration expires")
        void poisonedToNormal() {
            context.setState(new PoisonedState(context));
            assertTrue(context.getCurrentState() instanceof PoisonedState);
            for (int i = 0; i < 3; i++) {
                context.updateState();
            }
            assertTrue(context.getCurrentState() instanceof NormalState);
        }

        @Test
        @DisplayName("StunnedState initiates transition to NormalState when duration expires")
        void stunnedToNormal() {
            context.setState(new StunnedState(context));
            assertTrue(context.getCurrentState() instanceof StunnedState);
            for (int i = 0; i < 2; i++) {
                context.updateState();
            }
            assertTrue(context.getCurrentState() instanceof NormalState);
        }

        @Test
        @DisplayName("State uses context reference to call setState (transition)")
        void stateCallsContextSetState() {
            HeroState normal = context.getCurrentState();
            assertSame(context, normal.getContext());
            context.receivePoison();
            assertTrue(context.getCurrentState() instanceof PoisonedState);
        }
    }

    // -------------------------------------------------------------------------
    // 5. BOTH CONTEXT AND STATES CAN SET NEXT STATE
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("5. Context and states set next state")
    class SetStateTests {

        @Test
        @DisplayName("Context can set state directly (setState)")
        void contextCanSetState() {
            context.setState(new StunnedState(context));
            assertTrue(context.getCurrentState() instanceof StunnedState);
        }

        @Test
        @DisplayName("State initiates transition via context.setState")
        void stateInitiatesViaContextSetState() {
            context.receivePoison();
            assertTrue(context.getCurrentState() instanceof PoisonedState);
            for (int i = 0; i < 3; i++) {
                context.updateState();
            }
            assertTrue(context.getCurrentState() instanceof NormalState);
        }
    }

    // -------------------------------------------------------------------------
    // 6. BEHAVIOUR CHANGES WITH STATE
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("6. Behavior changes with state")
    class BehaviorChangesTests {

        @Test
        @DisplayName("Hero behaves differently per state (attack)")
        void behaviorDiffersByState() {
            int normalAttack = context.getAttack();
            context.receivePoison();
            int poisonedAttack = context.getAttack();
            assertTrue(poisonedAttack < normalAttack);
        }

        @Test
        @DisplayName("Hero behaves differently per state (canAct)")
        void canActDiffersByState() {
            assertTrue(context.canAct());
            context.receiveStun();
            assertFalse(context.canAct());
            for (int i = 0; i < 2; i++) {
                context.updateState();
            }
            assertTrue(context.canAct());
        }
    }
}
