package es.daw.jakartalogin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


@WebServlet("/alta")
public class AltaServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AltaServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        try {
            List<String> tecnologias = leerFichero("/WEB-INF/datos/tecnologias.txt");
            LOGGER.info(tecnologias.toString());

            request.setAttribute("tecnologias",tecnologias);

            request.getRequestDispatcher("/formulario.jsp").forward(request,response);

        }catch (IOException e){
            // PENDIENTE!!!! enviar a una paǵina jsp de error el mensaje de error...
            LOGGER.severe(e.getMessage());
        }




    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        // Pendiente leer todos los parámetros del formulario
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String tecnologia = request.getParameter("tecnologia");
        String nivel = request.getParameter("nivel");

        // EN ESTE PUNTO SE COMPROBARÍA EN BD SI EXISTE UN USUARIO CON ESE NOMBRE... ETC...
        // CONSIDERAMOS QUE TODO OK!!!

        // Pendiente enviar a la jsp como atributos los parámetros..
        request.setAttribute("nombre",nombre);
        request.setAttribute("email",email);
        request.setAttribute("tecnologia",tecnologia);
        request.setAttribute("nivel",nivel);

        // Pendiente llamar a la página confirmacion.jsp

        request.getRequestDispatcher("/confirmacion.jsp").forward(request,response);

    }

    /**
     * Lee un fichero de texto
     * @param pathFile ruta al fichero. Debe ser absoluta y encontrarse protegida en WEB-INF
     * @return List de cadena de texto de cada linea
     * @throws IOException
     */
    private List<String> leerFichero(String pathFile) throws IOException{
        List<String> lista = new ArrayList<>();

        InputStream is = getServletContext().getResourceAsStream(pathFile);

        if ( is == null)
            throw new IOException("No se encuentra "+pathFile);


        try(BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))){
            String linea;
            while( (linea = br.readLine()) != null){
                if (!linea.isBlank())
                    lista.add(linea.trim());

            }
        }
        return lista;
    }

}