package com.services.api.constant;


import com.services.api.utils.ConfigurationService;

public class ServiceConstant {


    public static final String URL_QRCODE_ACCESS = "https://hqqrcode.developteam.net"  ;
    public static final String ADMIN_USERNAME = "admin";

    public static final String ROOT_DIRECTORY =  ConfigurationService.getInstance().getString("file.upload-dir","/tmp/upload");

    public static final Integer USER_KIND_ADMIN = 1;
    public static final Integer USER_KIND_USER = 2;
    public static final Integer USER_KIND_AGENCY = 3;

    public static final Integer STATUS_ACTIVE = 1;
    public static final Integer STATUS_PENDING = 0;
    public static final Integer STATUS_LOCK = -1;
    public static final Integer STATUS_DELETE = -2;

    public static final Integer GROUP_KIND_ADMIN = 1;
    public static final Integer GROUP_KIND_AGENCY = 2;
    public static final Integer GROUP_KIND_CUSTOMER = 3;

    public static final Integer MAX_ATTEMPT_FORGET_PWD = 5;
    public static final Integer MAX_TIME_FORGET_PWD = 5 * 60 * 1000; //5 minutes
    public static final Integer MAX_ATTEMPT_LOGIN = 5;



    public static final String DEVICE_TYPE_ANDROID = "ANDROID";
    public static final String DEVICE_TYPE_IOS = "IOS";
    public static final String DEVICE_TYPE_WEB = "WEBSITE";
    public static final String DEVICE_TYPE_PARTNER_API = "PARTNER_API";
    public static final String DEVICE_TYPE_WEB_PORTAL = "WEB_PORTAL";

    public static final Integer BOOKING_STATE_REQUEST = 0;
    public static final Integer BOOKING_STATE_ACCEPT = 1;
    public static final Integer BOOKING_STATE_FAIR = 2;
    public static final Integer BOOKING_STATE_DONE = 3;
    public static final Integer BOOKING_STATE_CANCEL = 4;
    public static final Integer BOOKING_STATE_CALL_CENTER = 5;

    public static final Integer PAYMENT_TYPE_CASH = 1;
    public static final Integer PAYMENT_TYPE_CARD = 2;

    public static final String PROVINCE_KIND_PROVINCE="PROVINCE_KIND_PROVINCE";
    public static final String PROVINCE_KIND_DISTRICT="PROVINCE_KIND_DISTRICT";
    public static final String PROVINCE_KIND_WARD="PROVINCE_KIND_WARD";

    public static final Integer GENDER_MALE = 1;
    public static final Integer GENDER_FEMALE = 2;
    public static final Integer GENDER_OTHER = 3;

    public static final Integer POSITION_STATE_BUSY = 2;
    public static final Integer POSITION_STATE_ONLINE = 1;
    public static final Integer POSITION_STATE_OFFLINE = 0;


    public static final Integer PROMOTION_KIND_MONEY = 1;
    public static final Integer PROMOTION_KIND_PERCENT = 2;
    public static final Integer PROMOTION_STATE_DONE = 2;
    public static final Integer PROMOTION_STATE_RUNNING = 1;
    public static final Integer PROMOTION_STATE_PENDING = 0;
    public static final Integer PROMOTION_BEHAVIOR_SINGLE = 0;
    public static final Integer PROMOTION_BEHAVIOR_MULTI = 1; 

    public static final Integer KNOWLEDGE_STATE_PENDING = 0;
    public static final Integer KNOWLEDGE_STATE_ACTIVE = 1;

    private ServiceConstant(){
        throw new IllegalStateException("Utility class");
    }

}
