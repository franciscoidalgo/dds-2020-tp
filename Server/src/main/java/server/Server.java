package server;


import domain.Entidad.CategorizacionEmpresa.Categoria;
import domain.Entidad.CategorizacionEmpresa.Sector;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;
import spark.Spark;


public class Server {
    public static void main(String[] args) throws Exception {
        //iniciarCategoriasAFIP();

        Spark.port(Integer.parseInt(System.getenv("PORT")));
        //Spark.port(9000);
        Router.init();
        //DebugScreen.enableDebugScreen();
    }

    /*
    private static void iniciarCategoriasAFIP() {
        Repositorio<Sector> sectorRepositorio = FactoryRepo.get(Sector.class);
        if (sectorRepositorio.buscarTodos().isEmpty()) {
            //AGRO
            Sector sectorAgro = new Sector("Agropecuario", "Agropecuario");
            sectorAgro.agregateCategoria(new Categoria(5, 17260000, "Micro"));
            sectorAgro.agregateCategoria(new Categoria(10, 71960000, "Pequena"));
            sectorAgro.agregateCategoria(new Categoria(50, 426720000, "Mediana - Tramo 1"));
            sectorAgro.agregateCategoria(new Categoria(215, 676810000, "Mediana - Tramo 2"));
            sectorRepositorio.agregar(sectorAgro);

            //Mineria
            Sector sectorMineria = new Sector("Industria y Mineria", "Industria y Mineria");
            sectorMineria.agregateCategoria(new Categoria(15, 33920000, "Micro"));
            sectorMineria.agregateCategoria(new Categoria(60, 243290000, "Pequena"));
            sectorMineria.agregateCategoria(new Categoria(235, 1651750000, "Mediana - Tramo 1"));
            sectorMineria.agregateCategoria(new Categoria(655, (int) 2540380000L, "Mediana - Tramo 2"));
            sectorRepositorio.agregar(sectorMineria);

            //Comercio
            Sector sectorComercio = new Sector("Comercio", "Comercio");
            sectorComercio.agregateCategoria(new Categoria(7, 36320000, "Micro"));
            sectorComercio.agregateCategoria(new Categoria(35, 247200000, "Pequena"));
            sectorComercio.agregateCategoria(new Categoria(125, 1821760000, "Mediana - Tramo 1"));
            sectorComercio.agregateCategoria(new Categoria(345, (int) 2602540000L, "Mediana - Tramo 2"));
            sectorRepositorio.agregar(sectorComercio);

            //Servicio
            Sector sectorServicio = new Sector("Servicio", "Servicio");
            sectorServicio.agregateCategoria(new Categoria(7, 9900000, "Micro"));
            sectorServicio.agregateCategoria(new Categoria(30, 59710000, "Pequena"));
            sectorServicio.agregateCategoria(new Categoria(165, 494200000, "Mediana - Tramo 1"));
            sectorServicio.agregateCategoria(new Categoria(535, 705790000, "Mediana - Tramo 2"));
            sectorRepositorio.agregar(sectorServicio);

            //Construccion
            Sector sectorConst = new Sector("Construccion", "Construccion");
            sectorServicio.agregateCategoria(new Categoria(12, 19450000, "Micro"));
            sectorServicio.agregateCategoria(new Categoria(45, 115370000, "Pequena"));
            sectorServicio.agregateCategoria(new Categoria(200, 643710000, "Mediana - Tramo 1"));
            sectorServicio.agregateCategoria(new Categoria(590, 965460000, "Mediana - Tramo 2"));
            sectorRepositorio.agregar(sectorServicio);
        }
    }
     */
}
