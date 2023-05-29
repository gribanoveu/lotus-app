package com.github.lotus.views.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
/**
 * @author Evgeny Gribanov
 * @version 29.05.2023
 */
public class TextBoxComponent {
    public static Component addTabDescription(String header, String description) {
        var textContentLayout = new VerticalLayout();

        var descriptionHeader = new H3(header);
        descriptionHeader.setWidthFull();

        var descriptionInfo = new Span(description);
        descriptionInfo.setWidthFull();

        var descriptionBorder = new Div();
        descriptionBorder.addClassName("desc-border");

        textContentLayout.setPadding(false);
        textContentLayout.add(descriptionBorder, descriptionHeader, descriptionInfo);

            var horizontal = new HorizontalLayout();
            horizontal.setWidthFull();
            horizontal.add(descriptionBorder,  textContentLayout);
        return horizontal;
    }
}
