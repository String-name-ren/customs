package com.leader.ren.service.bigscreen;

import com.leader.ren.common.dto.RestVo;
import com.leader.ren.common.utils.CommUtils;
import com.leader.ren.mapper.bigscreen.EpidemicPeoplePlaneMapper;
import com.leader.ren.mapper.bigscreen.EpidemicPieMapper;
import com.leader.ren.mapper.bigscreen.EpidemicSampleMapper;
import com.leader.ren.mapper.bigscreen.EpidemicScoreMapper;
import com.leader.ren.model.bigscreen.entity.EpidemicPeoplePlane;
import com.leader.ren.model.bigscreen.entity.EpidemicPie;
import com.leader.ren.model.bigscreen.entity.EpidemicSample;
import com.leader.ren.model.bigscreen.entity.EpidemicScore;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EpidemicService {

    @Autowired
    private EpidemicPeoplePlaneMapper peoplePlaneMapper;

    @Autowired
    private EpidemicSampleMapper sampleMapper;

    @Autowired
    private EpidemicScoreMapper scoreMapper;

    @Autowired
    private EpidemicPieMapper pieMapper;

    public RestVo getLineData(){
        Map<String,Map<String,List<String>>> result = new HashMap<>();

        //人和飞机
        List<EpidemicPeoplePlane> peoplePlaneData = peoplePlaneMapper.getPeoplePlaneData();
        if(CommUtils.isNotNull(peoplePlaneData)){
            Map<String,List<String>> echarts1 = new HashMap<>();
            List<String> echarts1_title = new ArrayList<>();
            List<String> inData1 = new ArrayList<>();
            List<String> outData1 = new ArrayList<>();

            Map<String,List<String>> echarts2 = new HashMap<>();
            List<String> echarts2_title = new ArrayList<>();
            List<String> inData2 = new ArrayList<>();
            List<String> outData2 = new ArrayList<>();
            for (EpidemicPeoplePlane peoplePlane : peoplePlaneData){
                echarts1_title.add(peoplePlane.getRecordDate());
                inData1.add(peoplePlane.getPeopleIn());
                outData1.add(peoplePlane.getPeopleOut());

                echarts2_title.add(peoplePlane.getRecordDate());
                inData2.add(peoplePlane.getPlaneIn());
                outData2.add(peoplePlane.getPlaneOut());
            }
            echarts1.put("title",echarts1_title);
            echarts1.put("inData",inData1);
            echarts1.put("outData",outData1);

            echarts2.put("title",echarts2_title);
            echarts2.put("inData",inData2);
            echarts2.put("outData",outData2);

            result.put("echarts1",echarts1);
            result.put("echarts2",echarts2);
        }

        //采样问题
        List<EpidemicSample> sampleData = sampleMapper.getSampleData();
        if(CommUtils.isNotNull(sampleData)){
            Map<String,List<String>> echarts3 = new HashMap<>();
            List<String> echarts3_title = new ArrayList<>();
            List<String> sample3 = new ArrayList<>();

            Map<String,List<String>> echarts5 = new HashMap<>();
            List<String> echarts5_title = new ArrayList<>();
            List<String> sample5 = new ArrayList<>();


            for(EpidemicSample sample : sampleData){
                if(CommUtils.isNotNull(sample.getDate1()) && CommUtils.isNotNull(sample.getSampleCount())){
                    echarts3_title.add(sample.getDate1());
                    sample3.add(sample.getSampleCount());
                }
                echarts5_title.add(sample.getDate2());
                sample5.add(sample.getSampleRate());
            }
            echarts3.put("title",echarts3_title);
            echarts3.put("data",sample3);

            echarts5.put("title",echarts5_title);
            echarts5.put("data",sample5);

            result.put("echarts3",echarts3);
            result.put("echarts5",echarts5);
        }

        //考核分数
        List<EpidemicScore> scoreData = scoreMapper.getScoreData();
        if(CommUtils.isNotNull(scoreData)){
            Map<String,List<String>> echarts4 = new HashMap<>();
            List<String> echarts4_title = new ArrayList<>();
            List<String> score4 = new ArrayList<>();
            for(EpidemicScore score : scoreData){
                echarts4_title.add(score.getId()+"");
                score4.add(score.getScore());
            }
            echarts4.put("title",echarts4_title);
            echarts4.put("data",score4);
            result.put("echarts4",echarts4);
        }

        return RestVo.SUCCESS(result);
    }

    public RestVo getPieData(){
        Map<String,List<Map<String,String>>> result = new HashMap<>();


        List<EpidemicPie> eData = pieMapper.getPieDataByType(1);
        if(CommUtils.isNotNull(eData)){
            List<Map<String,String>> echarts6 = new ArrayList<>();
            for(EpidemicPie pie : eData){
                Map<String,String> map = new HashMap<>();
                map.put("name",pie.getName());
                map.put("value",pie.getValue());
                echarts6.add(map);
            }
            result.put("echarts6",echarts6);
        }

        List<EpidemicPie> gData = pieMapper.getPieDataByType(2);
        if(CommUtils.isNotNull(gData)){
            List<Map<String,String>> echarts8 = new ArrayList<>();
            for(EpidemicPie pie : gData){
                Map<String,String> map = new HashMap<>();
                map.put("name",pie.getName());
                map.put("value",pie.getValue());
                echarts8.add(map);
            }
            result.put("echarts8",echarts8);
        }


        List<EpidemicPie> data71 = pieMapper.getPieDataByType(3);
        if(CommUtils.isNotNull(data71)){
            List<Map<String,String>> echarts71 = new ArrayList<>();
            for(EpidemicPie pie : data71){
                Map<String,String> map = new HashMap<>();
                map.put("name",pie.getName());
                map.put("value",pie.getValue());
                echarts71.add(map);
            }
            result.put("echarts71",echarts71);
        }


        List<EpidemicPie> data72 = pieMapper.getPieDataByType(4);
        if(CommUtils.isNotNull(data72)){
            List<Map<String,String>> echarts72 = new ArrayList<>();
            for(EpidemicPie pie : data72){
                Map<String,String> map = new HashMap<>();
                map.put("name",pie.getName());
                map.put("value",pie.getValue());
                echarts72.add(map);
            }
            result.put("echarts72",echarts72);
        }


        List<EpidemicPie> data73 = pieMapper.getPieDataByType(5);
        if(CommUtils.isNotNull(data73)){
            List<Map<String,String>> echarts73 = new ArrayList<>();
            for(EpidemicPie pie : data73){
                Map<String,String> map = new HashMap<>();
                map.put("name",pie.getName());
                map.put("value",pie.getValue());
                echarts73.add(map);
            }
            result.put("echarts73",echarts73);
        }


        return RestVo.SUCCESS(result);
    }



}
