package com.company.projectmanagement.view.project;

import com.company.projectmanagement.entity.Project;

import com.company.projectmanagement.entity.Task;
import com.company.projectmanagement.entity.User;
import com.company.projectmanagement.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "projects", layout = MainView.class)
@ViewController("Project.list")
@ViewDescriptor("project-list-view.xml")
@LookupComponent("projectsDataGrid")
@DialogMode(width = "64em")
public class ProjectListView extends StandardListView<Project> {

    @Autowired
    private CurrentAuthentication currentAuthentication;

    public void onInitEntity(StandardDetailView.InitEntityEvent<Task> event) { 
        User user = (User) currentAuthentication.getUser();
        event.getEntity().setAssignee(user);
    }


}