package one.xingyi.statemachine;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IStateMachine<S, E> {
    ResultOrError<S> transition(S state, E event);
}

@EqualsAndHashCode
@Getter
@ToString
@RequiredArgsConstructor
class StateMachine<S, E> implements IStateMachine<S, E>{

    final List<S> states;
    final Map<String, List<EventAndState<S, E>>> outputs;
    final AdditionalTransitionCheck<S, E> additionalTransitionCheck;
    final StateTypeClass<S, E> stateTypeClass;
    final EventTypeClass<E> eventTypeClass;

    public ResultOrError<S> transition(S state, E event) {
        if (!states.contains(state))
            return ResultOrError.error("State " + stateTypeClass.stateName(state) + " not known");
        String stateName = stateTypeClass.stateName(state);
        List<EventAndState<S, E>> eventAndStates = outputs.get(stateName);
        if (eventAndStates == null)
            return ResultOrError.error("Event " + eventTypeClass.eventName(event) + " not allowed for state " +  stateTypeClass.stateName(state));
        Optional<EventAndState<S, E>> first = eventAndStates.stream().filter(eas -> eas.event.equals(event)).findFirst();
        if (first.isEmpty())
            return ResultOrError.error("Event " + eventTypeClass.eventName(event) + " not allowed for state " +  stateTypeClass.stateName(state));
        Optional<Error<S>> additionalError = additionalTransitionCheck.validate(state, event, first.get().state);
        if (additionalError.isPresent()) return additionalError.get();
        return ResultOrError.result(first.get().state);
    }

}
