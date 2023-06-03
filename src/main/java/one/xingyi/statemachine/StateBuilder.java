package one.xingyi.statemachine;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.*;
import java.util.function.Function;

@EqualsAndHashCode
@Getter
@ToString
@RequiredArgsConstructor
public class StateBuilder<S, E> {

    final List<S> states;
    final Map<String, List<EventAndState<S, E>>> outputs;
    final Function<S, String> stateName;
    final Function<E, String> eventName;

    public static <S, E> StateBuilder<S, E> builder(Function<S, String> stateName, Function<E, String> eventName) {
        return new StateBuilder<>(new ArrayList<>(), new HashMap<>(), stateName, eventName);
    }

    public StateMachine<S, E> build(AdditionalTransitionCheck<S, E> additionalTransitionCheck) {
        return new StateMachine<>(states, outputs, additionalTransitionCheck, stateName::apply, eventName::apply);
    }

    public StateMachine<S, E> build() {
        return new StateMachine<>(states, outputs, (un, us, ed) -> Optional.<Error<S>>empty(), stateName::apply, eventName::apply);
    }

    public StateBuilder<S, E> state(S s) {
        states.add(s);
        validateUniqueStateNames();
        return this;
    }

    public StateBuilder<S, E> transition(S state, E event, S newState) {
        validateState(state);
        validateState(newState);
        String stateName = this.stateName.apply(state);
        outputs.computeIfAbsent(stateName, k -> new ArrayList<>()).add(new EventAndState<>(event, newState));
        return this;
    }

    private void validateUniqueStateNames() {
        Set<String> existing = new HashSet<>();
        states.stream().map(stateName).forEach(s -> {
            if (existing.contains(s)) throw new RuntimeException("Duplicate state name " + s);
            existing.add(s);
        });
    }

    private void validateState(S s) {
        if (!states.contains(s)) throw new RuntimeException("State " + s + " not added");
    }

}



