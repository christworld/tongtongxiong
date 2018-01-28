package com.allbear.hopedemo.Const;

/**
 * Created by Administrator on 2017/11/12.
 */

public class Const {
    private static int USER_ID = 1;

    public static final String MySQLDB = "ttx";
    public static final String MySQLURL = "jdbc:mysql://106.39.224.198:3306/" + MySQLDB;
    public static final String MySQLUSER = "ttxdebug";
    public static final String MySQLPASSWORD = "ttxd4wb";

    public final static String DATABASE_NAME = "TtxDB";
    public final static int DATABASE_VERSION = 1;
    public final static String MySQLHttpHead = "http://106.39.224.198:6789/";

    public static void setUserId(int id){
        USER_ID = id;
    }
    public static int getUserId(){
        return USER_ID;
    }
    public static final String [] mTablesNamesWithUserID = {
//            "study_schedules",
            "time_sets",
//            "users",
//            "users_2"
    };
    public static final String [] mTablesNames = {
            "time_sets",
            "users",
            "books",
            "classes",
            "diags",
            "dialog_prog",
            "equips",
            "prog_schedules",
            "progs",
            "study_schedules",
            "users_2",
            "videos",
            "word_prog",
            "words",
            "ask_children",
            "ask_robot",
            "word_games",
            "vccs",
            "city",
            "province",
            "holiday",
            "programming"
    };

    public static final String [] mCreateTables = {
            Const.createTableSql_time_sets,
            Const.createTableSql_users,
            Const.createTableSql_books,
            Const.createTableSql_classes,
            Const.createTableSql_diags,
            Const.createTableSql_dialog_prog,
            Const.createTableSql_equips,
            Const.createTableSql_prog_schedules,
            Const.createTableSql_progs,
            Const.createTableSql_study_schedules,
            Const.createTableSql_users_2,
            Const.createTableSql_videos,
            Const.createTableSql_word_prog,
            Const.createTableSql_words,
            Const.createTableSql_ask_children,
            Const.createTableSql_ask_robot,
            Const.createTableSql_word_games,
            Const.createTableSql_vccs,
            Const.createTableSql_city,
            Const.createTableSql_province,
            Const.createTableSql_holiday,
            Const.createTableSql_programming
        };

    public static final String createTableSql_books = "CREATE TABLE `books` (\n" +
            "  `book_id` int(11) NOT NULL,\n" +
            "  `unit_num` int(11) NOT NULL,\n" +
            "  `eng_name` varchar(45) NOT NULL,\n" +
            "  `zhushi` varchar(45) DEFAULT NULL,\n" +
            "  `chi_name` varchar(45) NOT NULL,\n" +
            "  `file_name` varchar(45) NOT NULL,\n" +
            "  `file_url` varchar(200) NOT NULL,\n" +
            "  `gui_name` varchar(45) NOT NULL,\n" +
            "  `gui_url` varchar(200) NOT NULL,\n" +
            "  `words` varchar(200) DEFAULT NULL,\n" +
            "  `stats` varchar(500) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`book_id`)\n" +
            ")";
    public static final String createTableSql_classes = "CREATE TABLE `classes` (\n" +
            "  `class_id` int(11) NOT NULL,\n" +
            "  `unit_num` int(11) NOT NULL,\n" +
            "  `books_schd` varchar(50) NOT NULL,\n" +
            "  `diag` int(11) DEFAULT NULL,\n" +
            "  `words_schd` varchar(45) DEFAULT NULL,\n" +
            "  `video_schd` varchar(45) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`class_id`)\n" +
            ")";

    public static final String createTableSql_diags = "CREATE TABLE `diags` (\n" +
            "  `diag_id` int(11) NOT NULL,\n" +
            "  `unit_num` int(11) NOT NULL,\n" +
            "  `eng_sent` varchar(200) NOT NULL,\n" +
            "  `eng_url` varchar(200) NOT NULL,\n" +
            "  `chi_sent` varchar(200) NOT NULL,\n" +
            "  `chi_url` varchar(200) NOT NULL,\n" +
            "  `keywords` varchar(200) NOT NULL,\n" +
            "  PRIMARY KEY (`diag_id`)\n" +
            ") ";

