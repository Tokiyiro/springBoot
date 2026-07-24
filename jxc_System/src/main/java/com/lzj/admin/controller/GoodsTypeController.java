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

    @RequestMapping("queryAllGoodsTypes")
    @ResponseBody
    public List<GoodsType> queryAllGoodsTypes() {
        return goodsTypeService.queryAllGoodsTypes();
    }


    @RequestMapping("index")
    public String index(){
        return "goodsType/goods_type";
    }


    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> goodsTypeList(){
        return null;
    }


    @RequestMapping("addGoodsTypePage")
    public String addGoodsTypePage(Integer pId, Model model){
        model.addAttribute("pId",pId);
        return "goodsType/add";
    }

    @RequestMapping("save")
    @ResponseBody
    public RespBean saveGoodsType(GoodsType goodsType){
        return RespBean.success("商品类别添加成功");
    }


    @RequestMapping("delete")
    @ResponseBody
    public RespBean deleteGoodsType(Integer id){
        return RespBean.success("商品类别删除成功");
    }

}
