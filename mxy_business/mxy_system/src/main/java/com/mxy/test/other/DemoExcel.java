package com.mxy.test.other;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;
import com.mxy.system.utils.CustomSheetWriteHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DemoExcel {

//    @RequestMapping(value = "/userExport", method = RequestMethod.GET)
//    public void userExport(HttpServletResponse response, String ccId, String vdnId){
//        response.setContentType("application/vnd.ms-excel");
//        response.setCharacterEncoding("utf-8");
//        try {
//            String fileName = URLEncoder.encode("AgentInfo", "UTF-8");
//            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
//            // 新建ExcelWriter
//            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).registerWriteHandler(new CustomSheetWriteHandler()).build();
//            // 1、Agent Info-sheet
//            JSONObject queryAgentInfoJson = new JSONObject();
//            queryAgentInfoJson.put("url", "/ccbcs/rs/agents/query/" + ccId + "/" + vdnId);
//            JSONObject queryAgentInfoResult = (JSONObject)transoport(queryAgentInfoJson);
//            List<JSONObject> agentInfoList = queryAgentInfoResult.getJSONArray("data").toJavaList(JSONObject.class);
//            List<List<Object>> agentInfoData = new ArrayList<>();
//            agentInfoList.forEach(t -> {
//                List<Object> data = new ArrayList<>();
//                data.add(t.get("id"));
//                data.add(t.get("name"));
//                data.add(t.get("fullName"));
//                data.add(t.get("groupId"));//
//                data.add(t.get("null"));
//                data.add(t.get("userClassId"));//
//
//                StringBuffer sfSkillQueue = new StringBuffer();
//                StringBuffer sfAgentPower = new StringBuffer();
//                StringBuffer sfSkillPower = new StringBuffer();
//                List<JSONObject> list = t.getJSONArray("skills").toJavaList(JSONObject.class);
//                list.forEach(k -> {
//                    sfSkillQueue.append(k.getString("skillId")).append(",");
//                    sfAgentPower.append(k.getString("agentPower")).append(",");
//                    sfSkillPower.append(k.getString("skillPower")).append(",");
//                });
//                data.add(String.valueOf(sfSkillQueue));
//                data.add(String.valueOf(sfAgentPower));
//                data.add(String.valueOf(sfSkillPower));
//                agentInfoData.add(data);
//            });
//            WriteSheet agentInfoSheet = EasyExcel.writerSheet(0, "Agent Info").head(ExportUserDto.class).build();
//            excelWriter.write(agentInfoData, agentInfoSheet);
//
//            // 2、Skill Info-sheet
//            List<List<String>> skillInfoTitle = new ArrayList<>();
//            skillInfoTitle.add(Collections.singletonList("Skill ID"));
//            skillInfoTitle.add(Collections.singletonList("Skill Name"));
//            JSONObject querySkillInfoJson = new JSONObject();
//            querySkillInfoJson.put("url","/ccbcs/rs/skillqueues/query/" + ccId + "/" + vdnId);
//            querySkillInfoJson.put("ids",new Long[]{});
//            JSONObject querySkillInfoResult = (JSONObject)transoport(querySkillInfoJson);
//            List<JSONObject> skillInfoList = querySkillInfoResult.getJSONArray("data").toJavaList(JSONObject.class);
//            List<List<Object>> skillInfoData = new ArrayList<>();
//            skillInfoList.forEach(t->{
//                List<Object> data = new ArrayList<>();
//                data.add(t.get("id"));
//                data.add(t.get("name"));
//                skillInfoData.add(data);
//            });
//            WriteSheet skillInfoSheet = EasyExcel.writerSheet(1, "Skill Info").head(skillInfoTitle).build();
//            excelWriter.write(skillInfoData, skillInfoSheet);
//
//            // 3、Skill Group Info-sheet
//            List<List<String>> skillGroupInfoTitle = new ArrayList<>();
//            skillGroupInfoTitle.add(Collections.singletonList("Skill Group ID"));
//            skillGroupInfoTitle.add(Collections.singletonList("Skill Group Name"));
//            skillGroupInfoTitle.add(Collections.singletonList("Skill IDs"));
//
//            JSONObject querySkillGroupJson = new JSONObject();
//            querySkillGroupJson.put("url", "/ccbcs/rs/skillgroups/query/" + ccId + "/" + vdnId);
//            querySkillGroupJson.put("ids",new Long[]{});
//            JSONObject querySkillGroupResult = (JSONObject)transoport(querySkillGroupJson);
//            List<JSONObject> skillGroupList = querySkillGroupResult.getJSONArray("data").toJavaList(JSONObject.class);
//            List<List<Object>> skillGroupData = new ArrayList<>();
//            skillGroupList.forEach(t->{
//                List<Object> data = new ArrayList<>();
//                data.add(t.get("id"));
//                data.add(t.get("groupName"));
//                StringBuffer sf = new StringBuffer();
//                List<JSONObject> list = t.getJSONArray("skills").toJavaList(JSONObject.class);
//                list.forEach(k->{
//                    sf.append(k.getString("skillId")).append(",");
//                });
//                data.add(String.valueOf(sf));
//                skillGroupData.add(data);
//            });
//            WriteSheet skillGroupInfoSheet = EasyExcel.writerSheet(2, "Skill Group Info").head(skillGroupInfoTitle).build();
//            excelWriter.write(skillGroupData, skillGroupInfoSheet);
//
//            // 4、File Version-sheet
//            List<List<Object>> fileVersionResult = new ArrayList<>();
//            List<Object> fileVersionData = new ArrayList<>();
//            fileVersionData.add("File Version:");
//            fileVersionData.add("1.1.0");
//            fileVersionResult.add(fileVersionData);
//            fileVersionData = new ArrayList<>();
//            fileVersionData.add("Export Date:");
//            fileVersionData.add(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
//            fileVersionResult.add(fileVersionData);
//            WriteSheet fileVersionSheet = EasyExcel.writerSheet(3, "File Version").build();
//            excelWriter.write(fileVersionResult, fileVersionSheet);
//
//            //关闭流
//            excelWriter.finish();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


}
