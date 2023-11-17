# hierarchy

## Requirement
* Maven
* JDK 1.8

## Run unit test
* To run unit test, please run the following command in project folder
  * `mvn test`
* It's also possible to run unit test in your favorite IDE by running it through `com.almworks.structure.cloud.commons.util.FilterTest`

## Filter class
* Class `com.almworks.structure.cloud.commons.util.Filter`
* Step to travel through the hierarchy and filter
  1. Start with node index 0
  2. Find sibling of current node and store in sibling stack
  3. Test current node against predicate
  4. If test passes, move to node with next index
  5. If test fails, move to next sibling in sibling stack
  6. Go back to step #2 until travel to the end of hierarchy
