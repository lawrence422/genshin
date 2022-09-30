package com.genshin.system.mapper;

import com.genshin.system.dao.pojo.Artifact;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArtifactMapper {

    @Insert("INSERT INTO artifact(artifact_set,artifact_type,artifact_main_stats,artifact_sub_initial_stats_1,artifact_sub_initial_stats_1_value,artifact_sub_initial_stats_2,artifact_sub_initial_stats_2_value,artifact_sub_initial_stats_3,artifact_sub_initial_stats_3_value,artifact_sub_initial_stats_4,artifact_sub_initial_stats_4_value)VALUES" +
            "(#{artifactSet},#{artifactType},#{artifactMainStats},#{artifactSubInitialStats1},#{artifactSubInitialStats1Value},#{artifactSubInitialStats2},#{artifactSubInitialStats2Value},#{artifactSubInitialStats3},#{artifactSubInitialStats3Value},#{artifactSubInitialStats4},#{artifactSubInitialStats4Value}) ")
    int insertFourStatsArtifact(Artifact artifact);

    @Insert("INSERT INTO artifact(artifact_set,artifact_type,artifact_main_stats,artifact_sub_initial_stats_1,artifact_sub_initial_stats_1_value,artifact_sub_initial_stats_2,artifact_sub_initial_stats_2_value,artifact_sub_initial_stats_3,artifact_sub_initial_stats_3_value)VALUES" +
            "(#{artifactSet},#{artifactType},#{artifactMainStats},#{artifactSubInitialStats1},#{artifactSubInitialStats1Value},#{artifactSubInitialStats2},#{artifactSubInitialStats2Value},#{artifactSubInitialStats3},#{artifactSubInitialStats3Value}) ")
    int insertThreeStatsArtifact(Artifact artifact);
}
