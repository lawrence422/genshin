package com.genshin.system.service.impl;

import com.genshin.system.dao.pojo.Artifact;
import com.genshin.system.mapper.ArtifactMapper;
import com.genshin.system.service.ArtifactService;
import dto.response.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@SuppressWarnings("rawtypes")
public class ArtifactServiceImpl implements ArtifactService {
    @Autowired
    ArtifactMapper artifactMapper;

    @Override
    public JsonResult insertArtifact(List<Artifact> list) {
        if (list!=null&&list.size()!=0){
            for (Artifact artifact:list){
                if (artifact.getArtifactSubInitialStats4()!=null) {
                    artifactMapper.insertFourStatsArtifact(artifact);
                }else {
                    artifactMapper.insertThreeStatsArtifact(artifact);
                }
            }
            return JsonResult.success("Successful insert "+list.size()+" artifacts");
        }
        return JsonResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }
}
