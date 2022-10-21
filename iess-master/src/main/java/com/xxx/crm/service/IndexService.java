package com.xxx.crm.service;

import cn.hutool.core.util.StrUtil;

import com.xxx.crm.base.BaseController;
import com.xxx.crm.dao.NoteMapper;
import com.xxx.crm.dao.TeaseMapper;
import com.xxx.crm.dao.XuYuanMapper;
import com.xxx.crm.dao.ZuFangMapper;
import com.xxx.crm.entity.Note;
import com.xxx.crm.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexService extends BaseController  {
//    NoteMapper noteMapper = new MybatisUtils().getSqlSession().getMapper(NoteMapper.class);
//    NoteTypeMapper noteTypeMapper =new MybatisUtils().getSqlSession().getMapper(NoteTypeMapper.class);
    @Autowired
    NoteMapper noteMapper;
    public Page<Note> noteList(Integer userId, String title, String date, String typeIdStr, String pageNumStr, String pageSizeStr)
    {
        // 设置分页参数的默认值
        Integer pageNum = 1; // 默认当前页是第一页
        Integer pageSize = 10; // 默认每页显示5条数据
        // 1. 参数的非空校验 （如果参数不为空，则设置参数）
        if (!StrUtil.isBlank(pageNumStr)) {
            // 设置当前页
            pageNum = Integer.parseInt(pageNumStr);
        }
        if (!StrUtil.isBlank(pageSizeStr)) {
            // 设置每页显示的数量
            pageSize = Integer.parseInt(pageSizeStr);
        }
        Integer typeId=null;
        if (!StrUtil.isBlank(typeIdStr)) {
            // 设置每页显示的数量
            typeId=Integer.parseInt(typeIdStr);
        }
        // 2. 查询当前登录用户的云记数量，返回总记录数 （long类型）
        int size = noteMapper.findNoteCount(userId, title, date, typeId);
        // 3. 判断总记录数是否大于0
        if (size < 1) {
            return null;
        }
        Page<Note> page = new Page<>(pageNum, pageSize, size);
        // 4. 如果总记录数大于0，调用Page类的带参构造，得到其他分页参数的值，返回Page对象
        // 得到数据库中分页查询的开始下标
        Integer index = (pageNum -1) * pageSize;
        // 5. 查询当前登录用户下当前页的数据列表，返回note集合
        List<Note> noteList = noteMapper.selectNotes(userId, title, date, typeId, index, pageSize);
//         6. 将note集合设置到page对象中
        page.setDataList(noteList);
        // 7. 返回Page对象
        return page;
    }

    public Page<Note> noteListNotByUserId(Integer userId, String title, String date, String typeIdStr, String pageNumStr, String pageSizeStr)
    {
        // 设置分页参数的默认值
        Integer pageNum = 1; // 默认当前页是第一页
        Integer pageSize = 10; // 默认每页显示5条数据
        // 1. 参数的非空校验 （如果参数不为空，则设置参数）
        if (!StrUtil.isBlank(pageNumStr)) {
            // 设置当前页
            pageNum = Integer.parseInt(pageNumStr);
        }
        if (!StrUtil.isBlank(pageSizeStr)) {
            // 设置每页显示的数量
            pageSize = Integer.parseInt(pageSizeStr);
        }
        Integer typeId=null;
        if (!StrUtil.isBlank(typeIdStr)) {
            // 设置每页显示的数量
            typeId=Integer.parseInt(typeIdStr);
        }
        // 2. 查询当前登录用户的云记数量，返回总记录数 （long类型）
        int size = noteMapper.findAllNoteCount();
        // 3. 判断总记录数是否大于0
        if (size < 1) {
            return null;
        }
        Page<Note> page = new Page<>(pageNum, pageSize, size);
        // 4. 如果总记录数大于0，调用Page类的带参构造，得到其他分页参数的值，返回Page对象
        // 得到数据库中分页查询的开始下标
        Integer index = (pageNum -1) * pageSize;
        // 5. 查询当前登录用户下当前页的数据列表，返回note集合
        List<Note> noteList = noteMapper.selectNoteListNotByUserId(title, date, typeId, index, pageSize);
//         6. 将note集合设置到page对象中
        page.setDataList(noteList);
        // 7. 返回Page对象
        return page;
    }





    @Autowired
    TeaseMapper teaseMapper;
    public Page<Note> teaseList(Integer userId, String title, String date, String typeIdStr, String pageNumStr, String pageSizeStr)
    {
        // 设置分页参数的默认值
        Integer pageNum = 1; // 默认当前页是第一页
        Integer pageSize = 10; // 默认每页显示5条数据
        // 1. 参数的非空校验 （如果参数不为空，则设置参数）
        if (!StrUtil.isBlank(pageNumStr)) {
            // 设置当前页
            pageNum = Integer.parseInt(pageNumStr);
        }
        if (!StrUtil.isBlank(pageSizeStr)) {
            // 设置每页显示的数量
            pageSize = Integer.parseInt(pageSizeStr);
        }
        Integer typeId=null;
        if (!StrUtil.isBlank(typeIdStr)) {
            // 设置每页显示的数量
            typeId=Integer.parseInt(typeIdStr);
        }
        // 2. 查询当前登录用户的云记数量，返回总记录数 （long类型）
        int size = teaseMapper.findNoteCount(userId, title, date, typeId);
        // 3. 判断总记录数是否大于0
        if (size < 1) {
            return null;
        }
        Page<Note> page = new Page<>(pageNum, pageSize, size);
        // 4. 如果总记录数大于0，调用Page类的带参构造，得到其他分页参数的值，返回Page对象
        // 得到数据库中分页查询的开始下标
        Integer index = (pageNum -1) * pageSize;
        // 5. 查询当前登录用户下当前页的数据列表，返回note集合
        List<Note> noteList = teaseMapper.selectNotes(userId, title, date, typeId, index, pageSize);
//         6. 将note集合设置到page对象中
        page.setDataList(noteList);
        // 7. 返回Page对象
        return page;
    }

    public Page<Note> teaseListNotByUserId(Integer userId, String title, String date, String typeIdStr, String pageNumStr, String pageSizeStr)
    {
        // 设置分页参数的默认值
        Integer pageNum = 1; // 默认当前页是第一页
        Integer pageSize = 10; // 默认每页显示5条数据
        // 1. 参数的非空校验 （如果参数不为空，则设置参数）
        if (!StrUtil.isBlank(pageNumStr)) {
            // 设置当前页
            pageNum = Integer.parseInt(pageNumStr);
        }
        if (!StrUtil.isBlank(pageSizeStr)) {
            // 设置每页显示的数量
            pageSize = Integer.parseInt(pageSizeStr);
        }
        Integer typeId=null;
        if (!StrUtil.isBlank(typeIdStr)) {
            // 设置每页显示的数量
            typeId=Integer.parseInt(typeIdStr);
        }
        // 2. 查询当前登录用户的云记数量，返回总记录数 （long类型）
        int size = teaseMapper.findAllNoteCount();
        // 3. 判断总记录数是否大于0
        if (size < 1) {
            return null;
        }
        Page<Note> page = new Page<>(pageNum, pageSize, size);
        // 4. 如果总记录数大于0，调用Page类的带参构造，得到其他分页参数的值，返回Page对象
        // 得到数据库中分页查询的开始下标
        Integer index = (pageNum -1) * pageSize;
        // 5. 查询当前登录用户下当前页的数据列表，返回note集合
        List<Note> noteList = teaseMapper.selectNoteListNotByUserId(title, date, typeId, index, pageSize);
//         6. 将note集合设置到page对象中
        page.setDataList(noteList);
        // 7. 返回Page对象
        return page;
    }



    @Autowired
    XuYuanMapper xuYuanMapper;
    public Page<Note> xuYuanList(Integer userId, String title, String date, String typeIdStr, String pageNumStr, String pageSizeStr)
    {
        // 设置分页参数的默认值
        Integer pageNum = 1; // 默认当前页是第一页
        Integer pageSize = 10; // 默认每页显示5条数据
        // 1. 参数的非空校验 （如果参数不为空，则设置参数）
        if (!StrUtil.isBlank(pageNumStr)) {
            // 设置当前页
            pageNum = Integer.parseInt(pageNumStr);
        }
        if (!StrUtil.isBlank(pageSizeStr)) {
            // 设置每页显示的数量
            pageSize = Integer.parseInt(pageSizeStr);
        }
        Integer typeId=null;
        if (!StrUtil.isBlank(typeIdStr)) {
            // 设置每页显示的数量
            typeId=Integer.parseInt(typeIdStr);
        }
        // 2. 查询当前登录用户的云记数量，返回总记录数 （long类型）
        int size = xuYuanMapper.findNoteCount(userId, title, date, typeId);
        // 3. 判断总记录数是否大于0
        if (size < 1) {
            return null;
        }
        Page<Note> page = new Page<>(pageNum, pageSize, size);
        // 4. 如果总记录数大于0，调用Page类的带参构造，得到其他分页参数的值，返回Page对象
        // 得到数据库中分页查询的开始下标
        Integer index = (pageNum -1) * pageSize;
        // 5. 查询当前登录用户下当前页的数据列表，返回note集合
        List<Note> noteList = xuYuanMapper.selectNotes(userId, title, date, typeId, index, pageSize);
//         6. 将note集合设置到page对象中
        page.setDataList(noteList);
        // 7. 返回Page对象
        return page;
    }

    public Page<Note> xuYuanListNotByUserId(Integer userId, String title, String date, String typeIdStr, String pageNumStr, String pageSizeStr)
    {
        // 设置分页参数的默认值
        Integer pageNum = 1; // 默认当前页是第一页
        Integer pageSize = 10; // 默认每页显示5条数据
        // 1. 参数的非空校验 （如果参数不为空，则设置参数）
        if (!StrUtil.isBlank(pageNumStr)) {
            // 设置当前页
            pageNum = Integer.parseInt(pageNumStr);
        }
        if (!StrUtil.isBlank(pageSizeStr)) {
            // 设置每页显示的数量
            pageSize = Integer.parseInt(pageSizeStr);
        }
        Integer typeId=null;
        if (!StrUtil.isBlank(typeIdStr)) {
            // 设置每页显示的数量
            typeId=Integer.parseInt(typeIdStr);
        }
        // 2. 查询当前登录用户的云记数量，返回总记录数 （long类型）
        int size = xuYuanMapper.findAllNoteCount();
        // 3. 判断总记录数是否大于0
        if (size < 1) {
            return null;
        }
        Page<Note> page = new Page<>(pageNum, pageSize, size);
        // 4. 如果总记录数大于0，调用Page类的带参构造，得到其他分页参数的值，返回Page对象
        // 得到数据库中分页查询的开始下标
        Integer index = (pageNum -1) * pageSize;
        // 5. 查询当前登录用户下当前页的数据列表，返回note集合
        List<Note> noteList = xuYuanMapper.selectNoteListNotByUserId(title, date, typeId, index, pageSize);
//         6. 将note集合设置到page对象中
        page.setDataList(noteList);
        // 7. 返回Page对象
        return page;
    }

    @Autowired
    ZuFangMapper zuFangMapper;
    public Page<Note> zuFangList(Integer userId, String title, String date, String typeIdStr, String pageNumStr, String pageSizeStr)
    {
        // 设置分页参数的默认值
        Integer pageNum = 1; // 默认当前页是第一页
        Integer pageSize = 10; // 默认每页显示5条数据
        // 1. 参数的非空校验 （如果参数不为空，则设置参数）
        if (!StrUtil.isBlank(pageNumStr)) {
            // 设置当前页
            pageNum = Integer.parseInt(pageNumStr);
        }
        if (!StrUtil.isBlank(pageSizeStr)) {
            // 设置每页显示的数量
            pageSize = Integer.parseInt(pageSizeStr);
        }
        Integer typeId=null;
        if (!StrUtil.isBlank(typeIdStr)) {
            // 设置每页显示的数量
            typeId=Integer.parseInt(typeIdStr);
        }
        // 2. 查询当前登录用户的云记数量，返回总记录数 （long类型）
        int size = zuFangMapper.findNoteCount(userId, title, date, typeId);
        // 3. 判断总记录数是否大于0
        if (size < 1) {
            return null;
        }
        Page<Note> page = new Page<>(pageNum, pageSize, size);
        // 4. 如果总记录数大于0，调用Page类的带参构造，得到其他分页参数的值，返回Page对象
        // 得到数据库中分页查询的开始下标
        Integer index = (pageNum -1) * pageSize;
        // 5. 查询当前登录用户下当前页的数据列表，返回note集合
        List<Note> noteList = zuFangMapper.selectNotes(userId, title, date, typeId, index, pageSize);
//         6. 将note集合设置到page对象中
        page.setDataList(noteList);
        // 7. 返回Page对象
        return page;
    }

    public Page<Note> zuFangListNotByUserId(Integer userId, String title, String date, String typeIdStr, String pageNumStr, String pageSizeStr)
    {
        // 设置分页参数的默认值
        Integer pageNum = 1; // 默认当前页是第一页
        Integer pageSize = 10; // 默认每页显示5条数据
        // 1. 参数的非空校验 （如果参数不为空，则设置参数）
        if (!StrUtil.isBlank(pageNumStr)) {
            // 设置当前页
            pageNum = Integer.parseInt(pageNumStr);
        }
        if (!StrUtil.isBlank(pageSizeStr)) {
            // 设置每页显示的数量
            pageSize = Integer.parseInt(pageSizeStr);
        }
        Integer typeId=null;
        if (!StrUtil.isBlank(typeIdStr)) {
            // 设置每页显示的数量
            typeId=Integer.parseInt(typeIdStr);
        }
        // 2. 查询当前登录用户的云记数量，返回总记录数 （long类型）
        int size = zuFangMapper.findAllNoteCount();
        // 3. 判断总记录数是否大于0
        if (size < 1) {
            return null;
        }
        Page<Note> page = new Page<>(pageNum, pageSize, size);
        // 4. 如果总记录数大于0，调用Page类的带参构造，得到其他分页参数的值，返回Page对象
        // 得到数据库中分页查询的开始下标
        Integer index = (pageNum -1) * pageSize;
        // 5. 查询当前登录用户下当前页的数据列表，返回note集合
        List<Note> noteList = zuFangMapper.selectNoteListNotByUserId(title, date, typeId, index, pageSize);
//         6. 将note集合设置到page对象中
        page.setDataList(noteList);
        // 7. 返回Page对象
        return page;
    }




}