    public static final String createTableSql_dialog_prog = "CREATE TABLE `dialog_prog` (\n"+
            "  `dialog_prog_id` int(11) NOT NULL,\n"+
            "  `user_id` int(11) NOT NULL,\n"+
            "  `question` varchar(45) NOT NULL,\n"+
            "  `answer` varchar(45) NOT NULL,\n"+
            "  PRIMARY KEY (`dialog_prog_id`)\n"+
            ") ";

    public static final String createTableSql_equips = "CREATE TABLE `equips` (\n" +
            "  `equips_id` int(11) NOT NULL,\n" +
            "  `sn` varchar(45) NOT NULL,\n" +
            "  `user_id` varchar(45) NOT NULL,\n" +
            "  `reg_date` datetime DEFAULT NULL,\n" +
            "  `equip_name` varchar(45) DEFAULT NULL,\n" +
            "  `phone_number` varchar(45) NOT NULL,\n" +
            "  `equip_pass` varchar(45) DEFAULT NULL,\n" +
            "  `city_id` int(11) DEFAULT '3',\n" +
            "  PRIMARY KEY (`equips_id`)\n" +
            ") ";

    public static final String createTableSql_prog_schedules = "CREATE TABLE `prog_schedules` (\n" +
            "  `prog_id` int(11) NOT NULL,\n" +
            "  `prog_name` varchar(45) NOT NULL,\n" +
            "  `schedules` int(11) DEFAULT NULL,\n" +
            "  `start_time` datetime DEFAULT NULL,\n" +
            "  `switch_flag` int(11) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`prog_id`)\n" +
            ") ";

    public static final String createTableSql_progs = "CREATE TABLE `progs` (\n" +
            "  `prog_id` int(11) NOT NULL,\n" +
            "  `prog_name` varchar(200) NOT NULL,\n" +
            "  `prog_intr` varchar(200) NOT NULL,\n" +
            "  `prog_flag` varchar(1) NOT NULL,\n" +
            "  `prog_time` time DEFAULT NULL,\n" +
            "  PRIMARY KEY (`prog_id`)\n" +
            ") ";

    public static final String createTableSql_study_schedules = "CREATE TABLE `study_schedules` (\n" +
            "  `user_id` int(11) NOT NULL,\n" +
            "  `class_id` int(11) NOT NULL DEFAULT '0',\n" +
            "  `video_sche` int(11) NOT NULL DEFAULT '0',\n" +
            "  `word_sche` int(11) NOT NULL DEFAULT '0',\n" +
            "  `prog1_sche` int(11) NOT NULL DEFAULT '0',\n" +
            "  `prog2_sche` int(11) NOT NULL DEFAULT '0',\n" +
            "  `prog3_sche` int(11) NOT NULL DEFAULT '0',\n" +
            "  `prog4_sche` int(11) NOT NULL DEFAULT '0',\n" +
            "  `prog5_sche` int(11) NOT NULL DEFAULT '0',\n" +
            "  `prog6_sche` int(11) NOT NULL DEFAULT '0',\n" +
            "  `prog7_sche` int(11) NOT NULL DEFAULT '0',\n" +
            "  `prog8_sche` int(11) NOT NULL DEFAULT '0',\n" +
            "  `day_nums` int(11) NOT NULL DEFAULT '0'\n" +
            ") ";

    public static final String createTableSql_time_sets = "CREATE TABLE `time_sets` (\n" +
            "  `user_id` int(11) NOT NULL,\n" +
            "  `getup_time` time DEFAULT NULL,\n" +
            "  `holi_getup_time` time DEFAULT NULL,\n" +
            "  `school_time` time DEFAULT NULL,\n" +
            "  `oral_time` time DEFAULT NULL,\n" +
            "  `video_time` time DEFAULT NULL,\n" +
            "  `book_time` time DEFAULT NULL,\n" +
            "  `sleep_time` time DEFAULT NULL,\n" +
            "  `last_update_time` time DEFAULT NULL" +
            ")";

