package com.ptsisi.daily;

public interface User extends Entity, TimeEntity {

  /**
   * @return the username
   */
  public String getUsername();

  /**
   * @param username the username to set
   */
  public void setUsername(String username);

  /**
   * @return the password
   */
  public String getPassword();

  /**
   * @param password the password to set
   */
  public void setPassword(String password);

  /**
   * @return the fullname
   */
  public String getFullname();

  /**
   * @param fullname the fullname to set
   */
  public void setFullname(String fullname);

  /**
   * @return the email
   */
  public String getEmail();

  /**
   * @param email the email to set
   */
  public void setEmail(String email);

}
