package com.genshin.system.service;

import com.genshin.system.dao.pojo.Artifact;
import dto.response.JsonResult;

import java.util.List;

public interface ArtifactService {
    JsonResult insertArtifact(List<Artifact> list);
}
