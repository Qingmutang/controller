package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "private_messages")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PrivateMessage extends AuditableEntity<UserAccount, Long> {

  private static final long serialVersionUID = 1L;

  public enum Type {

	NOTIFICATION,
	MESSAGE
  }

  @Column(name = "title")
  private String title;

  @Column(name = "content")
  private String content;

  @Column(name = "is_read")
  private boolean read = false;

  @ManyToOne
  @JoinColumn(name = "to_id")
  private UserAccount to;

  @ManyToOne
  @JoinColumn(name = "from_id")
  private UserAccount from;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private Type type = Type.NOTIFICATION;

  public PrivateMessage(String title, String content) {
	this.title = title;
	this.content = content;
  }


}
