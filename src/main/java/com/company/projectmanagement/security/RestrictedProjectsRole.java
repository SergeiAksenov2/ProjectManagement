package com.company.projectmanagement.security;

import com.company.projectmanagement.entity.Project;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.security.model.RowLevelBiPredicate;
import io.jmix.security.model.RowLevelPolicyAction;
import io.jmix.security.role.annotation.PredicateRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;

@RowLevelRole(name = "RestrictedProjectsRole", code = RestrictedProjectsRole.CODE)
public interface RestrictedProjectsRole {
    String CODE = "restricted-projects";

    @PredicateRowLevelPolicy(entityClass = Project.class, actions = {RowLevelPolicyAction.UPDATE, RowLevelPolicyAction.DELETE})
    default RowLevelBiPredicate<Project, ApplicationContext> projectPredicate() {

        return (project, applicationContext) -> {
            CurrentAuthentication currentAuthentication = applicationContext.getBean(CurrentAuthentication.class);
            UserDetails user = currentAuthentication.getUser();
            return user.equals(project.getManager());
        };

    }
}