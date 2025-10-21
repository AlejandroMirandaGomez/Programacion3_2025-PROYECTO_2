package PROYECTO_2.presentation.medicos;

import PROYECTO_2.logic.Service;
import pos.logic.Medico;
import pos.logic.Usuario;

import java.util.List;

public class Controller_Medicos {
    View_Medicos view;
    Model_Medicos  model;

    public Controller_Medicos(View_Medicos view, Model_Medicos model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
        getMedicos();
    }

    public void create(Medico e) throws Exception{
        Service.getInstance().create(e);
        model.setCurrent(new Medico());
        model.setMedicos(Service.getInstance().findAll());
    }

    public void createUser(Usuario e) throws Exception{
        Service.getInstance().create(e);

    }

    public void read(String id) throws Exception{
        Medico m = new Medico();
        m.setId(id);
        try {
            model.setCurrent(Service.getInstance().read(m));
        } catch (Exception ex) {
            Medico b = new Medico();
            b.setId(id);
            model.setCurrent(b);
            throw ex;
        }
    }

    public void clear() {
        model.setCurrent(new Medico());
    }

    public void getMedicos(){
        List<Medico> list = Service.getInstance().findAll();
        model.setMedicos(list);
    }

    public void filtrarMedicos(String tipo, String texto){
        List<Medico> list = Service.getInstance().filtrarMedicos(tipo, texto);
        model.setMedicos(list);
    }

    public void remove(Medico e) throws Exception{
        Service.getInstance().removeMedico(e);
        model.setCurrent(new Medico());
        model.setMedicos(Service.getInstance().findAll());
    }

    public void edit(Medico e, Medico n) throws Exception{
        Service.getInstance().update(n);
    }

    public void selectCurrent(Medico n){
        model.setCurrent(n);
    }
}
