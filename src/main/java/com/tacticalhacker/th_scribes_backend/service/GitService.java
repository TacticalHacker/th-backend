package com.tacticalhacker.th_scribes_backend.service;

import com.tacticalhacker.th_scribes_backend.model.GitData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GitService {

    private final GitData gitData;

    public GitData getGitInfo() {
        return gitData;
    }
}
