package ch.fortylove.presentation.components;


import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@AnonymousAllowed
@Component
public class LegalNoticeComponent extends VerticalLayout {


    public LegalNoticeComponent() {

        add(new NativeLabel("Jonas Cahenzli"));
        add(new NativeLabel("Hünenbergstrasse 19"));
        add(new NativeLabel("6006 Luzern"));
        add(new NativeLabel("Schweiz"));
        add(new NativeLabel("jonas.cahenzli@gmail.com"));
        add(new NativeLabel("www.fortylove.ch seit 2023"));
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSpacing(true);
    }
}
