-- health_code_user数据库
CREATE DATABASE IF NOT EXISTS health_code_user;
USE health_code_user;
-- 用户 ID 映射表
CREATE TABLE uid_mapping (
    uid BIGINT PRIMARY KEY COMMENT '用户唯一标识',
    openid VARCHAR(255) NOT NULL UNIQUE COMMENT '用户的微信唯一标识',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间'
);

-- 用户信息表
CREATE TABLE user_info (
    uid BIGINT PRIMARY KEY COMMENT '用户唯一标识',
    identity_card VARCHAR(18) NOT NULL UNIQUE COMMENT '用户身份证号码',
    phone_number VARCHAR(11) NOT NULL UNIQUE COMMENT '用户手机号码',
    name VARCHAR(255) NOT NULL COMMENT '用户姓名',
    area_id BIGINT NOT NULL COMMENT '用户所在区域的 ID',
    address VARCHAR(255) NOT NULL COMMENT '用户详细地址',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间'
);

-- 区域编码表（新增）
CREATE TABLE area_code (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '区域编码记录的唯一标识',
    district INT NOT NULL COMMENT '区编号',
    street INT NOT NULL COMMENT '街道编号',
    community BIGINT NOT NULL COMMENT '社区编号',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
    INDEX area_code_idx_street (street),
    INDEX area_code_idx_district (district),
    INDEX area_code_idx_community (community)
);

-- 核酸检测人员表
CREATE TABLE nucleic_acid_test_personnel (
    tid BIGINT PRIMARY KEY COMMENT '核酸检测人员唯一标识',
    name VARCHAR(255) NOT NULL COMMENT '核酸检测人员姓名',
    identity_card VARCHAR(18) NOT NULL UNIQUE COMMENT '核酸检测人员身份证号码',
    password_hash VARCHAR(255) COMMENT '核酸检测人员登录密码的哈希值',
    status BOOLEAN NOT NULL COMMENT 'false：不可登录，true：可以登录',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间'
);

-- 健康码管理人员表
CREATE TABLE health_code_manager (
    mid BIGINT PRIMARY KEY COMMENT '健康码管理人员唯一标识',
    name VARCHAR(255) NOT NULL COMMENT '健康码管理人员姓名',
    identity_card VARCHAR(18) NOT NULL UNIQUE COMMENT '健康码管理人员身份证号码',
    password_hash VARCHAR(255) NOT NULL COMMENT '健康码管理人员登录密码的哈希值',
    status BOOLEAN NOT NULL COMMENT 'false：不可登录，true：可以登录',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间'
);

-- health_code_health_code数据库
CREATE DATABASE IF NOT EXISTS health_code_health_code;
USE health_code_health_code;
-- 健康码表
CREATE TABLE health_code (
    uid BIGINT PRIMARY KEY COMMENT '用户唯一标识',
    color INT NOT NULL COMMENT '健康码颜色，0：红色，1：黄色，2：绿色',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间'
);

-- 申诉信息表（新增）
CREATE TABLE appeal_log (
    appeal_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '申诉记录的唯一标识，自增主键',
    uid BIGINT NOT NULL COMMENT '用户唯一标识',
    appeal_reason TEXT NOT NULL COMMENT '申诉的具体原因，可记录较长文本',
    appeal_materials LONGTEXT COMMENT '申诉时提交的相关材料，以 Base64 编码形式存储',
    appeal_status INT NOT NULL COMMENT '0: 未处理, 1: 已处理',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间'
) COMMENT '该表用于记录用户的申诉信息，包括申诉原因、材料、状态以及创建和更新时间等';

-- health_code_itinerary_code数据库
CREATE DATABASE IF NOT EXISTS health_code_itinerary_code;
USE health_code_itinerary_code;
-- 行程码表
CREATE TABLE itinerary_code (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '行程码记录的唯一标识',
    uid BIGINT NOT NULL COMMENT '用户唯一标识',
    place INT NOT NULL COMMENT '行程地点编号',
    time DATE DEFAULT (CURDATE()) COMMENT '当前日期',
    INDEX itinerary_code_idx_uid (uid)
);

