package one.xingyi.statemachine;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import one.xingyi.stateprocessor.StateChanged;
import one.xingyi.stateprocessor.StateProcessorContext;

import java.util.Optional;

public interface StateFixture {
    static StateMachine<String, String> makeWashingMachine(AdditionalTransitionCheck<String, String> additionalCheck) {
        return StateBuilder.<String, String>builder(s -> s + "_s", s -> s + "_e")
                .state("doorClosed")
                .state("doorOpen")
                .state("washing")
                .state("finished")
                .state("forTesting")
                .transition("doorClosed", "doorOpened", "doorOpen")
                .transition("doorOpen", "doorClosed", "doorClosed")
                .transition("doorClosed", "start", "washing")
                .transition("washing", "finishedWashing", "finished")
                .transition("finished", "doorOpened", "doorOpen").build(additionalCheck);
    }

    StateMachine<String, String> washingMachine = makeWashingMachine((un, us, ed) -> Optional.empty());

    @EqualsAndHashCode
    @Getter
    @ToString
    @RequiredArgsConstructor
    static class ToForTest {
        final String state;
        final Optional<String> event;
    }

    static StateProcessorContext<String, ToForTest, String, String> makeContext(StateChanged<String, ToForTest, String, String> stateChanged) {
        return new StateProcessorContext<>(washingMachine,
                (from, to) -> to.state,
                (from, to) -> to.event,
                stateChanged);
    }

    StateProcessorContext<String, ToForTest, String, String> context = makeContext((un, u, s, e, d) -> {
    });


}
