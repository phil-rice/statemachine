# Statemachine 

This has a number of parts
* A state machine builder that allows us to describe a state machine in terms of states and events
  * This uses the type class pattern, so you can use any java object as the states or the events
  * Which means you can use existing code without having to change those classes
* A state machine 
  * This does not store the current state. Given a state and an event
      * It works out if this is a legal transition, and if so returns the next state, if not a suitable error message
* A state machine processor
  * This allows us to add a state machine as a higher order function
    * Given some bizness logic which is modeled as a function 'from' -> 'to'
        * The bizness logic is called
        * The 'from' and 'to' are scanned. The current state extracted. Any event (optional) extracted
        * If there was an event the statemachine is called to see if this is a legal transition
        * A listener is informed (to do sideeffects...) about the legal or illegal transition

This is targeted at a world where there are a lot of state machines. For examples orders that need processing.
Each order has a state. There is existing business logic that processes the orders

This pattern allows us to extra the business logic about the state machine that is smeared throughout the code and 
centralise it. A clear separation of concerns


