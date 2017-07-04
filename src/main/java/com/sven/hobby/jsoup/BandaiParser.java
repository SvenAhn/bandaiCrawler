package com.sven.hobby.jsoup;

import com.google.common.collect.Lists;
import com.sven.hobby.DTO.Gunpla;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Coupang on 2016. 11. 15..
 */
public class BandaiParser {
	private static final String BANDAI_PREMIUM_URL = "http://www.bandaimall.co.kr/premium/index.do";
	private static final String GUNPLA_SELECTOR = "div.reserve_ban_con div.reserve_ban";
	private static final String GO_TO_PAGE = "http://www.bandaimall.co.kr/defaults/index.do?main_category=3000000000&planclass_code=00000031&planGb=00&orderType=&nowYear=0000&nowMonth=00&currentPage=";

	private int page = 1;

	private Document bandaiConnect() throws IOException {
		Document doc = Jsoup.connect(BANDAI_PREMIUM_URL).get();
		return doc;
	}

	private Elements getPremiumList(Document document) {
		Elements Gunpla = document.select(GUNPLA_SELECTOR);
		return Gunpla;
	}

	public List<Gunpla> getPremiumGunplaList() throws IOException, ParseException {

		Document doc = bandaiConnect();
		int paging = doc.select("div.paging a").size() - 2;

		if (paging < 0 || paging == 0) {
			return Lists.newArrayList();
		} else if (page >= 0) {
			List<Gunpla> gunplaList = new ArrayList<Gunpla>();
			for (int page = 0; page < paging; page++) {
				Document newDoc = Jsoup.connect(GO_TO_PAGE + String.valueOf(page + 1)).get();
				Elements gunplaInfoList = getPremiumList(newDoc);
				for (Element gunplaInfo : gunplaInfoList) {

					Gunpla gunpla = new Gunpla();

					String title = gunplaInfo.select("a div.reserve_ban_txt span").get(0).text();
					gunpla.setTitle(title);

					String url = gunplaInfo.select("a").first().attr("href");
					String code = url.split("goods_code=")[1];
					gunpla.setCodes(Long.parseLong(code));

					String salesDate = gunplaInfo.select("a div.reserve_ban_txt span").get(1).text();
					gunpla.setDateRange(salesDate);

					String shippingDateString = gunplaInfo.select("p").text();
					Date shippingDate = new SimpleDateFormat("yyyy/mm").parse(shippingDateString.split(":")[1].trim());
					gunpla.setShppingDate(shippingDate);

					gunplaList.add(gunpla);
				}
			}
			return gunplaList;
		} else {
			return Lists.newArrayList();
		}
	}
}
