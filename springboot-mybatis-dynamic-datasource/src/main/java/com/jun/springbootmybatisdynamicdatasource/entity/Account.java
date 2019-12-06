package com.jun.springbootmybatisdynamicdatasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Timestamp;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author JZxiaoxiao
 * @since 2019-12-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("account")
@ApiModel(value="Account对象", description="用户表")
public class Account extends Model<Account> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "itemID", type = IdType.AUTO)
    private String itemID;

    @ApiModelProperty(value = "年龄")
    @TableField("age")
    private String age;

    @ApiModelProperty(value = "名字")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private String status;

    @ApiModelProperty(value = "创建时间")
    @TableField("createDate")
    private Timestamp createDate;


    @Override
    protected Serializable pkVal() {
        return this.itemID;
    }

}
