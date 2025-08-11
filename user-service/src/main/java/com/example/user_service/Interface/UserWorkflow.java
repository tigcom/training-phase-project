package com.example.user_service.Interface;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import org.example.common_lirbary.dto.OrderDTO;
import org.example.common_lirbary.dto.productDTO;

@WorkflowInterface
public interface UserWorkflow {
    @WorkflowMethod
    OrderDTO processOrder(productDTO product,String username);
}
