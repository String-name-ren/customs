package com.leader.ren.common.constant;

/**
 * 公共常量
 */
public class Common {
    public static final String JWT_KEY_CODE = "code";
    public static final String JWT_KEY_NAME = "name";
    public static final String JWT_KEY_TIME = "time";

    /**
     * 门店Code
     */
    public static final String SHOP_CODE_KEY = "constant:shop_code";

    /**
     * 微信token
     */
    public static final String WX_SESSION_KEY = "wx:sessionKey:";

    public static final String USER_LOGIN_HY_TOKEN = "user:login:hy:token:";

    public static final String USER_LOGIN_HY_ID = "user:login:hy:id:";

    public static final String USER_LOGIN_DY_TOKEN = "user:login:dy:token:";

    public static final String USER_LOGIN_DY_ID = "user:login:dy:id:";

    /**
     * 会员等级key
     */
    public static final String MEMBER_LEVEL_KEY = "westore-memeber-level";


    /**
     * 商品条码Key
     */
    public static final String GOODS_SN = "westore-goodsSn:";

    /**
     * 商品状态 - 字典Tag
     */
    public static final String PUB_ITEM_STATUS_TAG = "PUB_ITEM_STATUS";

    /**
     * 调拨单状态 - 已完成
     */
    public static final String PUB_MOVE_DOC_STATUS = "3";

    /**
     * 删除标志 1正常 , 0 删除
     */
    public static final Byte D_FLAG_NORMAL = 1;

    /**
     * 删除标志 1正常 , 0 删除
     */
    public static final Byte D_FLAG_INVALID = 0;

    /**
     * 礼物单
     */
    public static final Byte GITF_FLAG = 3;

    /**
     * PO单
     */
    public static final Byte PO_FLAG = 4;

    /**
     *中间状态
     * 采购状态 1 待发货 ,2 部分发货， 3 已发货 4 部分到货(收一部分，还有一部分未收) ,
     * 终态(订单完成)：
     * 5 有差异 (收货差异，100个收50个), 6 全部到货
     */
    public static final Byte PUB_DOC_RESET_STATUS = 0;
    public static final Byte PUB_DOC_DELIVERY_WAIT = 1;

    public static final Byte PUB_DOC_DELIVERY_SEND_PART = 2;

    public static final Byte PUB_DOC_DELIVERY_YET = 3;

    public static final Byte PUB_DOC_DELIVERY_PART = 4;

    public static final Byte PUB_DOC_DELIVERY_DIFF = 5;

    public static final Byte PUB_DOC_DELIVERY_ALL = 6;

    public static final Byte PUB_DOC_ORDER_CHECKPASS = 3;

    //1-待发货,2部分发货，3，已发货，4，待收货，
    // 5-拒收, 6-已收货,7-收货差异(未收完) 8，供方取消

    public static final Byte PUB_DELIVERY_REJ_RECV = 5;

    public static final Byte PUB_DELIVERY_CHK_RECV = 6;

    public static final Byte PUB_DELIVERY_CHK_DEF = 7;

    public static final Byte PUB_DELIVERY_CHK_CANCEL = 8;

    //public static final Byte PUB_DELIVERY_PRE_RECV = 4;

    public static final Byte PUB_DELIVERY_DELIVERY = 3;

    public static final Byte PUB_DELIVERY_PRE_SEND = 1;

    public static final Byte PUB_DELIVERY_SEND_PART = 2;

    public static final Byte PROC_STATUS_ORDEERED = 1;

    /**采购需求审核拒绝*/
    public static final Integer SCM_CHECK_REFUSE=3;
    /**
     * 查询用户店铺信息缓存
     */
    public static final String SYS_USER_DEPT = "SYS_USER_DEPT";

    /**
     * 查询用户所有状态店铺信息缓存
     *
     * @author 张立河
     * date 2019/7/23 14:07
     */
    public static final String SYS_USER_DEPT_ALL_SHOP = "SYS_USER_DEPT_ALL_SHOP";

    /**
     * 查询机构信息缓存
     */
    public static final String SYS_USER_DEPT_BRANCH = "SYS_USER_DEPT_BRANCH";

    public static final String SYS_USER_BRANCH_STORAGE = "SYS_USER_BRANCH_STORAGE";

    public static final String SYS_SHOP_BRANCH = "SYS_SHOP_BRANCH";

    /**
     * 查询用户仓库信息缓存
     */
    public static final String SYS_USER_STORAGE = "SYS_USER_STORAGE";

    /**
     * 查询用户所有状态仓库信息缓存
     */
    public static final String SYS_USER_STORAGE_ALL_STATUS = "SYS_USER_STORAGE_ALL_STATUS";



