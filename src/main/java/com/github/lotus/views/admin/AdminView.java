package com.github.lotus.views.admin;

import com.github.lotus.data.Role;
import com.github.lotus.data.entity.User;
import com.github.lotus.security.AuthenticatedUser;
import com.github.lotus.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.SerializableSupplier;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.RolesAllowed;

import java.util.Optional;

@PageTitle("Admin")
@Route(value = "admin", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class AdminView extends VerticalLayout {

    private final AuthenticatedUser authenticatedUser;

    Tab profile = new Tab(new Span("User Control"));
    Tab settings = new Tab( new Span("App Settings"));
    Tab notifications = new Tab( new Span("Notifications"));

    MultiSelectComboBox<Role> comboBox = new MultiSelectComboBox<>();


    public AdminView(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;

        addClassName("admin-view");
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        TabSheet tabSheet = new TabSheet();
        tabSheet.addThemeVariants(TabSheetVariant.LUMO_TABS_EQUAL_WIDTH_TABS);
        tabSheet.setWidthFull();


        tabSheet.add("User Setting",
                new LazyComponent(() -> new Div(
                        userDetails(),
                        comboBox
                )));

        tabSheet.add("User Control",
                new LazyComponent(() -> new Div(
                        new Text("This is the Dashboard tab content")
                )));

        tabSheet.add("App Setting",
                new LazyComponent(() -> new Div(
                        new Text("This is the Dashboard tab content")
                )));


        add(comboBox, tabSheet);

    }

    private Component userDetails() {
        TextField fullName = new TextField();
        TextField userName = new TextField();
        FormLayout formLayout = new FormLayout();

        formLayout.setMaxWidth("500px");

        fullName.setRequired(true);
        userName.setRequired(true);

        formLayout.addFormItem(fullName, "Full Name");
        formLayout.addFormItem(userName, "User Name");
        formLayout.addFormItem(comboBox, "Roles");
        comboBox.setWidth("500px");



        authenticatedUser.get().map(user -> {
           fullName.setValue(user.get().getName());
           userName.setValue(user.get().getUsername());
           comboBox.setItems(user.get().getRoles());
           comboBox.select(user.get().getRoles());
            return true;
        });

        return formLayout;
    }

    public static class LazyComponent extends Div {
        public LazyComponent(SerializableSupplier<? extends Component> supplier) {
            addAttachListener(e -> {
                if (getElement().getChildCount() == 0) {
                    add(supplier.get());
                }
            });
        }
    }
}
