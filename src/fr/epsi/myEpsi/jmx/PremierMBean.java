package fr.epsi.myEpsi.jmx;

public interface PremierMBean {

	public String getName();
	public int getValue();
	public void setValue(int valeur);
	public void refresh();
	public Integer getNumberFfAnnonces();
	public void pushAdminAnnonces(String AnnoncesContenu);
}
