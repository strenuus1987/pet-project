package domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="departments")
public class Department implements Serializable {

	private static final long serialVersionUID = 7344978288187655508L;

	@Id
	@GeneratedValue(generator = "increment2")
	@GenericGenerator(name = "increment2", strategy = "increment")
	private Long id;
	
	@Column(name="name")
	private String name;
	
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

	public Department(String name) {
		this.name = name;
	}
	
	public Department() {};

	public String toPage(String sourcePage) {
		
		StringBuilder sb = new StringBuilder("<td>");
		sb.append("<a href=\"department?id=");
		sb.append(getId());
		sb.append("&source=");
		sb.append(sourcePage);
		sb.append("\">");
		sb.append(getName());
		sb.append("</a>");
		sb.append("</td>");
				
		return sb.toString();
	}
	
}
