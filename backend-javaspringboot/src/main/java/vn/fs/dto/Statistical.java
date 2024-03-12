package vn.fs.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistical {

//	public Statistical(int month, Date date, double amount, int count) {
//        this.month = month;
//        this.date = date;
//        this.amount = amount;
//        this.count = count;
//    }
	private int month;
	private Date date;
	private Double amount;
	private int count;

}