    public static final String createTableSql_users = "CREATE TABLE `users` (\n" +
            "  `user_id` int(11) NOT NULL,\n" +
            "  `name` varchar(45) NOT NULL,\n" +
            "  `eng_name` varchar(45) DEFAULT NULL,\n" +
            "  `gender` varchar(2) DEFAULT '女',\n" +
            "  `user_birth` datetime NOT NULL,\n" +
            "  `papa_birth` datetime DEFAULT NULL,\n" +
            "  `mama_birth` datetime DEFAULT NULL,\n" +
            "  PRIMARY KEY (`user_id`)\n" +
            ") ";
    public static final String createTableSql_users_2 = "CREATE TABLE `users_2` (\n" +
            "  `user_id` int(11) NOT NULL,\n" +
            "  `family_counts` int(11) NOT NULL DEFAULT '3',\n" +
            "  `families` varchar(45) DEFAULT NULL,\n" +
            "  `city_id` int(11) NOT NULL DEFAULT '21',\n" +
            "  `hobby` varchar(200) DEFAULT NULL,\n" +
            "  `papa_occup` varchar(200) DEFAULT NULL,\n" +
            "  `mama_occup` varchar(200) DEFAULT NULL,\n" +
            "  `area_img` varchar(200) DEFAULT NULL,\n" +
            "  `presever1` varchar(45) DEFAULT NULL,\n" +
            "  `presever2` varchar(45) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`user_id`)\n" +
            ")";
    public static final String createTableSql_videos = "CREATE TABLE `videos` (\n" +
            "  `video_id` int(11) NOT NULL,\n" +
            "  `video_name` varchar(100) NOT NULL,\n" +
            "  `video_title` varchar(100) NOT NULL,\n" +
            "  `video_chi` varchar(45) NOT NULL,\n" +
            "  `video_file` varchar(100) NOT NULL,\n" +
            "  `video_url` varchar(200) NOT NULL,\n" +
            "  PRIMARY KEY (`video_id`)\n" +
            ") ";
    public static final String createTableSql_word_prog = "CREATE TABLE `word_prog` (\n" +
            "  `word_prog_id` int(11) NOT NULL,\n" +
            "  `user_id` int(11) NOT NULL,\n" +
            "  `word_id` int(11) NOT NULL,\n" +
            "  `word_practice` varchar(200) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`word_prog_id`)\n" +
            ") ";
    public static final String createTableSql_words = "CREATE TABLE `words` (\n" +
            "  `word_id` int(11) NOT NULL,\n" +
            "  `unit_num` int(11) NOT NULL,\n" +
            "  `word_alp` varchar(45) NOT NULL,\n" +
            "  `word_means` varchar(200) NOT NULL,\n" +
            "  PRIMARY KEY (`word_id`)\n" +
            ") ";

