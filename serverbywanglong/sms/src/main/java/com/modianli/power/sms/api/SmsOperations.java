package com.modianli.power.sms.api;

import com.modianli.power.sms.core.SmsMessage;

public interface SmsOperations {

  String send(String mobile, String message);

  String send(SmsMessage msg);
}
