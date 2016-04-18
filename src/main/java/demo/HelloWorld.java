package demo;

import dagger.Component;
import dagger.Provides;
import dagger.Module;

interface MessageGetter {
    String getMessage();
}

public class HelloWorld {
    public static void main(String[] args) throws Exception {
        HelloWorldComponent component = DaggerHelloWorld_HelloWorldComponent.builder().build();
        MessageGetter messageGetter = component.messageGetter();
        System.out.println(messageGetter.getMessage());
    }

    @Component(modules = HelloWorldModule.class)
    interface HelloWorldComponent {
        MessageGetter messageGetter();
    }
}

@Module
class HelloWorldModule {
    @Provides
    MessageGetter provideMessageGetter() {
        return () -> "Hello World";
    }
}
