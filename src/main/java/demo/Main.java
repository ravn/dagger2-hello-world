package demo;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

interface HelloWorld {
    String getMessage();
}

public class Main {
    public static void main(String[] args) throws Exception {
    	// If compilation fails, see README.md
        HelloWorldComponent daggerGeneratedComponent = DaggerMain_HelloWorldComponent.builder().build();
		
        HelloWorld helloWorld = daggerGeneratedComponent.helloWorld();
        System.out.println(helloWorld.getMessage());
    }

    // What do we need?
    @Component(modules = HelloWorldModule.class)
    interface HelloWorldComponent {
        HelloWorld helloWorld();
    }
}

@Module // How do we make what is needed? (Except classes with @Inject constructor)
class HelloWorldModule {
    @Provides
    HelloWorld provideHelloWorld() {
        return () -> "Hello World";
    }
}
