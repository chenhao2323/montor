package com.github.cc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MonitorController: ${description}
 *
 * @author chenhao
 * @version 1.0
 * @date 2020/5/28 15:58
 */
@RestController
@RequestMapping("/monitor")
public class MonitorController {

    @GetMapping("test")
    public String test(){
        return "success";
    }

}
