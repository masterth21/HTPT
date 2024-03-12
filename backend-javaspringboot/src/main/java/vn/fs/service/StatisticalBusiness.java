package vn.fs.service;

import java.util.List;

import vn.fs.dto.CategoryBestSeller;
import vn.fs.dto.Statistical;
import vn.fs.entity.Order;
import vn.fs.entity.Product;

public interface StatisticalBusiness {
	
	public List<Statistical> getStatisticalYear(int year);
	
	public List<Integer> getYears();
	
	public Double getRevenueByYear(int year);
	
	public List<Order> getAllOrderSuccess();
	
	public List<CategoryBestSeller> getCategoryBestSeller();
	
	public List<Product> getInventory();

}
