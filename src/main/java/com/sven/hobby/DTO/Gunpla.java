package com.sven.hobby.DTO;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Coupang on 2016. 11. 15..
 */
@Getter
@Setter
public class Gunpla {
	private String title;
	private Long codes;
	private String dateRange;
	private Date shppingDate;

	public String toString() {
		return "title:" + this.title + "\ncode:" + String.valueOf(codes) + "\nsales date:" + dateRange
			+ "\nshipping date:" + new SimpleDateFormat("yyyy/DD").format(shppingDate).toString();
	}

}
