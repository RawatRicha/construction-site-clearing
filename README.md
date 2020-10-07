# construction-site-clearing

Details on the problem here: document/construction-site-clearing.pdf

•	Design Pattern- I have used the Command Design Pattern to capture the transition of the Bulldozer.

•	Approach-
The site map is stored in the form of a 2-D array and the boundary of the grid generated is indicated by array indices. 
When this project starts, the site map is loaded from the input file- inputExample.txt. The data from this file is then processed by the SiteMapProcessor.java to generate the site information. 
User inputs are parsed to generated commands in CommandParser.java and then these commands are applied on the Bulldozer in CommandProcessor.java. This helps us in fetching the path that the bulldozer follows. 
Additionally, possible violations and cost items are also checked. In the end, CostCalulator.java calculates and displays the final cost in the form of a map.

•	Assumption: At the start of the application, the bulldozer is at location (0,0). This is important when traversing the path.

Requirements for the application: 
•	Java 8
•	Gradle

Steps to follow to build and run the application:
•	To build the application: gradlew clean build
•	To run the application: gradlew run –args input/inputExample.txt

Test:
• All classes have their implementation of a JUnit Test
