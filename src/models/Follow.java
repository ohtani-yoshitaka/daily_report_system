package models;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "follows")

@NamedQueries({
@NamedQuery(
        name = "getFollowList",
        query = "SELECT f FROM Follow AS f WHERE f.employee = :employee ORDER BY f.id DESC"
        ),
@NamedQuery(
        name = "getFollowerCount",
        query = "SELECT COUNT(f) FROM Follow AS f WHERE f.following = :employee"
        ),
@NamedQuery(
        name = "checkFollow",
        query = "SELECT f FROM Follow AS f WHERE f.employee = :employee AND f.following = :following"
        )


})
public class Follow {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "following", nullable = false)
    private Employee following;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "follow_date", nullable = false)
    private Date follow_date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getFollowing() {
        return following;
    }

    public void setFollowing(Employee following) {
        this.following = following;
    }


    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Date getFollow_date() {
        return follow_date;
    }

    public void setFollow_date(Date follow_date) {
        this.follow_date = follow_date;
    }


}
