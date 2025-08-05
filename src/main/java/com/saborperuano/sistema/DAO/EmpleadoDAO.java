package com.saborperuano.sistema.DAO;

import com.saborperuano.sistema.Controllers.CredencialController;
import com.saborperuano.sistema.Interfaces.IEmpleado;
import com.saborperuano.sistema.Models.DTO.CredencialDTO;
import com.saborperuano.sistema.Models.Empleado;
import com.saborperuano.sistema.Models.Enums.Cargo;
import com.saborperuano.sistema.Models.DTO.EmpleadoDTO;
import com.saborperuano.sistema.Models.Enums.Estado;
import com.saborperuano.sistema.Models.Enums.EstadoRegistro;
import com.saborperuano.sistema.Views.Principal.FrmPrincipal;
import utils.ListaEnlazadaGenerica;

import java.sql.*;
import java.time.LocalDate;

import static com.saborperuano.sistema.Views.Principal.FrmPrincipal.listaPersonal;

public class EmpleadoDAO implements IEmpleado {

    @Override
    public ListaEnlazadaGenerica<EmpleadoDTO> listar() {
            String consulta = "{call sp_listarEmpleado}";
        Connection conexion = ConexionBD.getConnection();
        ListaEnlazadaGenerica<EmpleadoDTO> empleados = null;

        try {
            CallableStatement sentencia = conexion.prepareCall(consulta);
            ResultSet filas = sentencia.executeQuery();

            empleados = new ListaEnlazadaGenerica<>();
            while (filas.next()) {

                EstadoRegistro[] estados = EstadoRegistro.values();

                EmpleadoDTO nuevoRegistro = new EmpleadoDTO(
                        filas.getInt("codigo"),
                        filas.getString("usuario"),
                        filas.getString("contrasena"),
                        filas.getString("nombre"),
                        filas.getString("apellidoPaterno"),
                        filas.getString("apellidoMaterno"),
                        filas.getString("direccion"),
                        filas.getString("correo"),
                        filas.getInt("dni"),
                        filas.getInt("telefono"),
                        filas.getDouble("salario"),
                        Cargo.valueOf(filas.getString("cargo")),
                              estados[filas.getInt("estado")].name()
                );


                if (empleados.longitud == 0){
                    empleados.agregarInicio(nuevoRegistro);
                }else {
                    empleados.agregarAlFinal(nuevoRegistro);
                }

            }
            filas.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return empleados;
    }

    @Override
    public Empleado agregar(EmpleadoDTO registro) throws Exception {
        String consulta = "{call sp_agregarEmpleado(?,?,?,?,?,?,?,?,?,?,?,?)}";
        Connection conexion = ConexionBD.getConnection();
        Empleado nuevoEmpleado = null;
        try {
            //Creando nuevo empleado
             nuevoEmpleado = new Empleado(
                    listaPersonal.obtenerUltimoObjeto().getCodigo() +1 ,
                    registro.getCargo().ordinal()+1,1,registro.getNombre(),
                    registro.getApellidoPaterno(),registro.getApellidoMaterno(),registro.getDni(),
                    registro.getDireccion(),registro.getCorreo(),registro.getTelefono(), Date.valueOf(LocalDate.now()),
                    registro.getSalario(),1);

            //Registrandolo
            CallableStatement sentencia = conexion.prepareCall(consulta);
            sentencia.setInt(1, nuevoEmpleado.getIdCargo());
            sentencia.setInt(2, nuevoEmpleado.getIdEmpresa());
            sentencia.setString(3, nuevoEmpleado.getNombres());
            sentencia.setString(4, nuevoEmpleado.getApellido_paterno());
            sentencia.setString(5, nuevoEmpleado.getApellido_materno());
            sentencia.setInt(6, nuevoEmpleado.getDni());
            sentencia.setString(7, nuevoEmpleado.getDireccion());
            sentencia.setString(8, nuevoEmpleado.getCorreo_electronico());
            sentencia.setInt(9, nuevoEmpleado.getTelefono());
            sentencia.setDate(10, nuevoEmpleado.getFecha_inicio());
            sentencia.setDouble(11, nuevoEmpleado.getSalario());
            sentencia.setInt(12, nuevoEmpleado.getEstado());

            int filasAfectadas = sentencia.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Nuevo registro en empleado Agregado Correctamente");
            } else {
                throw new Exception("Error. No se pudo agregar el registro correctamente.");
            }

            //Creando credenciales del nuevo empleado para el sistema
            new CredencialController().agregar(new CredencialDTO(nuevoEmpleado.getIdEmpleado(),
                    registro.getUsuario(), registro.getContrasena()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return nuevoEmpleado;
    }

    @Override
    public Empleado modificar(EmpleadoDTO registro) throws Exception {

        String consulta = "{call sp_modificarEmpleado(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        Connection conexion = ConexionBD.getConnection();
        Empleado nuevoEmpleado = null;
        try {
            //Creando nuevo empleado
            nuevoEmpleado = new Empleado(
                    registro.getCodigo(),
                    registro.getCargo().ordinal()+1,1,registro.getNombre(),
                    registro.getApellidoPaterno(),registro.getApellidoMaterno(),registro.getDni(),
                    registro.getDireccion(),registro.getCorreo(),registro.getTelefono(), Date.valueOf(LocalDate.now()),
                    registro.getSalario(),1);

            //Registrandolo
            CallableStatement sentencia = conexion.prepareCall(consulta);
            sentencia.setInt(1, nuevoEmpleado.getIdEmpleado());
            sentencia.setInt(2, nuevoEmpleado.getIdCargo());
            sentencia.setInt(3, nuevoEmpleado.getIdEmpresa());
            sentencia.setString(4, nuevoEmpleado.getNombres());
            sentencia.setString(5, nuevoEmpleado.getApellido_paterno());
            sentencia.setString(6, nuevoEmpleado.getApellido_materno());
            sentencia.setInt(7, nuevoEmpleado.getDni());
            sentencia.setString(8, nuevoEmpleado.getDireccion());
            sentencia.setString(9, nuevoEmpleado.getCorreo_electronico());
            sentencia.setInt(10, nuevoEmpleado.getTelefono());
            sentencia.setDate(11, nuevoEmpleado.getFecha_inicio());
            sentencia.setDouble(12, nuevoEmpleado.getSalario());
            sentencia.setInt(13, nuevoEmpleado.getEstado());

            int filasAfectadas = sentencia.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Nuevo registro en empleado Agregado Correctamente");
            } else {
                throw new Exception("Error. No se pudo agregar el registro correctamente.");
            }

            //Creando credenciales del nuevo empleado para el sistema
            new CredencialController().modificar(new CredencialDTO(nuevoEmpleado.getIdEmpleado(),
                    registro.getUsuario(), registro.getContrasena()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return nuevoEmpleado;



    }

    @Override
    public boolean eliminar(int idEmpleado) throws Exception {
        String consulta = "{call sp_eliminarEmpleado(?)}";
        Connection conexion = ConexionBD.getConnection();
        boolean resultado = true;

        try {
            CallableStatement sentencia = conexion.prepareCall(consulta);
            sentencia.setInt(1, idEmpleado);

            int numeroFilasAfectadas = sentencia.executeUpdate();

            if (numeroFilasAfectadas == 0) {
                resultado = false;
                throw new Exception("Error. No se pudo eliminar el registro de los empleados con el id proporcionado.");
            }

            //Creando credenciales del nuevo empleado para el sistema
            new CredencialController().eliminar(idEmpleado);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }

}
