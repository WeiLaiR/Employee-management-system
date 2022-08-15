package com.weilai.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Information {

    //    id自行输入
    @TableId(type = IdType.INPUT)
    private Long eid;
    private Boolean sex;
    private Short age;
    private String education;
    private String phoneNumber;
    private String nickName;
    private String address;
    private String hobby;
    private String briefIntroduction;
    private String remarks;
    //    注解优先级高于yml配置文件中的设置！
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    //    逻辑删除
    @TableLogic
//    不展示逻辑删除位
    @JsonIgnore
    private Boolean isDelete;

}
