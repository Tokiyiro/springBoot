package com.lzj.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzj.admin.model.GoodsModel;
import com.lzj.admin.pojo.Goods;
import com.lzj.admin.pojo.GoodsType;
import com.lzj.admin.query.GoodsQuery;
import com.lzj.admin.service.GoodsService;
import com.lzj.admin.service.GoodsTypeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author TianTian
 * @date 2022/1/19 14:06
 */
@Controller
@RequestMapping("common")
public class CommonController {
	
    @Resource
    private GoodsService goodsService;
    @Resource
    private GoodsTypeService goodsTypeService;

    /**
     * 添加商品-选择商品页
     * @return
     */
    @RequestMapping("toSelectGoodsPage")
    public String toSelectGoodsPage(){
        return "common/goods";
    }

    /**
     * 添加商品-商品信息添加页(单价、进货数量)
     * @param gid
     * @param model
     * @return
     */
    @RequestMapping("toAddGoodsInfoPage")
    public String toGoodsInfoPage(Integer gid, Model model){
    	
    	// 根据商品ID查询商品
        Goods goods = goodsService.getById(gid);
        // 设置商品单位
        goods.setUnitName(goods.getUnit());
        // 设置商品类别名称
        if(goods.getTypeId() != null){
            goods.setTypeName(goodsTypeService.getById(goods.getTypeId()).getName());
        }
        // 传递商品信息到页面
        model.addAttribute("goods", goods);
        // 新增操作标识
        model.addAttribute("flag", 0);
        
        return "common/goods_add_update";
    }


    /**
     * 修改商品-商品信息修改页(单价、进货数量)
     * @param goodsModel
     * @param model
     * @return
     */
    @RequestMapping("toUpdateGoodsInfoPage")
    public String toUpdateGoodsInfoPage(GoodsModel goodsModel, Model model){
    	
    	Goods goods = goodsService.getById(goodsModel.getId());
    	// 编辑时保留进货数量和价格
    	goods.setNum(goodsModel.getNum());
    	goods.setLastPurchasingPrice(goodsModel.getPrice());
    	
    	GoodsType goodsType = goodsTypeService.getById(goods.getTypeId());
    	// 查询商品类别
        if(goods.getTypeId()!=null){
        	goods.setTypeName(goodsType.getName());
        }
        // 商品单位直接使用goods表中的unit字段
        goods.setUnitName(goods.getUnit());
        
        model.addAttribute("goods", goods);
        model.addAttribute("flag", 1);
        
        return "common/goods_add_update";
    }


    /**
     * 当前库存页
     * @return
     */
    @RequestMapping("toGoodsStockPage")
    public String toGoodsStockPage() {
        return "common/stock_search";
    }



    @RequestMapping("stockList")
    @ResponseBody
    public Map<String,Object> stockLick(GoodsQuery goodsQuery){
    	
    	Page<Goods> page = new Page<>(goodsQuery.getPage(), goodsQuery.getLimit());


        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();

        // 商品名称查询（条件查询）
        if(goodsQuery.getGoodsName()!=null && !goodsQuery.getGoodsName().trim().isEmpty()){
            queryWrapper.like("name",goodsQuery.getGoodsName());
        }
        
        // 商品类别查询(左侧树状图筛选支持)
        if(goodsQuery.getTypeId()!=null){
            queryWrapper.eq("type_id",goodsQuery.getTypeId());
        }
        
        // 查询商品列表
        IPage<Goods> result = goodsService.page(page, queryWrapper);

        // 补充页面需要的非数据库字段
        for(Goods goods : result.getRecords()){

            // 单位
            goods.setUnitName(goods.getUnit());

            // 商品类别
            if(goods.getTypeId()!=null){
            	
                GoodsType goodsType = goodsTypeService.getById(goods.getTypeId());

                if(goodsType!=null){
                    goods.setTypeName(goodsType.getName());
                }
            }

            // 销售总数（暂时设置0，后续统计销售表）
            goods.setSaleTotal(0);
        }


        // Layui返回格式
        Map<String,Object> map = new HashMap<>();

        map.put("code",0);
        map.put("msg","");
        map.put("count",result.getTotal());
        map.put("data",result.getRecords());

        return map;
    }


    /**
     * 商品报损|报溢查询页
     * @return
     */
    @RequestMapping("toDamageOverflowSearchPage")
    public String toDamageOverflowSearchPage(){
        return "common/damage_overflow_search";
    }


    /**
     * 库存报警页
     * @return
     */
    @RequestMapping("alarmPage")
    public String alarmPage(){
        return "common/alarm";
    }


    /**
     * 库存报警查询接口
     * @param goodsQuery
     * @return
     */
    @RequestMapping("listAlarm")
    @ResponseBody
    public Map<String,Object> listAlarm(GoodsQuery goodsQuery){
        goodsQuery.setType(3);
        return null;
    }







}