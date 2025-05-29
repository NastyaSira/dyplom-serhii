package com.sira.package_tracking.controller;

import com.sira.package_tracking.entity.TwilioDetails;
import com.sira.package_tracking.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms")
public class SmsController {

	@Autowired
	private SmsService smsService;

	@PostMapping("/send")
	public ResponseEntity<String> sendMessage(@RequestBody TwilioDetails twilioDetails) {

		if (twilioDetails == null || twilioDetails.getFromPhoneNumber() == null
				|| twilioDetails.getToPhoneNumber() == null || twilioDetails.getMessage() == null) {
			return ResponseEntity.badRequest().body("Некоректні вхідні дані");
		}

		String fromNumber = twilioDetails.getFromPhoneNumber();
		String toNumber = twilioDetails.getToPhoneNumber();
		String msg = twilioDetails.getMessage();

		smsService.sendSms(toNumber, msg);

		return ResponseEntity.ok("SMS успішно надіслано!");
	}
}
