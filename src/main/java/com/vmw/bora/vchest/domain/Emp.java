package com.vmw.bora.vchest.domain;

import java.nio.ByteBuffer;
import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class Emp {

    @PrimaryKey
    @Field
    protected String id;

    @Field
    protected String username;

    @Field
    protected Date joinDate;

    @Field
    protected Double storageSize;

    //@Field
    protected ByteBuffer content;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Double getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(Double storageSize) {
        this.storageSize = storageSize;
    }

    public ByteBuffer getContent() {
        return content;
    }

    public void setContent(ByteBuffer content) {
        this.content = content;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Emp other = (Emp) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Emp [id=" + id + ", username=" + username + ", joinDate="
                + joinDate + ", storageSize=" + storageSize + ", content=" + content + "]";
    }

}
