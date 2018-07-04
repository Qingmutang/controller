package com.bjpowernode.p2p.admin.model;

public class DictionaryInfo {
    private Integer id;

    private Integer type;

    private Integer typevalue;

    private String typename;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTypevalue() {
        return typevalue;
    }

    public void setTypevalue(Integer typevalue) {
        this.typevalue = typevalue;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}