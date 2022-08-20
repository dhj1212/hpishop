package com.hpi.modules.syscode.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 数据字典代码
 * </p>
 *
 * @author dhj
 * @since 2022-08-20
 */
@Getter
@Setter
@TableName("SYSCODE")
public class Syscode implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 代码
     */
    @TableId(value = "CODEID", type = IdType.ASSIGN_UUID)
    private String codeid;

    /**
     * 类型序号
     */
    @TableField("CODETYPESEQ")
    private BigDecimal codetypeseq;

    /**
     * 代码类别
     */
    @TableField("CODETYPE")
    private String codetype;

    /**
     * 代码名称
     */
    @TableField("CODENAME")
    private String codename;

    /**
     * 更新人
     */
    @TableField("UPDATEMAN")
    private String updateman;

    /**
     * 更新时间
     */
    @TableField("UPDATEDATE")
    private LocalDateTime updatedate;

    /**
     * 状态
     */
    @TableField("STATUS")
    private String status;

    /**
     * 说明
     */
    @TableField("COMMENTS")
    private String comments;


}
