package integracao.teste.exception;

public class FreteException extends Exception {

	private static final long serialVersionUID = 1L;

	public FreteException(Exception e) throws FreteException {
		super(e);
	}
	
	public FreteException(String erro) throws FreteException {
		super(erro);
	}

}
