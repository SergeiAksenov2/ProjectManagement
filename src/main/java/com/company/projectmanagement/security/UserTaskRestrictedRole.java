package com.company.projectmanagement.security;

import com.company.projectmanagement.entity.Task;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.security.model.RowLevelBiPredicate;
import io.jmix.security.model.RowLevelPolicyAction;
import io.jmix.security.role.annotation.PredicateRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;

@RowLevelRole(name = "UserTaskRestrictedRole", code = UserTaskRestrictedRole.CODE)
public interface UserTaskRestrictedRole {
    String CODE = "user-task-restricted-role";

    @PredicateRowLevelPolicy(entityClass = Task.class, actions = RowLevelPolicyAction.UPDATE)
    default RowLevelBiPredicate<Task, ApplicationContext> taskPredicate() {
        return (task, applicationContext) -> {
            CurrentAuthentication currentAuthentication = applicationContext.getBean(CurrentAuthentication.class);
            UserDetails user = currentAuthentication.getUser();
            return user.equals( task.getProject().getManager());
        };
    }
}