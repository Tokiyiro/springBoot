package com.lzj.admin.controller;


import com.lzj.admin.dto.TreeDto;
import com.lzj.admin.model.RespBean;
import com.lzj.admin.pojo.GoodsType;
import com.lzj.admin.service.GoodsTypeService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TianTian
 * @date 2022/1/19 8:36
 */
@Controller
@RequestMapping("/goodsType")
public class GoodsTypeController {

	@Resource
    private GoodsTypeService goodsTypeService;

	// 跳转商品分类管理页面
	@RequestMapping("index")
	public String index(){
	    return "goodsType/goods_type";
	}
	
	// 商品类别列表查询
	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(){

	    List<GoodsType> list = goodsTypeService.list();

	    Map<String,Object> map = new HashMap<>();

	    map.put("code",0);
	    map.put("msg","");
	    map.put("count",list.size());
	    map.put("data",list);

	    return map;
	}
	
	// 跳转添加商品类别页面
	@RequestMapping("addGoodsTypePage")
	public String addGoodsTypePage(Integer pId, Model model){

	    model.addAttribute("pId",pId);

	    return "goodsType/add";
	}
	
	// 添加商品类别
	@RequestMapping("save")
	@ResponseBody
	public RespBean save(GoodsType goodsType){

	    boolean flag = goodsTypeService.save(goodsType);

	    if(flag){
	        return RespBean.success("添加成功");
	    }else{
	        return RespBean.error("添加失败");
	    }

	}


	// 删除商品类别
	@RequestMapping("delete")
	@ResponseBody
	public RespBean delete(Integer id){

	    boolean flag = goodsTypeService.removeById(id);

	    if(flag){
	        return RespBean.success("删除成功");
	    }else{
	        return RespBean.error("删除失败");
	    }

	}
	
    //查询所有商品类别（树状图）
    @RequestMapping("queryAllGoodsTypes")
    @ResponseBody
    public List<TreeDto> queryAllGoodsTypes(){

    	return goodsTypeService.queryAllGoodsTypes();

    }
    
    // 跳转商品类别选择页面
    @RequestMapping("selectPage")
    public String selectPage(){
        return "goodsType/select";
    }

}