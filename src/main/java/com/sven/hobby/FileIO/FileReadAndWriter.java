package com.sven.hobby.FileIO;

import com.sven.hobby.DTO.Gunpla;
import com.sven.hobby.slack.HobbyChannel;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Coupang on 2016. 11. 16..
 */
public class FileReadAndWriter {

	private static final String FILE_PATH = "/pang/member/sven/bandai/premiumList.txt";

	private Map<Long, String> read() throws IOException {

		Map<Long, String> readMap = new HashMap<Long, String>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));

			String row = null;

			while ((row = br.readLine()) != null) {

				Long code = Long.parseLong(row.split("\t")[0]);
				String title = row.split("\t")[1];
				readMap.put(code, title);

			}
			return readMap;
		} catch (FileNotFoundException e) {
			return new HashMap<Long, String>();
		}
	}

	private void write(List<Gunpla> gunplaList) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(FILE_PATH);
		for (Gunpla gunpla : gunplaList) {
			outputStream.write((String.valueOf(gunpla.getCodes()) + "\t" + gunpla.getTitle() + "\r\n").getBytes());
		}
		outputStream.close();
	}

	public void compare(List<Gunpla> gunplaList) throws IOException {

		Map<Long, String> existingList = read();

		List<Gunpla> newGunplaList = new ArrayList<Gunpla>();

		for (Gunpla gunpla : gunplaList) {
			if (!existingList.containsKey(gunpla.getCodes())) {
				newGunplaList.add(gunpla);
			}
		}

		if (newGunplaList.size() != 0) {
			HobbyChannel slackCaller = new HobbyChannel();
			slackCaller.requestHobbyChanel(newGunplaList);
		}

		write(gunplaList);
	}
}