-- health_code_place_code数据库
CREATE DATABASE IF NOT EXISTS health_code_place_code;
USE health_code_place_code;
-- 场所信息表
CREATE TABLE place_info (
    pid BIGINT PRIMARY KEY COMMENT '场所唯一标识',
    uid BIGINT COMMENT '关联的场所负责人用户 ID',
    place_name VARCHAR(64) COMMENT '场所名称',
    area_id BIGINT NOT NULL COMMENT '场所所在区域的 ID',
    address VARCHAR(255) NOT NULL COMMENT '场所详细地址',
    status BOOLEAN NOT NULL COMMENT 'false：关停，true：开启',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
    INDEX place_info_idx_uid (uid)
);

-- 场所映射表
CREATE TABLE place_mapping (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '场所映射记录的唯一标识',
    pid BIGINT COMMENT '场所唯一标识',
    uid BIGINT COMMENT '用户唯一标识',
    time DATE DEFAULT (CURDATE()) COMMENT '用户访问场所的日期',
    INDEX place_mapping_idx_time (time),
    INDEX place_mapping_idx_pid (pid),
    INDEX place_mapping_idx_uid (uid, time)
);

-- health_code_nucleic_acids数据库
CREATE DATABASE IF NOT EXISTS health_code_nucleic_acids;
USE health_code_nucleic_acids;

-- 核酸检测记录表
CREATE TABLE nucleic_acid_test (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '核酸检测记录的唯一标识',
    uid BIGINT NOT NULL COMMENT '用户唯一标识',
    tid BIGINT NOT NULL COMMENT '核酸检测人员唯一标识',
    tubeid BIGINT NOT NULL COMMENT '检测管编号',
    test_address VARCHAR(255) NOT NULL COMMENT '核酸检测地址',
    re_test BOOLEAN COMMENT 'false：未复检，true：已复检',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
    INDEX nucleic_acid_test_idx_tubeid (tubeid),
    INDEX nucleic_acid_test_idx_uid_created_at (uid, created_at DESC)
);

-- 检测管信息表（新增）
CREATE TABLE tube_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '检测管信息记录的唯一标识',
    tubeid BIGINT NOT NULL COMMENT '检测管编号',
    kind INT NOT NULL COMMENT '0 单管， 1 十人混管， 2 二十人混管',
    result INT COMMENT '0：阴性，1：阳性，2未出',
    testing_organization VARCHAR(255) COMMENT '检测机构名称',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
    INDEX tube_info_idx_tubeid (tubeid),
    INDEX tube_info_idx_created_at (created_at DESC)
);

-- 核酸检测机构（新增）
CREATE TABLE nucleic_acid_testing_institutions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '核酸检测机构的唯一标识',
    name VARCHAR(255) NOT NULL COMMENT '机构名称',
    area_id BIGINT NOT NULL COMMENT '所在区域的 ID',
    address VARCHAR(255) NOT NULL COMMENT '详细地址',
    test_time VARCHAR(255) NOT NULL COMMENT '检测时间',
    contact_number VARCHAR(255) NOT NULL COMMENT '联系电话',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间'
);

-- health_code_vaccine_inoculation数据库
CREATE DATABASE IF NOT EXISTS health_code_vaccine_inoculation;
USE health_code_vaccine_inoculation;

-- 疫苗接种站点表（新增）
CREATE TABLE  vaccination_site (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '疫苗接种点的唯一标识',
    name VARCHAR(255) NOT NULL COMMENT '疫苗接种点名称',
    area_id BIGINT NOT NULL COMMENT '疫苗接种点所在区域的 ID',
    address VARCHAR(255) NOT NULL COMMENT '疫苗接种点详细地址',
    appointment_time VARCHAR(255) NOT NULL COMMENT '疫苗接种点可预约时间段'
);

-- 疫苗接种记录表（新增）
CREATE TABLE vaccination_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '疫苗接种记录唯一标识',
    uid BIGINT NOT NULL COMMENT '接种用户唯一标识',
    name VARCHAR(255) NOT NULL COMMENT '疫苗接种点名称',
    vaccine_type VARCHAR(255) NOT NULL COMMENT '疫苗种类',
    vacc_date DATE NOT NULL COMMENT '接种日期'
);