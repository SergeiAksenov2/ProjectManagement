package com.company.projectmanagement.app;

import com.company.projectmanagement.entity.Project;
import com.company.projectmanagement.entity.ProjectStats;
import com.company.projectmanagement.entity.Task;
import io.jmix.core.DataManager;
import io.jmix.core.impl.DataManagerImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectStatsService {

    private final DataManager dataManager;

    public ProjectStatsService(DataManagerImpl dataManager) {
        this.dataManager = dataManager;
    }

    public List<ProjectStats> fetchProjectStatistics() {
        List<Project> projects = dataManager.load(Project.class).all().list();
        List<ProjectStats> projectStats2 = projects.stream()
                .map(project -> {
                    ProjectStats stat = dataManager.create(ProjectStats.class);
                    stat.setId(project.getId());
                    stat.setProjectName(project.getName());
                    stat.setTasksCount(project.getTasks().size());
                    Integer plannedEfforts2 = project.getTasks().stream().map(Task::getEstimation).reduce(0, Integer::sum);
                    stat.setPlannedEfforts(plannedEfforts2);
                    return stat;
                }).collect(Collectors.toList());
        return projectStats2;
    }
}