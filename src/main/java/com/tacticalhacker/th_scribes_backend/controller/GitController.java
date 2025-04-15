package com.tacticalhacker.th_scribes_backend.controller;

import com.tacticalhacker.th_scribes_backend.model.GitData;
import com.tacticalhacker.th_scribes_backend.service.GitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/git")
public class GitController {

    @Autowired
    GitService gitService;

    @GetMapping("/info")
    public GitData getGitInfo() {
        return gitService.getGitInfo();
    }

}
