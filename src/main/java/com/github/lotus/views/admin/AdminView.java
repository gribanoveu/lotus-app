package com.github.lotus.views.admin;

import com.github.lotus.security.AuthenticatedUser;
import com.github.lotus.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.function.SerializableSupplier;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Admin")
@Route(value = "admin", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class AdminView extends VerticalLayout {

    private final AuthenticatedUser authenticatedUser;
    private final UserSettingTab userSettingTab;

    public AdminView(AuthenticatedUser authenticatedUser, UserSettingTab userSettingTab) {
        this.authenticatedUser = authenticatedUser;
        this.userSettingTab = userSettingTab;

        addClassName("admin-view");
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);

        TabSheet tabSheet = new TabSheet();
        tabSheet.addThemeVariants(TabSheetVariant.LUMO_TABS_EQUAL_WIDTH_TABS);
        tabSheet.setWidthFull();

        tabSheet.add("User Setting",
                new LazyComponent(() -> new VerticalLayout(
                        setDetails()
                )));

        tabSheet.add("User Control",
                new LazyComponent(() -> new Div(
                        new Text("This is the Dashboard tab content")
                )));

        tabSheet.add("App Setting",
                new LazyComponent(() -> new Div(
                        new Text("This is the Dashboard tab content")
                )));

        add(tabSheet);
    }

    private Component setDetails() {
        var formLayout = new FormLayout();

        // выполняем все в рамках 2 запросов к базе (юзер и роль)
        // todo настроить миграции бд
        authenticatedUser.getUser().map(user -> {
            var firstName = userSettingTab.firstNameTextField(user);
            var surName = userSettingTab.surNameTextField(user);
            var patronymic = userSettingTab.patronymicTextField(user);
            var username = userSettingTab.usernameTextField(user);
            var roles = userSettingTab.rolesComboBox(user);

            formLayout.add(firstName, surName, patronymic, username, roles);
            return true;
        });
        return formLayout;
    }

    public static class LazyComponent extends Div {
        public LazyComponent(SerializableSupplier<? extends Component> supplier) {
            addAttachListener(e -> {
                if (getElement().getChildCount() == 0) add(supplier.get());
            });
        }
    }
}
