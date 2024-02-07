package com.company.projectmanagement.view.timeentry;

import com.company.projectmanagement.entity.TimeEntry;

import com.company.projectmanagement.entity.User;
import com.company.projectmanagement.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Route(value = "timeEntries/:id", layout = MainView.class)
@ViewController("TimeEntry.detail")
@ViewDescriptor("time-entry-detail-view.xml")
@EditedEntityContainer("timeEntryDc")
public class TimeEntryDetailView extends StandardDetailView<TimeEntry> {

    @Autowired
    private CurrentAuthentication currentAuthentication;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<TimeEntry> event) {
        LocalDateTime localDateTime = LocalDateTime.now();
        event.getEntity().setEntryDate(localDateTime);
        final User user = (User) currentAuthentication.getUser();
        event.getEntity().setUser(user);
    }
}