package com.weilai.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    //    ID自动生成，使用雪花算法
    @TableId(type = IdType.ASSIGN_ID)
    private Long eid;
    private String name;
    private String department;
    private String post;
    private Date create_time;
    private Date update_time;
//    逻辑删除
    @TableLogic
    private Boolean is_delete;
}
