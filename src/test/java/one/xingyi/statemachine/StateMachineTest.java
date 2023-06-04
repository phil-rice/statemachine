package one.xingyi.statemachine;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StateMachineTest implements StateFixture {

    @Test
    public void testTransitionHappyPath() {
        assertEquals(ResultOrError.result("doorOpen"), washingMachine.transition("doorClosed", "doorOpened"));
        assertEquals(ResultOrError.result("doorClosed"), washingMachine.transition("doorOpen", "doorClosed"));
        assertEquals(ResultOrError.result("washing"), washingMachine.transition("doorClosed", "start"));
        assertEquals(ResultOrError.result("finished"), washingMachine.transition("washing", "finishedWashing"));
        assertEquals(ResultOrError.result("doorOpen"), washingMachine.transition("finished", "doorOpened"));
    }

    @Test
    public void testAdditionalTransitionCheck() {
        StateMachine<String, String> sm = StateFixture.makeWashingMachine((oldS, e, newS) -> {
            assertEquals("doorClosed", oldS);
            assertEquals("doorOpened", e);
            assertEquals("doorOpen", newS);
            return Optional.of(ResultOrError.error("Transition stopped"));
        });
        assertEquals(ResultOrError.error("Transition stopped"), sm.transition("doorClosed", "doorOpened"));
    }

    @Test
    public void testTransitionUnhappyPath() {
        assertEquals(ResultOrError.error("State invalidState_s not known"), washingMachine.transition("invalidState", "doorClosed"));
        assertEquals(ResultOrError.error("Event unknownEvent_e not allowed for state doorClosed_s"), washingMachine.transition("doorClosed", "unknownEvent"));
        assertEquals(ResultOrError.error("Event doorClosed_e not allowed for state forTesting_s"), washingMachine.transition("forTesting", "doorClosed"));
    }
}