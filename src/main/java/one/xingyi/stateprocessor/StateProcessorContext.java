package one.xingyi.stateprocessor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import one.xingyi.statemachine.IStateMachine;

import java.util.Optional;

@EqualsAndHashCode
@Getter
@ToString
@RequiredArgsConstructor
public class StateProcessorContext<From, To, S, E> implements HasStateTypeClass<From, To, S>, HasEventTypeClass<From, To, E> {
    final IStateMachine<S, E> stateMachine;
    final HasStateTypeClass<From, To, S> hasStateTypeClass;
    final HasEventTypeClass<From, To, E> hasEventTypeClass;
    final StateChanged<From, To, S, E> stateChanged;

    @Override
    public S findStateIn(From from, To to) {
        return hasStateTypeClass.findStateIn(from, to);
    }

    @Override
    public Optional<E> findEventIn(From from, To to) {
        return hasEventTypeClass.findEventIn(from, to);
    }


}
