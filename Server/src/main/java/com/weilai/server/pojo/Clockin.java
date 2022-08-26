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
public class Clockin {
    @TableId(type = IdType.AUTO)
    private Long cid;
    private Long eid;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date clockInTime;

    //    逻辑删除
    @TableLogic
//    不展示逻辑删除位
    @JsonIgnore
    private Boolean isDelete;
}
