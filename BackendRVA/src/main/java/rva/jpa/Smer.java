package rva.jpa;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the "smer" database table.
 * 
 */
@Entity
@Table(name="\"smer\"")
@NamedQuery(name="Smer.findAll", query="SELECT s FROM Smer s")
public class Smer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SMER_ID_GENERATOR", sequenceName="SMER_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SMER_ID_GENERATOR")
	@Column(name="\"id\"")
	private int id;

	@Column(name="\"naziv\"")
	private String naziv;

	@Column(name="\"oznaka\"")
	private String oznaka;

	//bi-directional many-to-one association to Grupa
	@OneToMany(mappedBy="smer")
	@JsonIgnore
	private List<Grupa> grupas;
	

	public Smer() {
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

	public String getOznaka() {
		return this.oznaka;
	}

	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}

	public List<Grupa> getGrupas() {
		return this.grupas;
	}

	public void setGrupas(List<Grupa> grupas) {
		this.grupas = grupas;
	}

	public Grupa addGrupa(Grupa grupa) {
		getGrupas().add(grupa);
		grupa.setSmer(this);

		return grupa;
	}

	public Grupa removeGrupa(Grupa grupa) {
		getGrupas().remove(grupa);
		grupa.setSmer(null);

		return grupa;
	}

}