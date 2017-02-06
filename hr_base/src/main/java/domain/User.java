package domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="users")
public class User implements Serializable {

	private static final long serialVersionUID = -7347462736861499932L;

	@Id
	@GeneratedValue(generator = "increment2")
	@GenericGenerator(name = "increment2", strategy = "increment")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="editor")
	private Boolean editor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getEditor() {
		return editor;
	}

	public void setEditor(Boolean editor) {
		this.editor = editor;
	}

	public User(String name, Boolean editor) {
		this.name = name;
		this.editor = editor;
	}
	
	public User(String name) {
		this(name, false);
	}
	
	public User() {};
	
}
