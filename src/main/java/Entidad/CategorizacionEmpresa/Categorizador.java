package Entidad.CategorizacionEmpresa;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Categorizador {

    public Categoria categoriza(Categorizable unCategorizable) {
        return this.buscaCategoriasPosiblesSegun(unCategorizable).isEmpty() ?
                this.buscaCategoriaMaxSegun(unCategorizable) : this.buscaCategoriaMinSegun(unCategorizable);

    }

    private List<Categoria> buscaCategoriasPosiblesSegun(Categorizable unCategorizable) {
        return unCategorizable.sector().getCategoria().stream().filter(unaCategoria ->
                unaCategoria.cumpleRequisitos(unCategorizable))
                .collect(Collectors.toList());
    }

    private Categoria buscaCategoriaMinSegun(Categorizable unCategorizable) {

        return this.buscaCategoriasPosiblesSegun(unCategorizable).stream()
                .min(Comparator.comparing(Categoria::getMaxCantPersonal)).get();

    }

    private Categoria buscaCategoriaMaxSegun(Categorizable unCategorizable) {

        return unCategorizable.sector().getCategoria().stream()
                .max(Comparator.comparing(Categoria::getMaxCantPersonal)).get();

    }
}
