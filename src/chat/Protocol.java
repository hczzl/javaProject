package chat;

public interface Protocol {
    final static String SOFTWARE = "QQ聊天室";// 软件名
    final static String VERSION = "1.4";// 软件版本号
    final static String DEFAULT_IP = "127.0.0.1";// 默认IP地址
    final static int DEFAULT_PORT = 8888;// 默认端口号
    final static int NAME_MAX = 15;// 用户名最大长度
    final static int NAME_MIN = 4;// 用户名最小长度
    // 敏感词汇
    final static String[] FORBID_WORDS = {"拉登", "基地组织", "恐怖分子"};
    final static int TIME_BETWEEN_MSG = 2;// 防刷屏时间设置
    final static String SYSTEM_MSG = "1k3N1k";// 系统信息
    final static String ADD_USER = "d9Fa0j";// 新用户进入聊天室
    final static String DELETE_USER = "9fIk3l";// 用户离开聊天室
    final static String EXIST_USERS = "9A3kmc";// 已登陆用户
    final static String MSG_FROM = "4lCcj8";// 信息来源标记
    final static String NAME_END = "ci8Kja";// 名字信息结尾标记
    final static String USER_LOGOUT = "k8O0z2";// 用户退出聊天室
    final static String USER_EXIST = "USER_EXIST";// 用户名已有人使用

    //登陆名已被使用的异常
    final static class ExistException extends Exception {
        public ExistException() {
            super("用户名已存在");
        }
    }

    //版本过期异常
    final static class VersionException extends Exception {
        public VersionException(String ver) {
            super("版本过期,最新版本为v" + ver);
        }
    }
}
