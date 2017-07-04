package com.sven.hobby;

import com.sven.hobby.DTO.Gunpla;
import com.sven.hobby.FileIO.FileReadAndWriter;
import com.sven.hobby.jsoup.BandaiParser;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Coupang on 2016. 11. 9..
 */
public class Run {
	public static void main(String args[]) throws IOException, ParseException {

		BandaiParser bandaiParser = new BandaiParser();
		List<Gunpla> resultList = bandaiParser.getPremiumGunplaList();

		FileReadAndWriter comparor = new FileReadAndWriter();
		comparor.compare(resultList);

	}
}
