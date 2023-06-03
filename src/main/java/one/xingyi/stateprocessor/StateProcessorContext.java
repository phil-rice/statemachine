package one.xingyi.stateprocessor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import one.xingyi.statemachine.StateMachine;

import java.util.Optional;

@EqualsAndHashCode
@Getter
@ToString
@RequiredArgsConstructor
class StateProcessorContext<From, To, S, E> implements HasStateTypeClass<From, To, S>, HasEventTypeClass<From, To, E> {
    final StateMachine<S, E> stateMachine;
    final HasStateTypeClass<From, To, S> hasStateTypeClass;
    final HasEventTypeClass<From, To, E> hasEventTypeClass;
    final StateChanged<From, To, S, E> stateChanged;

    @Override
    public S state(From from, To to) {
        return hasStateTypeClass.state(from, to);
    }

    @Override
    public Optional<E> event(From from, To to) {
        return hasEventTypeClass.event(from, to);
    }


}
