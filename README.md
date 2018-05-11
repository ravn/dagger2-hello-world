/*

Dagger 2 Hello World
===

[//]: # (Important:  As an experiment Main.java is also a valid markdown file copied unmodified to README.md, so only edit Main.java)

This project is a single file Hello World Dagger-2 Maven project for
Java 8 and later.


Getting up and running:
---

    mvn -q clean package exec:java

outputs "Hello World".

[Project works with Netbeans 8.2, Intellij 2017 and Eclipse 4.6.2 with m2e_apt.](/TROUBLESHOOTING.md)


Background:
---


This project demonstrates the minimal amount of work
required to create a small command line application, as the official documentation at
https://google.github.io/dagger/users-guide is targetted towards more
experienced programmers. In other words, this is _not_ a full Dagger tutorial (which unfortunately
is badly needed).



The major difference between Dagger and most other Dependency Injection frameworks
is that Dagger does all its heavy lifting in the compilation phase instead of at runtime.
This mean that Dagger can report problems as compilation errors instead of failing
at startup time.

This file contains is a minimal but fully functional Dagger enabled
Maven command line application.   There are several parts:

1. An interface belonging to our code that Dagger can created injected instances of.

1. A "module" describing how to create those objects Dagger cannot create by itself.

1. A "component" is an interface with a method for getting instances of our interface
   annotated with the modules Dagger needs.  During compilation
   Dagger figures out how to wire things together and writes
   the Java source files needed.

1. A main method showing how to wire things together.

Note:  It is also a proof-of-concept that using markdown in comments can be used to present complex ideas
   in real live code (much like Literate Programming) for github projects.

Please report back on code as well as documentation issues!


## Main.java

First we define the `HelloWorld` interface which is what our code
is concerned about.  All the rest of the code is for helping Dagger to
create an object implementing this interface so we can invoke its
methods with all injections in place.  This interface only contains a
single method but may contain many.

```
/* HelloWorld belongs to our "own" code. */
package demo;

interface HelloWorld { // Our work unit.
    String getMessage();
}

/*
```

In order to fulfill dependencies Dagger needs to have object instances given their classes.

Dagger can do this on its own if the class has exactly one constructor annotated with `@Inject`
(where the parameters are also considered dependencies so Dagger provides them to the constructor).

If this for any reason is not the case, Dagger needs help in the form of modules which contain "provider methods"
(as they are annotated with `@dagger.Provides`).

* Provider methods are responsible for returning an instance of the return value (which is frequently an interface).
* Provider methods may take parameters - dagger treats these as dependencies and provide them when the method is called.
* If multiple providers return the same type, use `@Named(...)` to separate them.  This is very common for configuration strings.
* It is good practice to provide good javadoc and parameter value checks in the provider method.
* As of the time of writing I have not yet figured out how to correctly handle Singletons.

```
/* Help Dagger make a HelloWorld instance */

@dagger.Module
class HelloWorldModule {
    @dagger.Provides
    HelloWorld provideHelloWorld() {
        return () -> "Hello World";
    }
}

/*
```

The `@Component` interface is the full description of:
1. what do we want Dagger to do in form of interface methods?
1. how to do the things Dagger cannot figure out on its own by listing the modules to consider as an annotation?

Dagger then at compile time determines the dependency graph and
puts this in stone by writing the source of a corresponding
class which implements this interface
which our code can then invoke at runtime.

Only the modules listed for the component are considered for this component, even if others
exist in the classpath.

The `main(...)` method does:

1. Invokes the class written by Dagger during compilation implementing our dependency graph to get a "magic" component.
1. Invoke the `helloWorld()` method on the magic component to get an object implementing the `HelloWorld` interface.  Our configuration specified
   that we wanted an instance where `getMessage()` returned "Hello World".
1. Print out the value of `helloWorld.getMessage()`.  As expected this isn't null but "Hello World".


```
/* HelloWorldComponent lists needed modules and has methods returning what we need */

public class Main {
    @dagger.Component(modules = HelloWorldModule.class)
    interface HelloWorldComponent {
        HelloWorld helloWorld();
    }

    public static void main(String[] args) throws Exception {
        // If compilation fails, see README.md
        HelloWorldComponent daggerGeneratedComponent = DaggerMain_HelloWorldComponent.builder().build();

        HelloWorld helloWorld = daggerGeneratedComponent.helloWorld();
        System.out.println(helloWorld.getMessage());
    }
}
