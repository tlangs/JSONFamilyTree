To run, execute:
    java -jar grandchildren.jar -f <filename>

If spouses have identical children lists, then execute:
    java -jar grandchildren.jar -i -f <filename>
for a minor speedup. It removes the redundant child id lists
so they are not calculated. Only use this if spouse children Id lists are identical.

To access the help screen, execute:
    java -jar grandchildren.jar -h

-----------------------------
Gradle
-----------------------------
If Gradle is installed, running 'gradle libjar' makes a jar file and puts it
in build/libs. 'gradle test' runs the tests.

If gradle is not installed, running 'gradlew libjar' and 'gradlew test' will
do the same as above, but it will first download the required files for it to work.