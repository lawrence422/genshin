package com.genshin.system.controller;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;


import com.fasterxml.jackson.databind.util.JSONPObject;
import com.genshin.system.dao.pojo.Artifact;
import com.genshin.system.service.ArtifactService;
import com.mysql.cj.xdevapi.JsonArray;
import dto.response.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SuppressWarnings("rawtypes")
@RequestMapping("/artifact")
public class ArtifactController {
    @Autowired
    ArtifactService artifactService;

    @PreAuthorize("hasAnyAuthority('normal','admin')")
    @PostMapping("insert")
    public JsonResult insertArtifact(@RequestBody List<Artifact> list){
        System.out.println(list);
        return artifactService.insertArtifact(list);
    }


}
