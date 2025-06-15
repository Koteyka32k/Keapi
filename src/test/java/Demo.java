import me.koteyka32k.keapi.bus.EventBus;
import me.koteyka32k.keapi.bus.IEventBus;
import me.koteyka32k.keapi.event.StagedCancellableEvent;
import me.koteyka32k.keapi.listener.ConsumerListener;
import me.koteyka32k.keapi.listener.DirectListener;
import me.koteyka32k.keapi.priority.Priority;
import me.koteyka32k.keapi.stage.Stage;
import me.koteyka32k.keapi.subscribe.Subscribe;

/**
 * A demo which shows how to do things.
 *
 * @author Koteyka32k
 * @since 1.0
 */
public class Demo {
    public static class MessageEvent extends StagedCancellableEvent {
        private final String message;

        public MessageEvent(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        // By default, it is Stage.ON, you can change
        // the default/preferred stage to anything you want!
        @Override
        public Stage getPreferredStage() {
            return Stage.ON;
        }
    }

    // Examples of common reflection method based listeners.
    public static class DemoListener {
        // Executed first.
        @Subscribe(priority = Priority.HIGH, stage = Stage.PRE)
        private void onMessageReceive(MessageEvent event) {
            System.out.println("Received message!");
            event.cancel();
        }

        // Executed second.
        @Subscribe(priority = Priority.MEDIUM_HIGH, stage = Stage.POST, ignoreCancellation = true)
        private void onMessageReceive2(MessageEvent event) {
            System.out.println("This not going be printed! Despite this ignoring cancellation it still won't be printed because" +
                    " we do not dispatch the message event during it's POST stage!");
        }
    }

    public static void main(String[] args) {
        IEventBus bus = new EventBus();
        bus.subscribe(new DemoListener());

        // Executed third
        // Example of a direct listener.
        bus.subscribe(new DirectListener<MessageEvent>(MessageEvent.class) {
            @Override
            public void handle(MessageEvent event) {
                // By default, we do not ignore cancellation.
                System.out.println("Too bad this isn't going to be printed too!");
            }
        });

        // Executed last
        // Example of a consumer listener.
        bus.subscribe(new ConsumerListener<>(MessageEvent.class, Priority.LOWEST,  true, event -> {
            System.out.println("Here is the message: \"" + event.getMessage() + "\"");
        }));

        MessageEvent event = new MessageEvent("Hello World!");
        event.setStage(Stage.PRE);
        bus.post(event);
        event.updateStage(); // you can also do event.setStage(Stage.ON);
        bus.post(event);
    }
}