    /********************************短信模板code**************************************/

    public static final String SYS_MSG_BIZ_CODE_CUSTOMER_BIND_MOBILE = "customer_bind_mobile";

    public static final String SYS_MSG_BIZ_CODE_CUSTOMER_REGISTER = "customer_register";

    public static final String SYS_MSG_BIZ_CODE_SALESORDER_PAYSTAT_FOUR = "salesorder_paystat_4";

    //审核同意
    public static final Byte AUDIT_APPROVAL = 2;
    //审核拒绝
    public static final Byte AUDIT_REJECT = 4;

    public static final Byte AP_EST_FLAG_YES = 1 ;

    public static final Byte AP_EST_FLAG_NO = 0 ;

    public static final Byte AP_EST_FLAG_PART = 2 ;

    //商品状态
    public static final Byte ITEM_STATE_NEW = 1;

    public static final Byte ITEM_STATE_TEST = 2;

    public static final Byte ITEM_STATE_NORMAL = 3;

    public static final Byte ITEM_STATE_ONE_TIME = 4;


    //采购退货审核同意
    public static final Byte RETURN_AUDIT_APPROVAL = 3;

    //采购退货审核
    public static final Byte PRUCHASE_AUDIT_CHECK = 5;
    //采购退货审核拒绝
    public static final Byte RETURN_AUDIT_REJECT = 2;

    //仓库启用状态
    public static final Byte USABLE_STORAGE = 2;
    //退货仓库状态
    public static final Byte IS_RETURN_STORAGE = 1;
    public static final String TEMPLATE_NOT_FIND ="APPROVAL_TEMPLATE_NOT_FIND" ;

    // 价格类型字典tag
    public static final String PUB_PRICE_TYPE_TAG = "PUB_PRICE_TYPE";

    // MQ空间
    public static String MQ_GOODS_NAME_SPACE = "GOODS";

    // 调价范围为全部
    public static final Long GOODS_CHGPRICE_RANG_ALL = 0L;

    // 调价标题最大长度为20字符
    public static final int GOODS_CHGPRICE_TITLE_LENGHT_MAX = 20;

    // 调价任务的说明文案
    public static final String GOODS_CHGPRICE_JOB_DESC_PREFIX = "调价Job";
    public static final String GOODS_CHGPRICE_JOB_DESC = GOODS_CHGPRICE_JOB_DESC_PREFIX + ":{0}";

    // 补货计划：月底
    public static final int GOODS_REPLENISH_PLAN_MONTH_END = 0;

    // 补货锁的KEY
    public static final String REPLENISH_PLAN_LOCK_KEY = "REPLENISH-PLAN";

    // 调价任务的说明文案
    public static final String GOODS_REPLENISH_JOB_DESC = "补货计划Job:{0}";

    // taskok的cookie的key
    public static final String TASKOK_LOGIN_IDENTITY_KEY = "TSK_JOB_LOGIN_IDENTITY";

    // 向右的斜线"/"
    public static final String LINE_RIGHT = "/";
    // 点"."
    public static final String POINT_STRING = ".";
    // 拷贝标识
    public static final String COPY_FLAG = "-COPY";

    // 上传图片类型
    public static final String PIC_TYPES = "gif/bmp/jpg/jpeg/png";

    // 上传Excel类型
    public static final String EXCEL_TYPES = "xlsx/xls/xlsb/xlsm/xlt";

    // 上传图片大小
    public static final Long PIC_SIZE_LIMIT = 2 * 1024 * 1024L;

    // 上传图片限制2M
    public static final Long PIC_SIZE_LIMIT_2M = 2 * 1024 * 1024L;

    // 上传图片限制10M
    public static final Long PIC_SIZE_LIMIT_10M = 2 * 1024 * 1024L;

    //上传媒体类型
    public static final String MEDIA_TYPES = "mp4/avi/mov/rmvb/flv/ts/vob/mpg/dat/wmv/asf/mkv/dv/trp/vob/aiff/amr/m4a/mp2/mp3/ogg/ra/au/wav/wma/mka/png/jpg/jpe/jpeg/jfif/bmp/dib/gif";

    //上传媒体文件大小
    public static final Long MEDIA_SIZE_LIMIT = 200 * 1024 * 1024L;

    //用户登录token
    public static final String USER_AUTH_TOKEN="user-auth-token:";

    //资产管理报废报修上传文件大小
    public static final Long ASSET_SIZE_LIMIT = 50 * 1024 * 1024L;

    //上传媒体类型
    public static final String ASSET_TYPES = "mp4/png/jpg/jpe/jpeg";

    //日志内容
    public static final String LOG_MESSAGE = "操作内容: ";

}


