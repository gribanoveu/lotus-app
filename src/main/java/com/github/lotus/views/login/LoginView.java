package com.github.lotus.views.login;

import com.github.lotus.security.AuthenticatedUser;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.stream.Collectors;

import static com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode.CENTER;

@AnonymousAllowed
@PageTitle("Login")
@Route(value = "login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
    public static final String IMAGE_LOGO_PATH = "/images/logo.svg";
    public static final String ALT_IMAGE_LOGO = "app";
    public static final String LOGIN_ACTION = "login";

    private final AuthenticatedUser authenticatedUser;
    private final LoginForm login = new LoginForm();

    public LoginView(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(CENTER);
        var logoImage = new Image(IMAGE_LOGO_PATH, ALT_IMAGE_LOGO);

        login.setAction(LOGIN_ACTION);
        add(logoImage, login);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (authenticatedUser.getUser().isPresent())
            event.forwardTo("");  // Already logged in

        login.setError(event.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error"));
    }
}
