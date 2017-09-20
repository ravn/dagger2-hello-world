package demo;

interface HelloWorld { // Our work unit.
    String getMessage();
}

@dagger.Module // What help does Dagger need?
class HelloWorldModule {
    @dagger.Provides
    HelloWorld provideHelloWorld() {
        return () -> "Hello World";
    }
}

public class Main {
    // What do we need Dagger to build?
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
