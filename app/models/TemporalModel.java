package models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tripti on 21/05/18.
 */
//Directly updated in DB by making changes in sql
//https://medium.com/@bengarvey/use-an-updated-at-column-in-your-mysql-table-and-make-it-update-automatically-6bf010873e6a
@MappedSuperclass
public class TemporalModel {
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    public Date updatedAt;

    @Column(name = "created_at",updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date createdAt;

    @PrePersist
    public void onPrePersist(){
        this.createdAt = new Date();;
        this.updatedAt = new Date();;
    }

    @PreUpdate
    public void onPreUpdate(){
        this.updatedAt = new Date();;
    }

    public long getCreationTime(){
        if(this.createdAt != null){
            return this.createdAt.getTime()/1000L;
        }
        return -1L;
    }
}
