package edu.dosw.service;

import edu.dosw.dto.RecetaRequest;
import edu.dosw.model.Receta;
import edu.dosw.model.RecetaConcursante;
import edu.dosw.repository.RepositorioReceta;
import edu.dosw.service.strategy.EstrategiaRegistroReceta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("mongoService")
public class ServicioReceta {
    @Autowired
    private final RepositorioReceta recetaRepository;
    private EstrategiaRegistroReceta estrategia;

    public ServicioReceta(RepositorioReceta recetaRepository) {
        this.recetaRepository = recetaRepository;
    }

    public void setEstrategia(EstrategiaRegistroReceta estrategia) {
        this.estrategia = estrategia;
    }

    public Receta registrarReceta(RecetaRequest req) {
        Receta receta = estrategia.registrarReceta(req);
        recetaRepository.guardar(receta);
        return receta;
    }

    public List<Receta> obtenerTodas() {
        return recetaRepository.findAll();
    }

    public Receta obtenerPorConsecutivo(String id) {
        return recetaRepository.findById(id);
    }

    public List<Receta> obtenerPorTipo(String tipo) {
        return recetaRepository.findByTipo(tipo);
    }

    public List<Receta> obtenerPorTemporada(int temp) {
        return recetaRepository.findByTemporada(temp);
    }

    public List<Receta> buscarPorIngrediente(String nombre) {
        return recetaRepository.findByIngrediente(nombre);
    }

    public void eliminar(String id) {
        recetaRepository.eliminar(id);
    }

    public Receta actualizar(RecetaRequest req) {
        Receta existente = recetaRepository.findById(req.getTitulo());

        if (req.getPasosPreparacion() != null)
            existente.setPasosPreparacion(req.getPasosPreparacion());
        if (req.getListaIngredientes() != null)
            existente.setListaIngredientes(req.getListaIngredientes());
        if (req.getNombreChef() != null)
            existente.setNombreChef(req.getNombreChef());

        recetaRepository.guardar(existente);
        return existente;
    }
}
