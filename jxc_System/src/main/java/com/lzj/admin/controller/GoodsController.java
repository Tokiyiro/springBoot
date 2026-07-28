package com.lzj.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzj.admin.model.RespBean;
import com.lzj.admin.pojo.Goods;
import com.lzj.admin.pojo.GoodsType;
import com.lzj.admin.query.GoodsQuery;
import com.lzj.admin.service.GoodsService;
import com.lzj.admin.service.GoodsTypeService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品控制器
 * @author TianTian
 * @date 2022/1/18 22:50
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
    private GoodsService goodsService;
	
	@Resource
    private GoodsTypeService goodsTypeService;
	
    // 跳转页面
    @RequestMapping("index")
    public String index(){
        return "goods/goods";
    }
	
    //	商品列表查询
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> list(GoodsQuery goodsQuery){

    	// 分页对象
        Page<Goods> page = new Page<>(goodsQuery.getPage(),goodsQuery.getLimit());
        // 分页条件
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();

        // 判断是否要商品名称搜索
        if(goodsQuery.getGoodsName()!=null && !goodsQuery.getGoodsName().trim().isEmpty()){
        	// 添加条件
            queryWrapper.like("name", goodsQuery.getGoodsName());
        }
        // 商品类别查询
        if(goodsQuery.getTypeId()!=null){
            queryWrapper.eq("type_id", goodsQuery.getTypeId());
        }
        // 排序
        queryWrapper.orderByAsc("code");
        
        goodsService.page(page,queryWrapper);
        
        // 补充非数据库字段
        for(Goods goods : page.getRecords()){

            // 单位
            goods.setUnitName(goods.getUnit());

            // 商品类别
            if(goods.getTypeId()!=null){
                GoodsType goodsType = goodsTypeService.getById(goods.getTypeId());
                if(goodsType!=null){
                    goods.setTypeName(goodsType.getName());
                }
            }
        }
        
        Map<String,Object> map = new HashMap<>();

        map.put("code",0);
        map.put("msg","");
        map.put("count",page.getTotal());
        map.put("data",page.getRecords());

        return map;
    }
    

    // 删除商品
    @RequestMapping("delete")
    @ResponseBody
    public RespBean delete(Integer id){

        boolean flag = goodsService.removeById(id);

        if(flag){
            return RespBean.success("删除成功");
        }else{
            return RespBean.error("删除失败");
        }
    }
    
    /**
     * 跳转添加/修改商品页面
     */
    @RequestMapping("addOrUpdateGoodsPage")
    public String addOrUpdateGoodsPage(Integer id, Integer typeId, Model model){
        // 编辑
        if(id != null){
            Goods goods = goodsService.getById(id);
            model.addAttribute("goods", goods);
            if(goods.getTypeId()!=null){
                GoodsType goodsType = goodsTypeService.getById(goods.getTypeId());
                model.addAttribute("goodsType", goodsType);
            }
        } else if(typeId != null) {// 新增选择类别
            GoodsType goodsType = goodsTypeService.getById(typeId);
            model.addAttribute("goodsType", goodsType);
        }

        return "goods/add_update";
    }

   	// 添加商品
    @RequestMapping("save")
    @ResponseBody
    public RespBean save(Goods goods){
    	
    	// 自动生成商品编码
    	QueryWrapper<Goods> wrapper = new QueryWrapper<>();
    	wrapper.orderByDesc("code");

    	Goods lastGoods = goodsService.list(wrapper)
    	        .stream()
    	        .findFirst()
    	        .orElse(null);

        int code = 1;

        if(lastGoods != null && lastGoods.getCode() != null){
            code = Integer.parseInt(lastGoods.getCode()) + 1;
        }

        goods.setCode(String.format("%04d", code));


    	// 初始库存
        goods.setInventoryQuantity(0);

        // 上次采购价格
        goods.setLastPurchasingPrice(goods.getPurchasingPrice());

        // 商品状态
        goods.setState(0);

        // 未删除
        goods.setIsDel(0);

        boolean flag = goodsService.save(goods);

        if(flag){
            return RespBean.success("添加成功");
        }else{
            return RespBean.error("添加失败");
        }
        
    }
    

    // 修改商品
    @RequestMapping("update")
    @ResponseBody
    public RespBean update(Goods goods){

        boolean flag = goodsService.updateById(goods);

        if(flag){
            return RespBean.success("修改成功");
        }else{
            return RespBean.error("修改失败");
        }
    }
    /**
     * 跳转商品类别选择页面
     */
    @RequestMapping("toGoodsTypePage")
    public String toGoodsTypePage(Integer typeId, Model model){

        if(typeId != null){
            GoodsType goodsType = goodsTypeService.getById(typeId);
            model.addAttribute("goodsType", goodsType);
        }

        return "goods/goods_type";
    }
}