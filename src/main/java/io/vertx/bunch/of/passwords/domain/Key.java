package io.vertx.bunch.of.passwords.domain;

import java.util.Date;
import java.util.Objects;

public class Key {

  private String login;
  private String password;
  private String link;
  private Date lastEdit;

  public Key() {

  }

  public Key(String login, String password, String link, Date lastEdit) {
    this.login = login;
    this.password = password;
    this.link = link;
    this.lastEdit = lastEdit;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public Date getLastEdit() {
    return lastEdit;
  }

  public void setLastEdit(Date lastEdit) {
    this.lastEdit = lastEdit;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Key key = (Key) o;
    return Objects.equals(login, key.login) &&
      Objects.equals(password, key.password) &&
      Objects.equals(link, key.link) &&
      Objects.equals(lastEdit, key.lastEdit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(login, password, link, lastEdit);
  }

  @Override
  public String toString() {
    return "Key{" +
      "login='" + login + '\'' +
      ", password='" + password + '\'' +
      ", link='" + link + '\'' +
      ", lastEdit=" + lastEdit +
      '}';
  }
}
