package com.moneyanalyzer.controller;

import com.moneyanalyzer.dto.StatementSummary;
import com.moneyanalyzer.service.StatementAnalysisSummary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/statements")
public class StatementController {

    private final StatementAnalysisSummary statementService;

    public StatementController(StatementAnalysisSummary statementService){
        this.statementService = statementService;
    }

    @PostMapping("/upload")
    public ResponseEntity<StatementSummary> uploadStatement(@RequestParam("file")MultipartFile file) throws Exception {
        return ResponseEntity.ok(statementService.processStatement(file));
    }
}
