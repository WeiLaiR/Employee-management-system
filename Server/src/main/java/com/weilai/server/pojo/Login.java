package com.weilai.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {

    //    使用了自定义id生成器
    @TableId(type = IdType.ASSIGN_ID)
    private Long eid;
    private String email;
    private String password;
    private Integer level;
//    逻辑删除
    @TableLogic
    private Boolean isDelete;


}
