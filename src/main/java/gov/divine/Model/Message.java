package gov.divine.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "message_id")
    private long id;
    @Column(name ="value", columnDefinition="TEXT")
    private String value;
    private String theme;
    private String file;
    private long senderUserId;
    private long receiverUserId;
    private boolean isRevision;
    private String receiverFullName;
    private String senderFullName;
    private Date sendDate = new Date();

    public Message() {
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public boolean isRevision() {
        return isRevision;
    }

    public void setRevision(boolean revision) {
        isRevision = revision;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public long getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(long senderUserId) {
        this.senderUserId = senderUserId;
    }

    public long getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(long receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public String getReceiverFullName() {
        return receiverFullName;
    }

    public void setReceiverFullName(String receiverFullName) {
        this.receiverFullName = receiverFullName;
    }

    public String getSenderFullName() {
        return senderFullName;
    }

    public void setSenderFullName(String senderFullName) {
        this.senderFullName = senderFullName;
    }
}
