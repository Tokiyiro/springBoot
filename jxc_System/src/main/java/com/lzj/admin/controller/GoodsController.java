package com.lzj.admin.controller;


import com.lzj.admin.model.RespBean;
import com.lzj.admin.pojo.Goods;
import com.lzj.admin.query.GoodsQuery;
import com.lzj.admin.service.GoodsService;
import com.lzj.admin.service.GoodsTypeService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 商品控制器
 * @author TianTian
 * @date 2022/1/18 22:50
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @Resource
    private GoodsTypeService goodsTypeService;

    @RequestMapping("index")
    public String index(){
        return "goods/goods";
    }



    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> goodsList(GoodsQuery goodsQuery){
        return goodsService.queryGoodsByParams(goodsQuery);
    }


    /**
     * 添加|更新商品页
     * @param id
     * @param typeId
     * @param model
     * @return
     */
    @RequestMapping("addOrUpdateGoodsPage")
    public String addOrUpdateGoodsPage(Integer id, Integer typeId,Model model){
        return "goods/add_update";
    }

    /**
     * 商品类别选择页
     * @param typeId
     * @param model
     * @return
     */
    @RequestMapping("toGoodsTypePage")
    public String toGoodsTypePage(Integer typeId, Model model){
        return "goods/goods_type";
    }

    /**
     * 添加商品接口
     * @param goods
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public RespBean saveGoods(Goods goods){
        return RespBean.success("商品记录添加成功");
    }


    /**
     * 更新商品接口
     * @param goods
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public RespBean updateGoods(Goods goods){
        return RespBean.success("商品记录更新成功");
    }

    @RequestMapping("delete")
    @ResponseBody
    public RespBean deleteGoods(Integer id){
        return RespBean.success("商品记录删除成功");
    }
}
