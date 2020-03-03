package rva.jpa;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the "projekat" database table.
 * 
 */
@Entity
@Table(name="\"projekat\"")
@NamedQuery(name="Projekat.findAll", query="SELECT p FROM Projekat p")
public class Projekat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PROJEKAT_ID_GENERATOR", sequenceName="PROJEKAT_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROJEKAT_ID_GENERATOR")
	@Column(name="\"id\"")
	private int id;

	@Column(name="\"naziv\"")
	private String naziv;

	@Column(name="\"opis\"")
	private String opis;

	@Column(name="\"oznaka\"")
	private String oznaka;

	//bi-directional many-to-one association to Student
	@OneToMany(mappedBy="projekat")
	@JsonIgnore
	private List<Student> students;

	public Projekat() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getOznaka() {
		return this.oznaka;
	}

	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}

	public List<Student> getStudents() {
		return this.students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public Student addStudent(Student student) {
		getStudents().add(student);
		student.setProjekat(this);

		return student;
	}

	public Student removeStudent(Student student) {
		getStudents().remove(student);
		student.setProjekat(null);

		return student;
	}

}