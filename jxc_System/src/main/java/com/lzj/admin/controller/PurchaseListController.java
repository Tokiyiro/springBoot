package com.lzj.admin.controller;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzj.admin.model.RespBean;
import com.lzj.admin.pojo.PurchaseList;
import com.lzj.admin.pojo.PurchaseListGoods;
import com.lzj.admin.query.PurchaseListQuery;
import com.lzj.admin.service.PurchaseListService;

import com.lzj.admin.service.UserService;
import com.lzj.admin.utils.AssertUtil;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * 进货单控制器
 * @author TianTian
 * @date 2022/1/19 12:32
 */
@Controller
@RequestMapping("/purchase")
public class PurchaseListController {

    @Resource
    private PurchaseListService purchaseListService;

    @Resource
    private UserService userService;

    /**
     * 进货入库主页
     * @return
     */
    @RequestMapping("index")
    public String index(Model model){
    	model.addAttribute("purchaseNumber",purchaseListService.getNextPurchaseNumber());
        return "purchase/purchase";
    }


    @RequestMapping("save")
    @ResponseBody
    public RespBean save(PurchaseList purchaseList, String goodsJson, Principal principal){
        return RespBean.success("商品进货入库成功!");
    }

    /**
     * 进货单查询页
     * @return
     */
    @RequestMapping("searchPage")
    public String searchPage(){
        return "purchase/purchase_search";
    }


    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> purchaseList(PurchaseListQuery purchaseListQuery){
        return null;
    }

    /**
     * 删除进货单记录
     * @param id
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public RespBean delete(Integer id){
        return RespBean.success("删除成功");
    }

    @RequestMapping("countPurchase")
    @ResponseBody
    public Map<String,Object> countPurchase(PurchaseListQuery purchaseListQuery){
        return null;
    }
}