    public static final String createTableSql_ask_children = "CREATE TABLE `ask_children` (\n"+
            "  `ask_children_id` int(11) NOT NULL,\n"+
            "  `day_nums` int(11) NOT NULL,\n"+
            "  `no` int(11) NOT NULL,\n"+
            "  `eng_question` varchar(145) NOT NULL,\n"+
            "  `chi_question` varchar(145) NOT NULL,\n"+
            "  `relative_flag` varchar(1) DEFAULT NULL,\n"+
            "  `keywords` varchar(145) DEFAULT NULL,\n"+
            "  `answer1` varchar(45) DEFAULT NULL,\n"+
            "  `answer1_chi` varchar(45) DEFAULT NULL,\n"+
            "  `answer2` varchar(45) DEFAULT NULL,\n"+
            "  `answer2_chi` varchar(45) DEFAULT NULL,\n"+
            "  PRIMARY KEY (`ask_children_id`)\n"+
            ") ";
    public static final String createTableSql_ask_robot = "CREATE TABLE `ask_robot` (\n"+
            "  `ask_robot_id` int(11) NOT NULL,\n"+
            "  `catelogy` varchar(145) DEFAULT NULL,\n"+
            "  `english_question` varchar(145) NOT NULL,\n"+
            "  `chinese_question` varchar(145) NOT NULL,\n"+
            "  `key_words` varchar(145) DEFAULT NULL,\n"+
            "  `answer1` varchar(145) DEFAULT NULL,\n"+
            "  `answer1_chi` varchar(145) DEFAULT NULL,\n"+
            "  `answer2` varchar(145) DEFAULT NULL,\n"+
            "  `answer2_chi` varchar(145) DEFAULT NULL,\n"+
            "  PRIMARY KEY (`ask_robot_id`)\n"+
            ") ";
    public static final String createTableSql_word_games = "CREATE TABLE `word_games` (\n" +
            "  `word_games_id` int(11) NOT NULL,\n" +
            "  `subject_chi` varchar(45) NOT NULL,\n" +
            "  `subject_eng` varchar(45) NOT NULL,\n" +
            "  `word_no` int(11) NOT NULL,\n" +
            "  `words` varchar(45) NOT NULL,\n" +
            "  `tongyi` varchar(45) DEFAULT NULL,\n" +
            "  `chinese` varchar(45) NOT NULL,\n" +
            "  `sound_flag` varchar(1) DEFAULT NULL,\n" +
            "  `sound_file` varchar(145) DEFAULT NULL,\n" +
            "  `sound_url` varchar(145) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`word_games_id`)\n" +
            ")";
    public static final String createTableSql_vccs = "CREATE TABLE `vccs` (\n"+
            "  `vccs_id` int(11) NOT NULL,\n"+
            "  `table_name` varchar(100) DEFAULT NULL,\n"+
            "  `table_version` float DEFAULT NULL,\n"+
            "  `table_time` timestamp NOT NULL,\n"+
            "  PRIMARY KEY (`vccs_id`)\n"+
            ") ";
    public static final String createTableSql_province = "CREATE TABLE `province` (\n" +
            "  `id` int(11) NOT NULL ,\n" +
            "  `name` varchar(50) DEFAULT NULL,\n" +
            "   PRIMARY KEY (`id`)\n" +
            ") ";
    public static final String createTableSql_city = "CREATE TABLE `city` (\n" +
            "  `city_id` int(11) NOT NULL,\n" +
            "  `city_index` int(11) NOT NULL,\n" +
            "  `province_id` int(11) NOT NULL,\n" +
            "  `name` varchar(100) NOT NULL DEFAULT '',\n" +
            "  PRIMARY KEY (`city_id`)\n" +
            ") ";
    public static final String createTableSql_holiday = "CREATE TABLE `holiday` (\n" +
            "  `year` int(11) DEFAULT NULL,\n" +
            "  `YMD` varchar(40) NOT NULL,\n" +
            "  `comment` varchar(100) DEFAULT NULL\n" +
            ")";
    public static final String createTableSql_programming = "CREATE TABLE `programming` (\n" +
            "  `id` int(11) NOT NULL,\n" +
            "  `cate` varchar(200) NOT NULL,\n" +
            "  `title` varchar(200) NOT NULL,\n" +
            "  `english1` varchar(200) DEFAULT NULL,\n" +
            "  `chinese1` varchar(200) DEFAULT NULL,\n" +
            "  `english2` varchar(200) DEFAULT NULL,\n" +
            "  `chinese2` varchar(200) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ")";



    public static final String [] mGetUpWeekCall ={
            "Good morning, good morning Sunday morning.	新的一天开始了	A new day begins. Let's get up in music! ",
            "Good morning, good morning  Monday morning.	新的一天开始了	A new day starts. Let's get up in music!",
            "Good morning, good morning Tuesday morning.	新的一天开始了	A new day starts. Let's get up in music!",
            "Good morning, good morning Wednesday morning.	新的一天开始了	A new day begins. Let's get up in music!",
            "Good morning, good morning Thursday morning.	新的一天开始了	A new day begins. Let's get up in music!",
            "Good morning, good morning Friday morning	新的一天开始了	A new day starts. Let's get up in music!",
            "Good morning, good morning Saturday morning.	新的一天开始了	A new day starts. Let's get up in music!"
    };

    public static final String sqlSelectCityName = "SELECT name  FROM city,equips where equips.city_id = city.city_id and equips.user_id = " + getUserId();
}
