package com.ptsisi.daily.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.ptsisi.daily.User;
import org.hibernate.validator.constraints.Length;

@Entity(name = "org.ptsisi.keyword.User")
@Table(name = "pt_users")
public class UserBean extends IntegerIdTimeObject implements User {

  private static final long serialVersionUID = -3737262583650533418L;

  @Column(length = 20, unique = true, nullable = false)
  private String username;
  @Column(length = 100, nullable = false)
  private String password;
  @Column(length = 100, nullable = false)
  private String fullname;
  @Column(length = 50, nullable = false)
  private String email;

  /*
   * (non-Javadoc)
   * @see com.ptsisi.daily.model.User#getUsername()
   */
  public String getUsername() {
    return username;
  }

  /*
   * (non-Javadoc)
   * @see com.ptsisi.daily.model.User#setUsername(java.lang.String)
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /*
   * (non-Javadoc)
   * @see com.ptsisi.daily.model.User#getPassword()
   */
  public String getPassword() {
    return password;
  }

  /*
   * (non-Javadoc)
   * @see com.ptsisi.daily.model.User#setPassword(java.lang.String)
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /*
   * (non-Javadoc)
   * @see com.ptsisi.daily.model.User#getFullname()
   */
  public String getFullname() {
    return fullname;
  }

  /*
   * (non-Javadoc)
   * @see com.ptsisi.daily.model.User#setFullname(java.lang.String)
   */
  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  /*
   * (non-Javadoc)
   * @see com.ptsisi.daily.model.User#getEmail()
   */
  public String getEmail() {
    return email;
  }

  /*
   * (non-Javadoc)
   * @see com.ptsisi.daily.model.User#setEmail(java.lang.String)
   */
  public void setEmail(String email) {
    this.email = email;
  }

}
