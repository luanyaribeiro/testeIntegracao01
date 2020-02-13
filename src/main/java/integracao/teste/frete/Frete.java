package integracao.teste.frete;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;

@Entity
public class Frete {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	@NotNull(message = "A cidade deve ser preenchida")
	@ManyToOne(cascade= {CascadeType.MERGE})
	private Cidade cidade;
	
	@NotNull(message = "O cliente deve ser preenchido")
	@ManyToOne(cascade= {CascadeType.MERGE})
	private Cliente cliente;

	@NotEmpty(message="A descrição deve ser preenchida")
	private String descricao;

	@NotNull(message = "O peso deve ser preenchido")
	@DecimalMin(value = "0.1", message = "O peso deve ser maior ou igual a 0.1")
	private Double peso;

	@NotNull(message = "O valor deve ser preenchido")
	@DecimalMin(value = "0.1", message = "O valor deve ser maior ou igual a 0.1")
	private Double valor;

	public Frete() {}

	public Frete(Cidade cidade, Cliente cliente, String descricao, Double peso, Double valor) {
		this.cidade = cidade;
		this.cliente = cliente;
		this.descricao = descricao;		
		this.peso = peso;
		this.valor = valor;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

}
