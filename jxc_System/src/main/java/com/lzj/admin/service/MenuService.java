package com.lzj.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzj.admin.dto.TreeDto;
import com.lzj.admin.pojo.Menu;
import com.lzj.admin.utils.PageResultUtil;
import io.swagger.models.auth.In;

import javax.lang.model.type.IntersectionType;
import java.util.List;
import java.util.Map;
/**
 * 菜单表服务类
 * @author TianTian
 * @date 2022/1/19 13:57
 */
public interface MenuService extends IService<Menu> {
    public List<TreeDto> queryAllMenu(Integer roleId);
    public Map<String,Object> menuList();
    public void saveMenu(Menu menu);
    public Menu findMenuByNameAndGrade(String name, Integer grade);
    public Menu findMenuByAclValue(String aclValue);
    public void deleteMenu(Integer id);
    public List findFather(Integer id);
    public void updateMenu(Menu menu);
}
