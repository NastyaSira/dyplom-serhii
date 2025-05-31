package com.sira.package_tracking;

import com.sira.package_tracking.controller.SmsController;
import com.sira.package_tracking.entity.TwilioDetails;
import com.sira.package_tracking.service.SmsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SmsModuleTests {


    @Mock
    private SmsService smsService;

    @InjectMocks
    private SmsController smsController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testController() {

        TwilioDetails details = new TwilioDetails(
                "+380937928706",
                "+380989383159",
                "Ваше замовлення успішно оформлене"
        );

        ResponseEntity<String> response = smsController.sendMessage(details);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("SMS успішно надіслано!", response.getBody());

        verify(smsService).sendSms(
                "+380937928706",
                "Ваше замовлення успішно оформлене"
        );
    }

    @Test
    public void testController_incorrect() {
        // некоректні вхідні дані
        TwilioDetails details = new TwilioDetails(
                null,
                "+380989383159",
                "Добрий день!"
        );

        ResponseEntity<String> response = smsController.sendMessage(details);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Некоректні вхідні дані", response.getBody());
        verify(smsService, never()).sendSms(any(), any());
    }

    @Test
    public void testTwilioDetails() {
     
        TwilioDetails details = new TwilioDetails(
                "+380937928706"
                "+380989383159",
                "Статус посилки: доставлено"
        );

        assertEquals("+380937928706", details.getToPhoneNumber());
        assertEquals("+380989383159", details.getFromPhoneNumber());
        assertEquals("Статус посилки: доставлено", details.getMessage());
    }
}
