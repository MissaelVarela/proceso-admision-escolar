package com.company.rest.api;

import com.company.model.DatosSolicitud;
import com.company.model.Solicitud;
import com.company.model.SolicitudDAO;
import com.company.rest.api.dto.SolicitudResult;
import com.company.rest.api.exception.ExecutionException;
import com.company.rest.api.exception.ValidationException;

import org.bonitasoft.engine.bdm.BusinessObjectDaoCreationException;
import org.bonitasoft.web.extension.rest.RestAPIContext;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;
import java.time.format.DateTimeFormatter;

/**
 * Controller class
 */
public class Index extends AbstractIndex {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Index.class.getName());

    // Parameters
    private int p = 0;
    private int c = 0;
    private int aspirante_id = -1;
    
    /**
     * Ensure request is valid
     *
     * @param request the HttpRequest
     */
    @Override
    public void validateInputParameters(HttpServletRequest request) {
        // To retrieve query parameters use the request.getParameter(..) method.
        // Be careful, parameter values are always returned as String values

        // Retrieve p parameter
        String p_input = request.getParameter(PARAM_P);
        if (p_input == null) {
            throw new ValidationException(format("the parameter %s is missing", PARAM_P));
        }
        try {
        	p = Integer.parseInt(p_input);
        }
        catch(Exception e) {
        	throw new ValidationException(format("%s parameter must be a number", PARAM_P));
        }
        
        // Retrieve c parameter
        String c_input = request.getParameter(PARAM_C);
        if (c_input == null) {
            throw new ValidationException(format("the parameter %s is missing", PARAM_C));
        }
        try {
        	c = Integer.parseInt(c_input);
        }
        catch(Exception e) {
        	throw new ValidationException(format("%s parameter must be a number", PARAM_C));
        }
        
        // Retrieve aspirante_id parameter
        String aspirante_id_input = request.getParameter(PARAM_ASPIRANTE_ID);
        if (aspirante_id_input == null) {
            throw new ValidationException(format("the parameter %s is missing", PARAM_ASPIRANTE_ID));
        }
        try {
        	aspirante_id = Integer.parseInt(aspirante_id_input);
        }
        catch(Exception e) {
        	throw new ValidationException(format("%s parameter must be a number", PARAM_ASPIRANTE_ID));
        }

    }

    /**
     * Execute business logic
     *
     * @param context
     * @return Result
     */
    @Override
    protected Object execute(RestAPIContext context) {

        LOGGER.debug(format("Execute rest api call with params:  %s, %s, %s",  p,  c,  aspirante_id));

        // Getting access to DAO
        SolicitudDAO solicitudDAO = null;
        try {
        	solicitudDAO = context.getApiClient().getDAO(SolicitudDAO.class);
        } catch(BusinessObjectDaoCreationException e) {
        	throw new ExecutionException("failed to create business object dao (SolicitudDAO)");
        }
        
        // Business logic
        try {
        	long id = Long.parseLong(String.valueOf(aspirante_id));
            List<Solicitud> consulta = solicitudDAO.findByAspirante_id(id, 0, 99);

            ArrayList<SolicitudResult> list = new ArrayList<>();
            
            for (Solicitud solicitud : consulta) {
            	
            	DatosSolicitud datos_solicitud = solicitud.getInformacion().getDatos_solicitud();
            	DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd 'de' MMM'.' yyyy',' hh:mm a");
            	
            	String status = solicitud.getStatus();
            	String fecha = solicitud.getFecha_inicio().format(customFormatter);
            	String licenciatura = datos_solicitud.getLicenciatura().getNombre();
            	String periodo = datos_solicitud.getPeriodo_de_ingreso().getNombre();
            	
            	SolicitudResult solicitudResult = new SolicitudResult(status, fecha, licenciatura, periodo);
            	
            	list.add(solicitudResult);
    		}
            
            return list;
        } catch(Exception e) {
        	throw new ExecutionException("something failed searching for the requested information. " + e.getMessage());
        }
        
    }
}