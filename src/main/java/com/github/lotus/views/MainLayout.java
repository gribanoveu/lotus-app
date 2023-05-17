package com.github.lotus.views;

import com.github.lotus.data.entity.User;
import com.github.lotus.security.AuthenticatedUser;
import com.github.lotus.views.admin.AdminView;
import com.github.lotus.views.helloworld.HelloWorldView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;

import java.util.Arrays;
import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {
    private final AuthenticatedUser authenticatedUser;
    private final AccessAnnotationChecker accessChecker;
    private static final String SIGN_OUT_LABEL = "Sign out";

    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;
        addToNavbar(createHeaderContent());
    }

    private Component createHeaderContent() {
        var layout = new Header(); // добавить header
        layout.addClassName("app-header");

        // добавить вложенный div и разместить в нем элементы
        var flexLayout = new FlexLayout();
        flexLayout.addClassName("header-content");

        addAppLogo(flexLayout);
        addMenuLinks(flexLayout);
        addUserBlock(flexLayout);

        layout.add(flexLayout);
        return layout;
    }

    /**
     * Список ссылок в хедере
     * @return добавляет ссылку в качестве < li >
     */
    private MenuItemInfo[] createMenuItems() {
        return new MenuItemInfo[]{ //
                new MenuItemInfo("Hello World", HelloWorldView.class),
                new MenuItemInfo("Admin", AdminView.class),
        };
    }

    public static class MenuItemInfo extends ListItem {
        private final Class<? extends Component> view;
        public MenuItemInfo(String menuTitle, Class<? extends Component> view) {
            this.view = view;
            var link = new RouterLink(menuTitle, view);
            link.addClassName("header-link");
            add(link);
        }
        public Class<?> getView() { return view; }

    }


    /** Проверить есть ли доступ и добавить ссылку в шапку */
    private void addMenuLinks(FlexLayout layout) {
        UnorderedList list = new UnorderedList();
        list.addClassName("header-links");
        layout.add(list);

        Arrays.stream(createMenuItems()).filter(menuItem ->
                accessChecker.hasAccess(menuItem.getView())).forEach(list::add);
    }

    /** Добавить лого в хедер */
    private void addAppLogo(FlexLayout layout) {
        var appName = new Image("images/logo.svg", "logo");
        appName.addClassName("header-logo");
        layout.add(appName);
    }

    /** добавить блок с именем пользователя, если не залогинен, то ссылка на авторизацию */
    private void addUserBlock(FlexLayout layout) {
        authenticatedUser.get().map(user -> {
            var userMenu = createUserMenu(user);
            layout.add(userMenu);
            return true;
        }).orElseGet(() -> {
            var loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
            return false;
        });
    }

    /** список меню после нажатия на блок пользователя*/
    private MenuBar createUserMenu(Optional<User> user) {
        MenuBar userMenu = new MenuBar();
        userMenu.setThemeName("tertiary-inline contrast");

        user.ifPresent(u -> {
            MenuItem userName = userMenu.addItem("\uD83D\uDE80" + " " + user.get().getName());
            userName.getSubMenu().addItem(SIGN_OUT_LABEL, event -> authenticatedUser.logout());
        });
        return userMenu;
    }


}
