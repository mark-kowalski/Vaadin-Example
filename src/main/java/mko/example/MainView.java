package mko.example;

import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.router.Route;

@Route
@Push
@PWA(name = "My Vaadin Example App", shortName = "My Vaadin Example App")
public class MainView extends VerticalLayout {

    private UpdateThread thread;

    public MainView() {
        Button button = new Button("Click me",
                event -> {
                    Notification.show("Clicked!");
                    runAsyncUpdate();
                });
        add(button);
    }

    private void runAsyncUpdate() {
        if (getUI().isPresent()) {
            Span updateSpan = new Span("Waiting for updates");
            add(updateSpan);
            thread = new UpdateThread(getUI().get(), updateSpan);
            thread.start();
        }
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
    }

    private static class UpdateThread extends Thread {
        private final UI ui;
        private final Span view;

        private int count = 0;

        public UpdateThread(UI ui, Span view) {
            this.ui = ui;
            this.view = view;
        }

        @Override
        public void run() {
            try {
                while (count < 10) {
                    Thread.sleep(1000);
                    String message = "This is update " + count++;
                    ui.access(() -> {
                        view.setText(message);
                    });
                }

                ui.access(() -> {
                    view.setText("Done updating");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}