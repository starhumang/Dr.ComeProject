package com.drcome.project.challenge;

import java.util.Date;

import lombok.Data;

@Data
public class ChallengeVO {

	Integer challengeNo;
	Date challengeDate;
	String challengeContent;
	String userId;
	String success;
}
