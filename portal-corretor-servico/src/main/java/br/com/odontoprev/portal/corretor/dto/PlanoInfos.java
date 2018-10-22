package br.com.odontoprev.portal.corretor.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class PlanoInfos implements Serializable {

	private static final long serialVersionUID = -853705059870114824L;
	
	List<PlanoInfo> planoInfos;

    public List<PlanoInfo> getPlanoInfos() {
        return planoInfos;
    }

    public void setPlanoInfos(List<PlanoInfo> planoInfos) {
        this.planoInfos = planoInfos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanoInfos that = (PlanoInfos) o;
        return Objects.equals(planoInfos, that.planoInfos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planoInfos);
    }

    @Override
    public String toString() {
        return "PlanoInfos{" +
                "planoInfos=" + planoInfos +
                '}';
    }
}
