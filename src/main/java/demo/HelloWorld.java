package demo;

import dagger.Component;
import dagger.Provides;
import dagger.Module;

import java.util.concurrent.Callable;

public class HelloWorld {
    public static void main(String[] args) throws Exception {
        System.out.println(DaggerHelloWorld_OurComponent.builder().build().getCallable().call());
    }

    @Component(modules = OurModule.class)
    interface OurComponent {
        Callable getCallable();
    }
}

@Module
class OurModule {
    @Provides
    Callable getHelloWorld() {
        return () -> "Hello World";
    }
}
