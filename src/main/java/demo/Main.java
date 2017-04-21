package demo;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

interface HelloWorld {
    String getMessage();
}

public class Main {
    public static void main(String[] args) throws Exception {
        HelloWorld helloWorld = DaggerMain_HelloWorldComponent.builder().build().helloWorld();
        System.out.println(helloWorld.getMessage());
    }

    @Component(modules = HelloWorldModule.class)
    interface HelloWorldComponent {
        HelloWorld helloWorld();
    }

}

@Module
class HelloWorldModule {
    @Provides
    HelloWorld provideHelloWorld() {
        return () -> "Hello World";
    }
}
