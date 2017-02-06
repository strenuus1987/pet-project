package domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="employees")
public class Employee implements Serializable {

	private static final long serialVersionUID = -8312017444920713186L;

	@Id
	@GeneratedValue(generator = "increment2")
	@GenericGenerator(name = "increment2", strategy = "increment")
	private Long id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="salary")
	private Double salary;
	
	@Column(name="birthdate")
	private GregorianCalendar birthdate;
	
	@Column(name="active")
	private Boolean active;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="department_id")
	private Department department;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public GregorianCalendar getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(GregorianCalendar birthdate) {
		this.birthdate = birthdate;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Employee(String firstName, String lastName, Double salary, GregorianCalendar birthdate, Boolean active,
			Department department) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
		this.birthdate = birthdate;
		this.active = active;
		this.department = department;
	}
	
	public Employee() {
		
	}

	public String getBirthdateToPage() {
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		return ft.format(getBirthdate().getTime());
	}
	
	public String toPage(Boolean isEdit) {
		
		StringBuilder sb = new StringBuilder("<td>");
		sb.append("<a href=\"employee?id=");
		sb.append(getId());
		sb.append("\">");
		sb.append(getFirstName());
		sb.append("</a>");
		sb.append("</td><td>");
		sb.append("<a href=\"employee?id=");
		sb.append(getId());
		sb.append("\">");
		sb.append(getLastName());
		sb.append("</a>");
		sb.append("</td><td>");
		
		if (isEdit) {
			sb.append("<a href=\"department?id=");
			sb.append(getDepartment().getId());
			sb.append("&source=");
			sb.append("employees");
			sb.append("\">");
			sb.append(getDepartment().getName());
			sb.append("</a>");
		}
		else {
			sb.append(getDepartment().getName());	
		}
		
		sb.append("</td><td>");
		
		GregorianCalendar date = getBirthdate();
		SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy");
	    fmt.setCalendar(date);
		sb.append(fmt.format(date.getTime()));
		
		sb.append("</td><td>");
		sb.append(String.format("%.2f", getSalary()));
		sb.append("</td>");
		
		return sb.toString();
	}
	
}
