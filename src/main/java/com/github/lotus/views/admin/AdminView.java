package com.github.lotus.views.admin;

import com.github.lotus.security.AuthenticatedUser;
import com.github.lotus.views.MainLayout;
import com.github.lotus.views.components.TextBoxComponent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.function.SerializableSupplier;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.RolesAllowed;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_PRIMARY;
import static com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode.START;
import static com.vaadin.flow.component.tabs.TabSheetVariant.LUMO_TABS_EQUAL_WIDTH_TABS;

@PageTitle("Admin")
@Route(value = "admin", layout = MainLayout.class)
@RolesAllowed({ "USER", "ADMIN" })
public class AdminView extends VerticalLayout {
    public static final String USER_SETTINGS_HEADER_TEXT = "Current user settings";
    public static final String USER_SETTINGS_DESCRIPTION_TEXT = "Change the data of the current user and click save";
    public static final String USER_SETTING_TAB = "User Setting";
    public static final String ADMIN_VIEW_CLASS_NAME = "admin-view";
    public static final String APP_SETTINGS_HEADER_TEXT = "App settings";
    public static final String APP_SETTINGS_DESCRIPTION_TEXT = "Change the data and click save";
    public static final String APP_SETTING_TAB = "App Setting";

    private final AuthenticatedUser authenticatedUser;
    private final UserSettingTab userSettingTab;

    private final TabSheet tabSheet = new TabSheet();

    public AdminView(AuthenticatedUser authenticatedUser, UserSettingTab userSettingTab) {
        this.authenticatedUser = authenticatedUser;
        this.userSettingTab = userSettingTab;

        addClassName(ADMIN_VIEW_CLASS_NAME);
        setSizeFull();
        setJustifyContentMode(START);
        setPadding(false);
        tabSheet.addThemeVariants(LUMO_TABS_EQUAL_WIDTH_TABS);
        tabSheet.setWidthFull();

        tabSheet.add(USER_SETTING_TAB,
                new LazyComponent(() -> new VerticalLayout(
                        TextBoxComponent.addTabDescription(USER_SETTINGS_HEADER_TEXT, USER_SETTINGS_DESCRIPTION_TEXT),
                        userSettingTab.addUserDetailsInfo(authenticatedUser)
                )));

        tabSheet.add(APP_SETTING_TAB,
                new LazyComponent(() -> new VerticalLayout(
                        TextBoxComponent.addTabDescription(APP_SETTINGS_HEADER_TEXT, APP_SETTINGS_DESCRIPTION_TEXT),
                        new Text("This is the Dashboard tab content")
                )));

        add(tabSheet);
    }

    public static class LazyComponent extends Div {
        public LazyComponent(SerializableSupplier<? extends Component> supplier) {
            addAttachListener(e -> {
                if (getElement().getChildCount() == 0) add(supplier.get());
            });
        }
    }
}
