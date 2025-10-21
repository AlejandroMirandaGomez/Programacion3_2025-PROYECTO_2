package PROYECTO_2.presentation.farmaceutas;

import PROYECTO_2.logic.Service;
import pos.logic.Farmaceuta;
import pos.logic.Usuario;

import java.util.List;

public class Controller_Farmaceutas {
    View_Farmaceutas view;
    Model_Farmaceutas model;

    public Controller_Farmaceutas(View_Farmaceutas view, Model_Farmaceutas model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
        getFarmaceutas();
    }

    public void create(Farmaceuta e) throws Exception{
        Service.getInstance().create(e);
        model.setCurrent(new Farmaceuta());
        model.setFarmaceutas(Service.getInstance().findAllFarmaceutas());
    }

    public void createUser(Usuario e) throws Exception{
        Service.getInstance().create(e);

    }

    public void clear() {
        model.setCurrent(new Farmaceuta());
    }

    public void getFarmaceutas(){
        List<Farmaceuta> list = Service.getInstance().findAllFarmaceutas();
        model.setFarmaceutas(list);
    }

    public void filtrarFarmaceutas(String tipo, String texto){
        List<Farmaceuta> list = Service.getInstance().filtrarFarmaceutas(tipo, texto);
        model.setFarmaceutas(list);
    }

    public void remove(Farmaceuta e) throws Exception{
        Service.getInstance().removeFarmaceuta(e);
        model.setCurrent(new Farmaceuta());
        model.setFarmaceutas(Service.getInstance().findAllFarmaceutas());
    }

    public void edit(Farmaceuta n) throws Exception{
        Service.getInstance().update(n);
        model.setCurrent(new Farmaceuta());
        model.setFarmaceutas(Service.getInstance().findAllFarmaceutas());

    }

    public void selectCurrent(Farmaceuta n){
        model.setCurrent(n);
    }


}
