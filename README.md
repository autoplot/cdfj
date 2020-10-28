# cdfj
Pure-Java code for reading NASA/GSFC CDF (Common Data Format) files

## Building cdfj

### Building with Ant

To build:
~~~~~
unix> git clone https://github.com/autoplot/cdfj.git
unix> cd cdfj
unix> ant -Dplatforms.JDK_8.home=/usr/local/jdk1.8 jar
~~~~~

* Note this requires platforms.JDK_8.home to be defined.  TODO: Fix this.

and cdfj.jar will be in the "dist" folder.

### Building with Maven

** Build **

Command: `./mvnw clean install`

** Displaying Spotbugs UI **

Command `./mvnw spotbugs:gui`

** Classpath Integrity Reporting **

Command: `./mvnw -Pclasspath-integrity`

Checks Execute
- [versions:display-property-updates](https://www.mojohaus.org/versions-maven-plugin/display-property-updates-mojo.html) 
- [versions:display-dependency-updates](https://www.mojohaus.org/versions-maven-plugin/display-dependency-updates-mojo.html)
- [versions:display-parent-updates](https://www.mojohaus.org/versions-maven-plugin/display-parent-updates-mojo.html)
- [versions:display-plugin-updates](https://www.mojohaus.org/versions-maven-plugin/display-plugin-updates-mojo.html)
- [dependency:analyze](https://maven.apache.org/plugins/maven-dependency-plugin/analyze-mojo.html)
- [dependency-check:check](https://jeremylong.github.io/DependencyCheck)



