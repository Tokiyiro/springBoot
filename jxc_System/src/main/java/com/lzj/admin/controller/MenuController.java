package com.lzj.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzj.admin.dto.TreeDto;
import com.lzj.admin.model.RespBean;
import com.lzj.admin.pojo.Menu;
import com.lzj.admin.service.MenuService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单控制器
 * @author TianTian
 * @date 2022/1/14 15:40
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;
	
	@RequestMapping("index")
	public String index() {
	    return "menu/menu";
	}

	/**
	 * 菜单列表
	 */
	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list() {

	    Map<String,Object> result = new HashMap<>();

	    result.put("code",0);
	    result.put("msg","");
	    result.put("data",menuService.listAllMenu());

	    return result;
	}
	
	@RequestMapping("addMenuPage")
	public String addMenuPage(Integer grade, Integer pId, Model model) {

	    model.addAttribute("grade", grade);
	    model.addAttribute("pId", pId);

	    return "menu/add";
	}
	
	@RequestMapping("save")
	@ResponseBody
	public RespBean save(Menu menu) {

	    if (menuService.save(menu)) {
	        return RespBean.success("保存成功");
	    }

	    return RespBean.error("保存失败");
	}
	
	@RequestMapping("updateMenuPage")
	public String updateMenuPage(Integer id, Model model) {

	    Menu menu = menuService.getById(id);

	    model.addAttribute("menu", menu);

	    return "menu/update";
	}
	
	@RequestMapping("update")
	@ResponseBody
	public RespBean update(Menu menu) {

	    menuService.updateMenu(menu);

	    return RespBean.success("修改成功");
	}
	
	@PreAuthorize("hasAnyAuthority('1030')")
	@RequestMapping("delete")
	@ResponseBody
	public RespBean delete(Integer id){

	    if(menuService.deleteMenu(id)){
	        return RespBean.success("删除成功");
	    }

	    return RespBean.error("删除失败");
	}
	
}