package com.itheima.babasport.dao.product;

import java.util.List;

import com.itheima.babasport.pojo.product.Brand;
import com.itheima.babasport.pojo.product.BrandQuery;

public interface BrandDao {
	
	//查询结果集
	//想查所有就查所有,想查分页就查分页
	public List<Brand> selectBrandListByQuery(BrandQuery brandQuery);
	
	//查询总条数
	public Integer countBrandByQuery(BrandQuery brandQuery);
	
	//通过id查询一个品牌对象
	public Brand selectBrandById(Long id);
	
	//修改品牌通过id
	public void uploadBrandById(Brand brand);
	
	//通过一堆id删除数据
	public void deletes(Long[] ids);
	
}
