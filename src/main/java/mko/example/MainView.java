package mko.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.router.Route;

@Route
@PWA(name = "My Vaadin Example App", shortName = "My Vaadin Example App")
public class MainView extends VerticalLayout {
    public MainView() {
        Button button = new Button("Click me",
                event -> Notification.show("Clicked!"));
        add(button);
    }
}
