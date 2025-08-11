package com.example.user_service.config;
import com.example.user_service.Interface.Impl.UserActivitiesImpl;
import com.example.user_service.Interface.Impl.UserWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemporalConfig {
    @Bean
    public WorkflowClient workflowClient() {
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
        return WorkflowClient.newInstance(service);
    }
    @Bean
    public WorkerFactory workerFactory(WorkflowClient client) {
        return WorkerFactory.newInstance(client);
    }
    @Bean
    public Worker userWorker(WorkerFactory factory, UserActivitiesImpl activitiesImpl) {
        Worker worker = factory.newWorker("UserTaskQueue");
        worker.registerWorkflowImplementationTypes(UserWorkflowImpl.class);
        worker.registerActivitiesImplementations(activitiesImpl);
        factory.start();
        return worker;
    }
}