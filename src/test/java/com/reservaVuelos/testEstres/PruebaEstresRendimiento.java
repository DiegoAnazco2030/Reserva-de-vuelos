package com.reservaVuelos.testEstres;

import com.reservaVuelos.modelo.persona.Usuario;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PruebaEstresRendimiento {

    private static RepoUsuariosAislado repositorio;
    private static final int CANTIDAD_REGISTROS = 1000000;
    private static List<Long> idGenerados = new ArrayList<>();

    @BeforeAll
    static void inicializar() {
        repositorio = new RepoUsuariosAislado();
    }

    @AfterAll
    static void finalizar() {
        new File("usuariosTest.raf").delete();
        new File("usuariosTest.idx").delete();
        IO.println("Archivos eliminador y test finalizado");
    }

    @Test
    @Order(1)
    void testPoblamientoMasivo(){

        //Comeinzo temporizador
        long inicioTiempoTest = System.nanoTime();

        for(int i = 0; i < CANTIDAD_REGISTROS; i++){
            //Creo datos aleatorio sin id como si fuera el dto, despues creo un usuario con id como se haria en el programa
            Usuario usuarioDTO = GeneradorDatos.generarUsuarioAleatorio();
            Long idUsuario = (long) i + 1;
            Usuario usuarioIngreso = new Usuario(
                    usuarioDTO.getNombre(),
                    usuarioDTO.getApellido(),
                    usuarioDTO.getTelefono(),
                    usuarioDTO.getEdad(),
                    usuarioDTO.getUsuarioPassaporteID(),
                    usuarioDTO.getUsuarioContrasenia(),
                    usuarioDTO.getUsuarioEmail(),
                    idUsuario
            );

            try{
                repositorio.guardar(usuarioIngreso);
                idGenerados.add(idUsuario);
            }catch(Exception e){
                fail("Error al guardar el usuario:" + e.getMessage());
            }
        }

        long finTiempoTest = System.nanoTime();
        //Tiempo en milisegundos que tardo
        double tiempoTotal = (double) (finTiempoTest - inicioTiempoTest) / 1000000;
        IO.println("Tiempo total escritura: " + tiempoTotal);
        IO.println("Promedio por registro " + tiempoTotal/CANTIDAD_REGISTROS + " milisegundos");
    }

    @Test
    @Order(2)
    void testComparacionBusqueda(){

        //Test de busqueda
        Long idBusqueda = idGenerados.getLast(); //Selecciono el ultimo usuario para que sea el peor caso

        long inicioTiempoBusquedaTest = System.nanoTime();
        Usuario usuarioBusqueda = repositorio.buscarPorID(idBusqueda);
        long finTiempoBusquedaTest = System.nanoTime();
        //Tiempo en milisegundos que tardo
        double tiempoTotalBusqueda = (double) (finTiempoBusquedaTest - inicioTiempoBusquedaTest) / 1000000;
        assertNotNull(usuarioBusqueda); //Si lo encontro debe ser no nulo
        IO.println("Tiempo total busqueda: " + tiempoTotalBusqueda);
    }

    @Test
    @Order(3)
    void testObtenerTodo(){

        //Test de obtenerTodo los usuarios
        long inicioTiempoObtenerTodoTest =  System.nanoTime();
        List<Usuario> todoUsuarios = repositorio.buscarTodos();
        long finTiempoObtenerTodoTest = System.nanoTime();
        //Tiempo en milisegundos que tardo
        double timepoTotalObtener = (double) (finTiempoObtenerTodoTest - inicioTiempoObtenerTodoTest) / 1000000;
        assertNotNull(todoUsuarios);
        IO.println("Tiempo total obtenerTodo: " + timepoTotalObtener);
    }
}