package com.lzj.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.lzj.admin.dto.TreeDto;
import com.lzj.admin.pojo.GoodsType;
import com.lzj.admin.mapper.GoodsTypeMapper;
import com.lzj.admin.service.GoodsTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzj.admin.utils.AssertUtil;
import com.lzj.admin.utils.PageResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

/**
 * 商品表类型实现类
 * @author TianTian
 * @date 2022/1/19 14:51
 */
@Service
public class GoodsTypeServiceImpl extends ServiceImpl<GoodsTypeMapper, GoodsType> implements GoodsTypeService {

	@Override
	public List<TreeDto> queryAllGoodsTypes() {
		 List<GoodsType> goodsTypes = this.list();

		    List<TreeDto> treeDtos = new ArrayList<>();

		    for (GoodsType goodsType : goodsTypes) {

		    	TreeDto treeDto = new TreeDto();

		        treeDto.setId(goodsType.getId());
		        treeDto.setName(goodsType.getName());
		        treeDto.setpId(goodsType.getpId());

		        treeDtos.add(treeDto);
		    }
		    return treeDtos;
	}
	
}