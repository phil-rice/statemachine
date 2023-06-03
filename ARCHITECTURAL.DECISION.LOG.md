
# Why pick JDK 8 over the latest
This library is targeted at people that want to refactor existing legacy code. The older the JDK the 
more reusable the library is. It comes at the price (which is high) of not being able to use the latest
JDK.

# Why use typeclasses over interfaces
* Because there is a lot of existing code, and we want people to be able to use it without changing it.
* Because a lot of people use strings and integers as states and we can't change those classes

# Why don't we store the state in the state machine
In the primary project that this will be used for the state is stored in a database for each individual order. The
same state machine is used for each order. So if we stored the state in the state machine we would have to create
a new state machine for each order. This would be a lot of overhead!

# Why is the state processor a higher order function
This is targeting at refactoring existing code. We are adopting a principle of separating concerns. This
separation is handled using higher order functions. The bizlogic is already being refactored to be a function 
 'from' -> 'to'. The state handling 'is just another concern'

# Why are fields final and visible
Really? You are asking this question? Every field has a getter. It's put there using an annotation.
This has the minor side effect of being more efficient (no method call) but the main reason is that
the code reads better with them as fields. It is a myth to say 'should have getters for fields'. Perhaps
one project in a hundred will get once or twice a benefit from it, at the cost of all projects having more code
and less readable code.

# Why are no methods or fields private
Using the modifier `private` is a code smell. Usually a bad one. It is a sign that you have not written a
test for the code. 

Worse: you have made the code untestable.  Once something is private it cannot be tested. 

One of the original reasons for the fetish of 'everything should be private' was the SOLID principle of 
'open for extension/closed for modification'. This is a good principle! But since the fields and methods are
private we meet that principle.

The goal of information hiding is met by the use of interfaces instead of implementations. Also note that 
package level protection lets us test, but doesn't let other packages use the code. 

# Why is the state machine builder not immutable?
OK I feel bad about this. But immutable code is not idiomatic or easy in Java. And there is
only pain and the possibility of bugs with immutability here with no benefit. Given the chronic
limitations of java in this area it was simpler and probably less buggy to make a standard fluent builder.