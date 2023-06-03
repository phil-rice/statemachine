package one.xingyi.statemachine;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@ToString
@RequiredArgsConstructor
public class EventAndState<S, E> {
    final E event;
    final S state;
}
