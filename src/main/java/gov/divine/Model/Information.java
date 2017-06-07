package gov.divine.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Information {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "information_id")
    private long id;
    @Column(name = "value", columnDefinition = "TEXT")
    private String value;
    private String theme;
    private String file;
    private long userId;
    private String userFullName;
    private Date sendDate = new Date();

    public Information() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }
}
