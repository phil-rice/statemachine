package one.xingyi.stateprocessor;

public interface HasStateTypeClass<From,To, S> {
    S state(From from, To to);
}
