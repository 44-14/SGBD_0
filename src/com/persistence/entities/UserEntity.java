package com.persistence.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "schoolux", catalog = "")
public class UserEntity {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String phoneNumber;
    private Date birthdate;
    private String gender;
    private String emailAddress;
    private boolean active;
    private Timestamp inscriptionDate;
    private String title;
    private String photo;
    private Integer idRole;
    private Integer idParent;
    private RoleEntity rolesByIdRole;
    private UserEntity usersByIdParent;
    private Collection<UserEntity> usersById;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = 50)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = false, length = 50)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 50)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 50)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "phone_number", nullable = true, length = 25)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "birthdate", nullable = false)
    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Basic
    @Column(name = "gender", nullable = false)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "email_address", nullable = false, length = 100)
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Basic
    @Column(name = "active", nullable = false)
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Basic
    @Column(name = "inscription_date", nullable = false)
    public Timestamp getInscriptionDate() {
        return inscriptionDate;
    }

    public void setInscriptionDate(Timestamp inscriptionDate) {
        this.inscriptionDate = inscriptionDate;
    }

    @Basic
    @Column(name = "title", nullable = true, length = 50)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "photo", nullable = true, length = 255)
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Basic
    @Column(name = "id_role", nullable = true)
    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    @Basic
    @Column(name = "id_parent", nullable = true)
    public Integer getIdParent() {
        return idParent;
    }

    public void setIdParent(Integer idParent) {
        this.idParent = idParent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id &&
                active == that.active &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(birthdate, that.birthdate) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(emailAddress, that.emailAddress) &&
                Objects.equals(inscriptionDate, that.inscriptionDate) &&
                Objects.equals(title, that.title) &&
                Objects.equals(photo, that.photo) &&
                Objects.equals(idRole, that.idRole) &&
                Objects.equals(idParent, that.idParent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, username, password, phoneNumber, birthdate, gender, emailAddress, active, inscriptionDate, title, photo, idRole, idParent);
    }

    @ManyToOne
    @JoinColumn(name = "id_role", referencedColumnName = "id")
    public RoleEntity getRolesByIdRole() {
        return rolesByIdRole;
    }

    public void setRolesByIdRole(RoleEntity rolesByIdRole) {
        this.rolesByIdRole = rolesByIdRole;
    }

    @ManyToOne
    @JoinColumn(name = "id_parent", referencedColumnName = "id")
    public UserEntity getUsersByIdParent() {
        return usersByIdParent;
    }

    public void setUsersByIdParent(UserEntity usersByIdParent) {
        this.usersByIdParent = usersByIdParent;
    }

    @OneToMany(mappedBy = "usersByIdParent")
    public Collection<UserEntity> getUsersById() {
        return usersById;
    }

    public void setUsersById(Collection<UserEntity> usersById) {
        this.usersById = usersById;
    }
}
