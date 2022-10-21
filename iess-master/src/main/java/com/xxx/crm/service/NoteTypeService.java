package com.xxx.crm.service;

import cn.hutool.core.util.StrUtil;

import com.xxx.crm.base.ResultInfo;
import com.xxx.crm.dao.NoteMapper;
import com.xxx.crm.dao.NoteTypeMapper;
import com.xxx.crm.entity.NoteType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteTypeService {
    @Autowired
    NoteTypeMapper noteTypeMapper;
    @Autowired
    NoteMapper noteMapper;
//    NoteTypeMapper noteTypeMapper = new MybatisUtils().getSqlSession().getMapper(NoteTypeMapper.class);
//    NoteMapper noteMapper = new MybatisUtils().getSqlSession().getMapper(NoteMapper.class);
    public List<NoteType> findTypeList(Integer userId) {
        if (noteTypeMapper==null){
            ApplicationContext ctx=new ClassPathXmlApplicationContext("spring.xml");
            SqlSessionFactory factoryBean=(SqlSessionFactory) ctx.getBean("sqlSessionFactory");
            SqlSession sqlSession =factoryBean.openSession(true);
            noteTypeMapper = sqlSession.getMapper(NoteTypeMapper.class);
        }
        List<NoteType> noteTypes = noteTypeMapper.selectNoteType(userId);
        return noteTypes;
    }
    public ResultInfo deleteType(String typeId) {
        ResultInfo resultInfo = new ResultInfo();
        // 1. 判断参数是否为空
        if (StrUtil.isBlank(typeId)) {
            resultInfo.setCode(0);
            resultInfo.setMsg("系统异常，请重试！");
            return resultInfo;
        }
        // 2. 调用Dao层，通过类型ID查询云记记录的数量
        long noteCount = noteMapper.selectNotesByID(Integer.parseInt(typeId));
        // 3. 如果云记数量大于0，说明存在子记录，不可删除
        if (noteCount > 0) {
            resultInfo.setCode(0);
            resultInfo.setMsg("该类型存在子记录，不可删除！！");
            return resultInfo;
        }
        // 4. 如果不存在子记录，调用Dao层的更新方法，通过类型ID删除指定的类型记录，返回受影响的行数
        int row = noteTypeMapper.deleteNoteType(Integer.parseInt(typeId));
        // 5. 判断受影响的行数是否大于0
        if (row > 0) {
            resultInfo.setCode(1);
        } else {
            resultInfo.setCode(0);
            resultInfo.setMsg("删除失败！");
        }
        return resultInfo;
    }
    public  ResultInfo addOrUpdate(String typeName, Integer userId, String typeId)
    {

        ResultInfo resultInfo = new ResultInfo();
        // 1. 判断参数是否为空 （类型名称）
        if (StrUtil.isBlank(typeName)) {
            resultInfo.setCode(0);
            resultInfo.setMsg("类型名称不能为空！");
            return resultInfo;
        }
        //  2. 调用Dao层，查询当前登录用户下，类型名称是否唯一，返回0或1 （1=可用；0=不可用）
        List<NoteType> noteTypes = noteTypeMapper.selectNoteType(userId);
        int code =1;
        for (NoteType noteType : noteTypes) {
            if (noteType.getTypeName().equals(typeName)){
                code=0;
            };
        }
        // 如果不可用，code=0，msg=xxx，返回ResultInfo对象
        if (code == 0) {
            resultInfo.setCode(0);
            resultInfo.setMsg("类型名称已存在，请重新输入！");
            return resultInfo;
        }
        // 3. 判断类型ID是否为空
        // 返回的结果
        NoteType noteType=new NoteType();
        noteType.setTypeName(typeName);
        if (!StrUtil.isBlank(typeId)){
            noteType.setTypeId(Integer.parseInt(typeId));
        }
        noteType.setUserId(userId);
        Integer key = null; // 主键或受影响的行数
        if (StrUtil.isBlank(typeId)) {//TODO 日记类型主键自动增长，需要调整业务
            // 如果为空，调用Dao层的添加方法，返回主键 （前台页面需要显示添加成功之后的类型ID）
            noteTypeMapper.addNoteType(noteType);
            key =noteTypeMapper.selectNoteTypeID(noteType);
        } else {
            // 如果不为空，调用Dao层的修改方法，返回受影响的行数
            key = noteTypeMapper.updateNoteType(noteType);
        }
        //  4. 判断 主键/受影响的行数 是否大于0
        if (key > 0) {
            resultInfo.setCode(1);
            resultInfo.setResult(key);
        } else {
            resultInfo.setCode(0);
            resultInfo.setMsg("更新失败！");
        }
        return resultInfo;
    }
}
