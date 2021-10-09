package com.ruoyi.activiti.controller;

import com.ruoyi.activiti.service.IFormHistoryDataService;
import com.ruoyi.common.core.domain.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HistoryFormDataCoroller {
    @Autowired
    private IFormHistoryDataService formHistoryDataService;

    @GetMapping(value = "historyFromData/ByInstanceId/{instanceId}")
    public Rest historyFromData(@PathVariable("instanceId") String instanceId) {
        return Rest.success(formHistoryDataService.historyDataShow(instanceId));

    }
}
