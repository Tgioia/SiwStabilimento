package it.uniroma3.siw.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Proprietario extends User {

    @OneToMany(mappedBy = "proprietario", cascade = CascadeType.ALL)
    private List<Stabilimento> stabilimenti;



    public List<Stabilimento> getStabilimenti() {
        return stabilimenti;
    }

    public void setStabilimenti(List<Stabilimento> stabilimenti) {
        this.stabilimenti = stabilimenti;
    }
}
