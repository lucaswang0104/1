package com.example.myspringboot.modulers.login.controller;

import java.util.*;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.myspringboot.modulers.login.entity.UserEntity;
import com.example.myspringboot.modulers.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 *
 *
 * @author ""
 * @email ""
 * @date 2022-09-09 16:00:25
 */
@CrossOrigin
@RestController
@RequestMapping("login/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登录
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public HashMap<String, Object> getuser(@RequestParam(value = "name") String name,@RequestParam(value = "password") String password){
        HashMap<String, Object> res=new HashMap<>();
        UserEntity user=userService.getOne(new QueryWrapper<UserEntity>().eq("username",name).eq("password",password));
        if(user!=null){
            res.put("code",200);
            res.put("list",user);
            return res;
        }else{
            res.put("code",500);
        }
        return res;
    }
    /**
     * 列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public HashMap<String, Object> list(@RequestParam(value = "name") String name){
        List<UserEntity> list=new ArrayList<>();
        HashMap<String, Object> res=new HashMap<>();
        if(name.equals("admin")){
         list=userService.queryPage();
            res.put("code",200);
            res.put("list",list);
            return res;
        } else{
            res.put("code",500);
        }
    return res;
    }

    /**
     * 查询
     */
    @RequestMapping(value = "/list/like")
    @ResponseBody
    public HashMap<String, Object> like(@RequestParam(value = "key") String key){
        List<UserEntity> list=new ArrayList<>();
        HashMap<String, Object> res=new HashMap<>();
        if(!key.equals("")){
            list=userService.list(new QueryWrapper<UserEntity>().like("username",key));
            res.put("code",200);
            res.put("list",list);
            return res;
        } else{
            res.put("code",500);
        }
        return res;
    }

    /**
     * 信息
     */
    @RequestMapping("/info")
    public HashMap<String, Object> info(@RequestParam(value = "id") Integer id){
		UserEntity user = userService.getById(id);
        HashMap<String, Object> res=new HashMap<>();
        res.put("user",user);
        return res;
    }

    /**
     * 保存
     */
    @RequestMapping("/save")

    public HashMap<String, Object> save(@RequestBody UserEntity user){
        HashMap<String, Object> res=new HashMap<>();
        UserEntity user1=userService.getOne(new QueryWrapper<UserEntity>().eq("username",user.getUsername()).eq("password",user.getPassword()));
        if(user1!=null){
            res.put("code",500);
            res.put("msg","用户已存在");
            return res;
        }
        boolean b= userService.save(user);
        if(b){
            res.put("code",200);
        }else {
            res.put("code",500);
        }
        return res;
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public HashMap<String, Object> update(@RequestBody UserEntity user){
        boolean b=userService.updateById(user);
        HashMap<String, Object> res=new HashMap<>();
        if(b){
            res.put("code",200);
        }else {
            res.put("code",500);
        }
        return res;
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")

    public HashMap<String, Object> delete(@RequestParam(value = "ids")  Integer ids){
        HashMap<String, Object> res=new HashMap<>();
		boolean b=userService.removeById(ids);
        if(b){
		res.put("code",200);
        }
        return res;
    }

}
