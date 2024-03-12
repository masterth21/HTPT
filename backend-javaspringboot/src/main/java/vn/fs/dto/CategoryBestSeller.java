package vn.fs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryBestSeller {

//	public CategoryBestSeller(String name, Integer count, Double amount) {
//        this.name = name;
//        this.count = count;
//        this.amount = amount;
//    }
	private String name;
	private int count;
	private Double amount;

}
