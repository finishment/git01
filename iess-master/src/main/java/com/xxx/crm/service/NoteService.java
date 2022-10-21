package com.xxx.crm.service;


import cn.hutool.core.util.StrUtil;
import com.xxx.crm.base.ResultInfo;
import com.xxx.crm.dao.NoteMapper;
import com.xxx.crm.entity.Note;
import com.xxx.crm.vo.NoteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoteService {
    @Autowired
    NoteMapper noteMapper;
    /**
     * 查询云记详情
     1. 参数的非空判断
     2. 调用Dao层的查询，通过noteId查询note对象
     3. 返回note对象
     * @param noteId
     * @return
     */
    public Note findNoteById(String noteId) {
        // 1. 参数的非空判断
        if (StrUtil.isBlank(noteId)) {
            return  null;
        }
        // 2. 调用Dao层的查询，通过noteId查询note对象
        Note note = noteMapper.selectNote(Integer.parseInt(noteId));
        // 3. 返回note对象
        return note;
    }
    /**
     * 删除云记
     1. 判断参数
     2. 调用Dao层的更新方法，返回受影响的行数
     3. 判断受影响的行数是否大于0
     如果大于0，返回1；否则返回0
     * @param noteId
     * @return
     */
    public Integer deleteNote(String noteId) {
        // 1. 判断参数
        if (StrUtil.isBlank(noteId)) {
            return 0;
        }
        // 2. 调用Dao层的更新方法，返回受影响的行数
        int row = noteMapper.deleteNote(Integer.parseInt(noteId));
        // 3. 判断受影响的行数是否大于0
        if (row > 0) {
            return 1;
        }
        return 0;
    }
    /**
     * 添加或修改云记
     1. 参数的非空判断
     如果为空，code=0，msg=xxx，result=note对象，返回resultInfo对象
     2. 设置回显对象 Note对象
     3. 调用Dao层，添加云记记录，返回受影响的行数
     4. 判断受影响的行数
     如果大于0，code=1
     如果不大于0，code=0，msg=xxx，result=note对象
     5. 返回resultInfo对象
     * @param typeId
     * @param title
     * @param content
     * @return
     */
    public ResultInfo addOrUpdate(String typeId,Integer userId, String title,
                                        String content, String noteId, String lon, String lat)
    {
        ResultInfo resultInfo = new ResultInfo();

        // 1. 参数的非空判断
//        if (StrUtil.isBlank(typeId)) {
//            resultInfo.setCode(0);
//            resultInfo.setMsg("请选择云记类型！");
//            return  resultInfo;
//        }
//        if (StrUtil.isBlank(title)) {
//            resultInfo.setCode(0);
//            resultInfo.setMsg("云记标题不能为空！");
//            return  resultInfo;
//        }
//        if (StrUtil.isBlank(content)) {
//            resultInfo.setCode(0);
//            resultInfo.setMsg("云记内容不能为空！");
//            return  resultInfo;
//        }

        // 设置经纬度的默认，默认设置为北京  116.404, 39.915
        if (lon == null || lat == null) {
            lon = "116.404";
            lat = "39.915";
        }

        // 2. 设置回显对象 Note对象
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setUserId(userId);
        if (typeId!=null) {
            note.setTypeId(Integer.parseInt(typeId));
        }else {
            note.setTypeId(Integer.parseInt("1"));
        }
        note.setLon(Float.parseFloat(lon));
        note.setLat(Float.parseFloat(lat));

        // 判断云记ID是否为空
        if (!StrUtil.isBlank(noteId)) {
            note.setNoteId(Integer.parseInt(noteId));
        }

        resultInfo.setResult(note);

        // 3. 调用Dao层，添加云记记录，返回受影响的行数
        int row=0;
        if (StrUtil.isBlank(noteId)){
            row = noteMapper.addNote(note);
        }else {
            row = noteMapper.updateNote(note);
        }
        // 4. 判断受影响的行数
        if (row > 0) {
            resultInfo.setCode(1);
        } else {
            resultInfo.setCode(0);
            resultInfo.setResult(note);
            resultInfo.setMsg("更新失败！");
        }
        return resultInfo;
    }


    /**
     * 通过月份查询对应的云记数量
     * @param userId
     * @return
     */
    public ResultInfo queryNoteCountByMonth(Integer userId) {
        ResultInfo resultInfo = new ResultInfo();

        // 通过月份分类查询云记数量
        List<NoteVo> noteVos = noteMapper.findNoteCountByDate(userId);

        // 判断集合是否存在
        if (noteVos != null && noteVos.size() > 0) {
            // 得到月份
            List<String> monthList = new ArrayList<>();
            // 得到云记集合
            List<Integer> noteCountList = new ArrayList<>();

            // 遍历月份分组集合
            for (NoteVo noteVo: noteVos) {
                monthList.add(noteVo.getGroupName());
                noteCountList.add((int)noteVo.getNoteCount());
            }

            // 准备Map对象，封装对应的月份与云记数量
            Map<String, Object> map = new HashMap<>();
            map.put("monthArray", monthList);
            map.put("dataArray", noteCountList);

            // 将map对象设置到ResultInfo对象中
            resultInfo.setResult(map);
        }
        return resultInfo;
    }
    /**
     * 查询用户发布云记时的坐标
     * @param userId
     * @return
     */
    public ResultInfo queryNoteLonAndLat(Integer userId) {
        ResultInfo resultInfo = new ResultInfo();

        // 通过用户ID查询云记列表
        List<Note> noteList = noteMapper.queryNoteList(userId);

        // 判断是否为空
        if (noteList != null && noteList.size() > 0) {
            resultInfo.setResult(noteList);
        }

        return resultInfo;
    }

    /**
     * 通过日期分组查询当前登录用户下的云记数量
     * @param userId
     * @return
     */
    public List<NoteVo> findNoteCountByDate(Integer userId) {
        return noteMapper.findNoteCountByDate(userId);
    }
    /**
     * 通过类型分组查询当前登录用户下的云记数量
     * @param userId
     * @return
     */
    public List<NoteVo> findNoteCountByType(Integer userId) {
        return noteMapper.findNoteCountByType(userId);
    }
}
