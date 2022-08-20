package com.hpi.modules.org.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 组织机构
 * </p>
 *
 * @author dhj
 * @since 2022-08-20
 */
public class Sysorg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 组织机构ID
     */
    private String orgid;

    /**
     * 组织机构名称
     */
    private String orgname;

    /**
     * 组织机构编号
     */
    private String orgno;

    /**
     * 上级组织机构ID
     */
    private String porgid;

    /**
     * 类型
     */
    private String orgtype;

    /**
     * 是否是公司（1：是；0：否）
     */
    private String iscompany;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 状态
     */
    private String status;

    /**
     * 序号
     */
    private BigDecimal seq;

    /**
     * 创建人
     */
    private String createby;

    /**
     * 创建日期
     */
    private Date createdate;

    /**
     * 微信部门对应ID
     */
    private BigDecimal wxdeptid;

    /**
     * 删除标志（0：未删除；1：删除）
     */
    private BigDecimal delflag;

    /**
     * 备注
     */
    private String remark;

    private String deptid;

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }
    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }
    public String getOrgno() {
        return orgno;
    }

    public void setOrgno(String orgno) {
        this.orgno = orgno;
    }
    public String getPorgid() {
        return porgid;
    }

    public void setPorgid(String porgid) {
        this.porgid = porgid;
    }
    public String getOrgtype() {
        return orgtype;
    }

    public void setOrgtype(String orgtype) {
        this.orgtype = orgtype;
    }
    public String getIscompany() {
        return iscompany;
    }

    public void setIscompany(String iscompany) {
        this.iscompany = iscompany;
    }
    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public BigDecimal getSeq() {
        return seq;
    }

    public void setSeq(BigDecimal seq) {
        this.seq = seq;
    }
    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }
    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
    public BigDecimal getWxdeptid() {
        return wxdeptid;
    }

    public void setWxdeptid(BigDecimal wxdeptid) {
        this.wxdeptid = wxdeptid;
    }
    public BigDecimal getDelflag() {
        return delflag;
    }

    public void setDelflag(BigDecimal delflag) {
        this.delflag = delflag;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    @Override
    public String toString() {
        return "Sysorg{" +
            "orgid=" + orgid +
            ", orgname=" + orgname +
            ", orgno=" + orgno +
            ", porgid=" + porgid +
            ", orgtype=" + orgtype +
            ", iscompany=" + iscompany +
            ", leader=" + leader +
            ", status=" + status +
            ", seq=" + seq +
            ", createby=" + createby +
            ", createdate=" + createdate +
            ", wxdeptid=" + wxdeptid +
            ", delflag=" + delflag +
            ", remark=" + remark +
            ", deptid=" + deptid +
        "}";
    }
}
