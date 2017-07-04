package com.sven.hobby.slack;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.sven.hobby.DTO.Gunpla;

import java.io.IOException;
import java.util.List;

/**
 * Created by Coupang on 2016. 11. 16..
 */
public class HobbyChannel {

	private static final String HOBBY_CHANEL = "hobby";
	private static final String HOBBY_TOKEN = "https://hooks.slack.com/services/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";
	private static final String NOTI ="*한정판 예약이 시작했습니다.*";

	public HobbyChannel() throws IOException {
	}

	public void requestHobbyChanel(List<Gunpla> gunplaList) throws IOException {

		List<String> titles = Lists.newArrayList();

		for (Gunpla gunpla : gunplaList) {
			titles.add(gunpla.getTitle());
		}

		Payload payload = Payload.builder()
			.channel(HOBBY_CHANEL)
			.username("Club G")
			.iconEmoji(":bandai:")
			.text(NOTI+"```" + Joiner.on("\n").skipNulls().join(titles) + "```")
			.build();

		Slack slack = Slack.getInstance();
		WebhookResponse response = slack.send(HOBBY_TOKEN, payload);

	}

}
