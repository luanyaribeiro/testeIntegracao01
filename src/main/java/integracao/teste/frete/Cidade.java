package integracao.teste.frete;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Cidade {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	@NotEmpty(message = "O nome deve ser preenchido")
	private String nome;

	@NotEmpty(message = "A UF deve ser preenchida")
	private String uf;

	@NotNull(message = "A taxa deve ser preenchida")
	@DecimalMin(value = "0.1", message = "A taxa deve ser maior ou igual a 0.1")
	private Double taxa;

	public Cidade() {}

	public Cidade(String nome, String uf, Double taxa) {
		this.nome = nome;
		this.uf = uf;
		this.taxa = taxa;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public Double getTaxa() {
		return taxa;
	}

	public void setTaxa(Double taxa) {
		this.taxa = taxa;
	}

}
