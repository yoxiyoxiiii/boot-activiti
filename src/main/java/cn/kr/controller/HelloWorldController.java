package cn.kr.controller;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloWorldController {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;




    @GetMapping("/start-process")
    public String startProcess() {

//        repositoryService.createDeployment()
//                .addClasspathResource("processes/MyProcess.bpmn")
//                .deploy();


        runtimeService.startProcessInstanceByKey("myProcess");
        return "Process started. Number of currently running"
                + "process instances = "
                + runtimeService.createProcessInstanceQuery().count();
    }

    @GetMapping("query")
    public String query(){
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        processDefinitionQuery.processDefinitionKey("myProcess");
        processDefinitionQuery.latestVersion();
        processDefinitionQuery.orderByProcessDefinitionVersion().desc();
        List<ProcessDefinition> list = processDefinitionQuery.list();
        for (ProcessDefinition pd : list) {
            System.out.println(pd.getId() + "    " + pd.getName() + "    " + pd.getVersion());
        }
        return "success";
    }
}
