package vn.fs.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.fs.dto.CategoryBestSeller;
import vn.fs.dto.Statistical;
import vn.fs.entity.Order;
import vn.fs.entity.Product;
import vn.fs.repository.OrderDAO;
import vn.fs.repository.ProductDAO;
import vn.fs.repository.StatisticalDAO;
import vn.fs.service.StatisticalBusiness;

@Service
public class StatisticalBusinessImpl implements StatisticalBusiness {

	@Autowired
	StatisticalDAO statisticalRepository;
	
	@Autowired
	OrderDAO orderRepository;
	
	@Autowired
	ProductDAO productRepository;
	
	@Override
	public List<Statistical> getStatisticalYear(int year) {
		List<Object[]> list = statisticalRepository.getMonthOfYear(year);
        List<Statistical> listReal = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            Statistical sta = new Statistical(i, null, 0.0, 0);
            for (Object[] obj : list) {
                int month = (int) obj[1];
                if (month == i) {
                    sta = new Statistical(month, null, (Double) obj[0], 0);
                    break;
                }
            }
            listReal.add(sta);
        }
        return listReal;
	}
	@Override
	public List<Integer> getYears() {
		return statisticalRepository.getYears();
	}
	@Override
	public Double getRevenueByYear(int year) {
		return statisticalRepository.getRevenueByYear(year);
	}
	@Override
	public List<Order> getAllOrderSuccess() {
		return orderRepository.findByStatus(2);
	}
	@Override
	public List<CategoryBestSeller> getCategoryBestSeller() {
		List<Object[]> list = statisticalRepository.getCategoryBestSeller();
        List<CategoryBestSeller> listCategoryBestSeller = new ArrayList<>();
        for (Object[] obj : list) {
            String categoryName = String.valueOf(obj[1]);
            int count = Integer.parseInt(String.valueOf(obj[0]));
            double totalRevenue = Double.parseDouble(String.valueOf(obj[2]));
            CategoryBestSeller categoryBestSeller = new CategoryBestSeller(categoryName, count, totalRevenue);
            listCategoryBestSeller.add(categoryBestSeller);
        }
        return listCategoryBestSeller;
	}
	@Override
	public List<Product> getInventory() {
		return productRepository.findByStatusTrueOrderByQuantityDesc();
	}

	
}
