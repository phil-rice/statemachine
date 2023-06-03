package one.xingyi.statemachine;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StateBuilderTest {

    StateMachine<String, String> washingMachine = StateBuilder.<String, String>builder(s -> s+"_s", s -> s+"_e")
            .state("doorClosed")
            .state("doorOpen")
            .state("washing")
            .state("finished")
            .state("forTesting")
            .transition("doorClosed", "doorOpened", "doorOpen")
            .transition("doorOpen", "doorClosed", "doorClosed")
            .transition("doorClosed", "start", "washing")
            .transition("washing", "finishedWashing", "finished")
            .transition("finished", "doorOpened", "doorOpen").build();

    @Test
    void testBuild() {
        assertEquals(List.of("doorClosed", "doorOpen", "washing", "finished", "forTesting"), washingMachine.states);
        assertEquals(List.of(new EventAndState<>("doorClosed", "doorClosed")), washingMachine.outputs.get("doorOpen_s"));
        assertEquals(List.of(new EventAndState<>("finishedWashing", "finished")), washingMachine.outputs.get("washing_s"));
        assertEquals(List.of(new EventAndState<>("doorOpened", "doorOpen")), washingMachine.outputs.get("finished_s"));
        assertEquals("doorClosed_s", washingMachine.stateTypeClass.stateName("doorClosed"));
        assertEquals("finished_s", washingMachine.stateTypeClass.stateName("finished"));
        assertEquals("doorOpened_e", washingMachine.eventTypeClass.eventName("doorOpened"));
    }

    @Test
    void testTransitionHappyPath() {
        assertEquals(ResultOrError.result("doorOpen"), washingMachine.transition("doorClosed", "doorOpened"));
        assertEquals(ResultOrError.result("doorClosed"), washingMachine.transition("doorOpen", "doorClosed"));
        assertEquals(ResultOrError.result("washing"), washingMachine.transition("doorClosed", "start"));
        assertEquals(ResultOrError.result("finished"), washingMachine.transition("washing", "finishedWashing"));
        assertEquals(ResultOrError.result("doorOpen"), washingMachine.transition("finished", "doorOpened"));
    }

    @Test
    void testTransitionUnhappyPath() {
        assertEquals(ResultOrError.error("State invalidState_s not known"), washingMachine.transition("invalidState", "doorClosed"));
        assertEquals(ResultOrError.error("Event unknownEvent_e not allowed for state doorClosed_s"), washingMachine.transition("doorClosed", "unknownEvent"));
        assertEquals(ResultOrError.error("Event doorClosed_e not allowed for state forTesting_s"), washingMachine.transition("forTesting", "doorClosed"));
    }
}
