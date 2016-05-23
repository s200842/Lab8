package it.polito.tdp.metrodeparis.model;

public class FermataSuLinea extends Fermata {

	private Linea linea;

	public FermataSuLinea(int idFermata, String nome, double coordx, double coordy, Linea linea) {
		super(idFermata, nome, coordx, coordy);
		this.linea = linea;
	}

	public FermataSuLinea(Fermata fermata, Linea linea) {
		super(fermata.getIdFermata(), fermata.getNome(), fermata.getCoordX(), fermata.getCoordY());
		this.linea = linea;
	}

	public void setLinea(Linea linea) {
		this.linea = linea;
	}

	public Linea getLinea() {
		return this.linea;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((linea == null) ? 0 : linea.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FermataSuLinea other = (FermataSuLinea) obj;
		if (linea == null) {
			if (other.linea != null)
				return false;
		} else if (!linea.equals(other.linea))
			return false;
		return true;
	}

}
